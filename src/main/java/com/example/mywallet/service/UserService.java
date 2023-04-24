package com.example.mywallet.service;

import com.example.mywallet.dto.request.UserUpdateRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.UserRepository;
import com.example.mywallet.utils.JwtUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public ApiResponse getById(UUID id) {
        UserEntity user = findById(id);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                user
        );
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("User not found"));
    }

    public ApiResponse uploadPhoto(HttpServletRequest request, MultipartFile file) {
        UserEntity byId = getUserByAccessToken(request);
        String s = imageService.uploadImage(file);
        byId.setPhotoUrl(s);
        UserEntity save = userRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }

    public ApiResponse edit(UserEntity user, UserUpdateRequestDto dto) {
        if (dto.getName() != null && !dto.getName().equals("")) {
            user.setName(dto.getName());
        }
        if (dto.getPassword() != null && !dto.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        UserEntity save = userRepository.save(user);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                save
        );
    }

    @SneakyThrows
    public UserEntity getUserByAccessToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            throw new RecordNotFound("Token isn't valid");
        }
        String token = requestHeader.replace("Bearer ", "");
        Claims claims = jwtUtils.isValidAccessToken(token);
        if (claims == null) {
            throw new RecordNotFound("Token isn't valid");
        }
        return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).
                readValue(claims.getSubject(), UserEntity.class);
    }
}
