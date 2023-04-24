package com.example.mywallet.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class NoteRequestDto {
    private String theme;
    private String title;
//    private UUID userId;
}
