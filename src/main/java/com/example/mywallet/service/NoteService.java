package com.example.mywallet.service;

import com.example.mywallet.dto.request.NoteRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.NoteResponseDto;
import com.example.mywallet.entity.NoteEntity;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.NoteRepository;
import com.example.mywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public ApiResponse create(NoteRequestDto requestDto, UserEntity user) {
        NoteEntity note = NoteEntity.of(requestDto);
//        UserEntity user = userRepository.findById(requestDto.getUserId()).orElseThrow(() ->
//                new RecordNotFound("User not found"));
        note.setUserEntity(user);
        NoteEntity save = noteRepository.save(note);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                NoteResponseDto.from(save)
        );
    }

    public ApiResponse update(UUID id, NoteRequestDto requestDto) {
        NoteEntity byId = getById(id);
        if (requestDto.getTheme() != null && !requestDto.getTheme().equals("")) {
            byId.setTheme(requestDto.getTheme());
        }
        if (requestDto.getTitle() != null && !requestDto.getTitle().equals("")) {
            byId.setTitle(requestDto.getTitle());
        }
        byId.setUpdateDate(LocalDate.now());
        NoteEntity save = noteRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                NoteResponseDto.from(save)
        );

    }

    public ApiResponse delete(UUID id) {
        NoteEntity byId = getById(id);
        noteRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Deleted note theme : " +
                        byId.getTheme()
        );
    }

    public ApiResponse getAllNotesOfUser(UserEntity user) {
//        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
//                new RecordNotFound("User not found"));
        List<NoteEntity> myNotes = user.getMyNotes();
        List<NoteResponseDto> list = myNotes.stream().map(NoteResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                list
        );
    }

    private NoteEntity getById(UUID id) {
        return noteRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Note not found"));
    }
}
