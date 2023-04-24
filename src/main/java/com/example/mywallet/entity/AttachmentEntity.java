package com.example.mywallet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AttachmentEntity {
    @Id
    @GeneratedValue
    private long id;
    private String fileOriginalName; // pdp.uz
    private long size;  //1024
    private String contentType;  // application/pdf/image
    private String name;
}

