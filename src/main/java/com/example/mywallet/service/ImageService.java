package com.example.mywallet.service;

import com.example.mywallet.entity.AttachmentEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AttachmentRepository attachmentRepository;
    private static final String PATH_FILE = "files/img/";

    @SneakyThrows
    public String uploadImage(MultipartFile file) {

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();

            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setSize(size);
            attachment.setContentType(contentType);
            attachment.setFileOriginalName(originalFileName);

            String randomName = makeRandomFileName(Objects.requireNonNull(originalFileName));

            attachment.setName(randomName);
            attachmentRepository.save(attachment);

            writeToFile(file, randomName);
            return randomName;
        }
        throw new RecordNotFound("file  not found");
    }

    private String makeRandomFileName(String originalFileName) {
        String[] split = originalFileName.split("\\.");
        return UUID.randomUUID() + "." + split[split.length - 1];
    }

    @SneakyThrows
    private void writeToFile(MultipartFile file, String randomName) {
        Path path = Paths.get(PATH_FILE + "/" + randomName);
        Files.copy(file.getInputStream(), path);
    }

    @SneakyThrows
    public String updateImage(MultipartFile file, String name) {
        if (name != null) {
            Optional<AttachmentEntity> foundByName = attachmentRepository.findByName(name);
            if (foundByName.isPresent()) {
                String randomName = makeRandomFileName(Objects.requireNonNull(file.getOriginalFilename()));
                writeToFile(file, randomName);

                AttachmentEntity attachmentEntity = foundByName.get();
                attachmentEntity.setName(randomName);
                attachmentEntity.setSize(file.getSize());
                attachmentEntity.setContentType(file.getContentType());
                attachmentEntity.setFileOriginalName(file.getOriginalFilename());

                attachmentRepository.save(attachmentEntity);
                File removedFile = new File(PATH_FILE + "/" + name);
                if (removedFile.delete()) {
                    System.out.println("File was  deleted successfully");
                } else {
                    System.out.println("File was Not  deleted successfully");
                }
                return randomName;
            } else {
                throw new RecordNotFound("AttachmentEntity was not found");
            }
        }
        return "";
    }

    public HttpEntity<byte[]> getPicture(String name) {
        Optional<AttachmentEntity> optionalAttachment = attachmentRepository.findByName(name);
        if (optionalAttachment.isPresent()) {
            AttachmentEntity attachment = optionalAttachment.get();
            try {
                FileInputStream fileInputStream = new FileInputStream(PATH_FILE + "/" + attachment.getName());
                byte[] image = new InputStreamResource(fileInputStream).getContentAsByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(image.length);
                return new HttpEntity<>(image, headers);
//                FileCopyUtils.copy(fileInputStream, outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}

