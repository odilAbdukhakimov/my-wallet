package com.example.mywallet.dto.response;

import com.example.mywallet.entity.NoteEntity;
import com.example.mywallet.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class NoteResponseDto {
    private UUID id;
    private String theme;
    private String title;
    @JsonProperty("user_entity")
    private String userEntity;
    @JsonProperty("created_date")
    private LocalDate createdDate;
    @JsonProperty("update_date")
    private LocalDate updateDate;

    public static NoteResponseDto from(NoteEntity entity) {
        return NoteResponseDto.builder()
                .id(entity.getId())
                .theme(entity.getTheme())
                .title(entity.getTitle())
                .userEntity(entity.getUserEntity().getName())
                .createdDate(entity.getCreatedDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }
}
