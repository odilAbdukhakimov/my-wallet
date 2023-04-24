package com.example.mywallet.entity;

import com.example.mywallet.dto.request.NoteRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteEntity extends BaseEntity{
    private String theme;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    private LocalDate createdDate;
    private LocalDate updateDate;

    public static NoteEntity of(NoteRequestDto dto){
        return NoteEntity.builder()
                .theme(dto.getTheme())
                .title(dto.getTitle())
                .createdDate(LocalDate.now())
                .build();
    }
}
