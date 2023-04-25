package com.example.mywallet.repository;

import com.example.mywallet.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findByUserEntity_Id(UUID id);
}
