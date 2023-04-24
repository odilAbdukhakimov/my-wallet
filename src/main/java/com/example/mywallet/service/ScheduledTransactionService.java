package com.example.mywallet.service;

import com.example.mywallet.dto.request.STransactionRequestDto;
import com.example.mywallet.dto.request.TransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.STransactionResponseDto;
import com.example.mywallet.dto.response.TransactionResponseDto;
import com.example.mywallet.entity.CategoryEntity;
import com.example.mywallet.entity.ScheduledTransactionEntity;
import com.example.mywallet.entity.TransactionEntity;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.CategoryRepository;
import com.example.mywallet.repository.ScheduledTransactionRepository;
import com.example.mywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduledTransactionService {
    private final ScheduledTransactionRepository sTransactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionService transactionService;

    public ApiResponse create(STransactionRequestDto requestDto) {
        ScheduledTransactionEntity of = ScheduledTransactionEntity.of(requestDto);
        ScheduledTransactionEntity save = sTransactionRepository.save(of);
        STransactionResponseDto response = getResponse(save);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                response
        );
    }

    public ApiResponse update(UUID id, STransactionRequestDto requestDto) {
        ScheduledTransactionEntity byId = getById(id);
        if (requestDto.getAmount() > 0) {
            byId.setAmount(requestDto.getAmount());
        }
        if (requestDto.getPlanDate() != null) {
            byId.setPlanDate(requestDto.getPlanDate());
        }
        if (requestDto.getCategoryId() != null) {
            byId.setCategoryId(requestDto.getCategoryId());
        }
        ScheduledTransactionEntity save = sTransactionRepository.save(byId);
        STransactionResponseDto response = getResponse(save);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                response
        );
    }

    public ApiResponse getAllScheduledTransactionsOfUser(UUID userId) {
        List<ScheduledTransactionEntity> byUserId = sTransactionRepository.findByUserId(userId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byUserId
        );
    }

    public ApiResponse delete(UUID id) {
        ScheduledTransactionEntity byId = getById(id);
        sTransactionRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    @SneakyThrows
    @Scheduled(cron = "0 */1 * * * *")
    public void cronCreateTransaction() {
        List<ScheduledTransactionEntity> byPlanDate = sTransactionRepository.findByPlanDate(LocalDate.now());
        List<ScheduledTransactionEntity> list = byPlanDate.stream().filter((sT) ->
                sT.getStatus().equals(TransactionStatusEnum.IN_PROGRESS)).toList();
        list.forEach(t -> {
            TransactionRequestDto requestDto = new TransactionRequestDto(t.getAmount(), t.getUserId(), t.getCategoryId());
            ApiResponse apiResponse = transactionService.create(requestDto);
            TransactionResponseDto data = (TransactionResponseDto) apiResponse.getData();
            t.setStatus(data.getStatus());
            sTransactionRepository.save(t);
        });

    }

    private ScheduledTransactionEntity getById(UUID id) {
        return sTransactionRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Scheduled Transaction not found"));
    }

    private STransactionResponseDto getResponse(ScheduledTransactionEntity entity) {
        STransactionResponseDto from = STransactionResponseDto.from(entity);
        UserEntity user = userRepository.findById(entity.getUserId()).orElseThrow(() -> new RecordNotFound("User not found"));
        CategoryEntity category = categoryRepository.findById(entity.getCategoryId()).orElseThrow(() -> new RecordNotFound("Category not found"));
        from.setUserEntityName(user.getName());
        from.setCategoryName(category.getName());
        return from;
    }
}
