package com.example.mywallet.controller;

import com.example.mywallet.dto.request.UserUpdateRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/")
@SecurityRequirement(name = "My walled")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping("{id}")
//    public ApiResponse getById(@PathVariable UUID id, HttpServletResponse response) throws IOException {
//        return userService.getById(id);
//    }

    @PostMapping(value = "upload-img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization Bearer token", required = true, dataType = "string", paramType = "header")
    })
    public ApiResponse uploadPhoto(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        return userService.uploadPhoto(request, file);
    }

    @PutMapping("update")
    public ApiResponse updateUser(@RequestBody UserUpdateRequestDto dto, HttpServletRequest request) {
        UserEntity user = userService.getUserByAccessToken(request);
        return userService.edit(user, dto);
    }
}
