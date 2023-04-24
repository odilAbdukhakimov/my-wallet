package com.example.mywallet.service;

import com.example.mywallet.dto.request.UserUpdateRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse getById(UUID id) {
        UserEntity user = findById(id);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                user
        );
    }

    private UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("User not found"));
    }

    public ApiResponse uploadPhoto(UUID id, MultipartFile file) {
        UserEntity byId = findById(id);
        String s = imageService.uploadImage(file);
        byId.setPhotoUrl(s);
        UserEntity save = userRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }

    public ApiResponse edit(UUID id, UserUpdateRequestDto dto) {
        UserEntity byId = findById(id);
        if (dto.getName() != null && !dto.getName().equals("")) {
            byId.setName(dto.getName());
        }
        if (dto.getPassword() != null && !dto.getPassword().equals("")) {
            byId.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        UserEntity save = userRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }
}
