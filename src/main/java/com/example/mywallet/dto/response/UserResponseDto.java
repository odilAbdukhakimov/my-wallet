package com.example.mywallet.dto.response;

import com.example.mywallet.entity.BalanceEntity;
import com.example.mywallet.entity.NoteEntity;
import com.example.mywallet.entity.TransactionEntity;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    private UUID id;
    private String name;
    private String username;
    @JsonProperty("photo_url")
    private String photoUrl;
    @JsonProperty("my_balance")
    private BalanceEntity myBalance;
    @JsonProperty("my_notes")
    private List<NoteEntity> myNotes;
    private List<TransactionEntity> transaction;
    @JsonProperty("role")
    private List<RoleEnum> perRoleEnumList;

    public static UserResponseDto from(UserEntity user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .photoUrl(user.getPhotoUrl())
                .myBalance(user.getMyBalance())
                .myNotes(user.getMyNotes())
                .transaction(user.getTransaction())
                .perRoleEnumList(user.getPerRoleEnumList())
                .build();

    }
}
