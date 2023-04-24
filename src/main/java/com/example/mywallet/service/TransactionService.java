package com.example.mywallet.service;

import com.example.mywallet.dto.request.TransactionRequestDto;
import com.example.mywallet.dto.response.ApiResponse;
import com.example.mywallet.dto.response.TransactionResponseDto;
import com.example.mywallet.dto.response.TransactionStatisticDto;
import com.example.mywallet.entity.CategoryEntity;
import com.example.mywallet.entity.TransactionEntity;
import com.example.mywallet.entity.UserEntity;
import com.example.mywallet.entity.enums.CategoryTypeEnum;
import com.example.mywallet.entity.enums.TransactionStatusEnum;
import com.example.mywallet.exception.RecordNotFound;
import com.example.mywallet.repository.CategoryRepository;
import com.example.mywallet.repository.TransactionRepository;
import com.example.mywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    public ApiResponse create(TransactionRequestDto dto) {
        TransactionEntity of = TransactionEntity.of(dto);
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new RecordNotFound("User not found"));
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() ->
                new RecordNotFound("Category not found"));
        double balance = user.getMyBalance().getBalance();
        if (balance < of.getAmount() && category.getType().equalsIgnoreCase(CategoryTypeEnum.OUTPUT.name())) {
            of.setStatus(TransactionStatusEnum.UNSUCCESSFUL);
            of.setTransactionType(CategoryTypeEnum.UNKNOWN.name());
        } else {
            if (category.getType().equals(CategoryTypeEnum.INPUT.name())) {
                balance += of.getAmount();
                of.setTransactionType(CategoryTypeEnum.INPUT.name());
            } else if (category.getType().equals(CategoryTypeEnum.OUTPUT.name())) {
                balance -= of.getAmount();
                of.setTransactionType(CategoryTypeEnum.OUTPUT.name());
            }
            of.setStatus(TransactionStatusEnum.SUCCESS);
            user.getMyBalance().setBalance(balance);
            userRepository.save(user);
        }
        of.setCategory(category);
        of.setUserEntity(user);
        TransactionEntity save = transactionRepository.save(of);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                TransactionResponseDto.from(save)
        );
    }

    public ApiResponse getTransactionByCategory(UUID categoryId, UUID userId) {
        List<TransactionEntity> byCategoryId = transactionRepository.findByCategory_IdAndUserEntity_Id(categoryId, userId);
        List<TransactionResponseDto> list = byCategoryId.stream().map(TransactionResponseDto::from).toList();
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getTransactionOnToday(String type, UUID userId) {
        List<TransactionEntity> byCreatedDate = transactionRepository.findByCreatedDateAndUserEntity_Id(LocalDate.now(), userId);
        List<TransactionResponseDto> list = byCreatedDate.stream().map(TransactionResponseDto::from).toList();
        String fileUrl = fileService.writeExcelFile(list);
        if (type.equalsIgnoreCase(CategoryTypeEnum.INPUT.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.INPUT.name())).toList();
        } else if (type.equalsIgnoreCase(CategoryTypeEnum.OUTPUT.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.OUTPUT.name())).toList();
        } else if (type.equalsIgnoreCase(CategoryTypeEnum.UNKNOWN.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.UNKNOWN.name())).toList();
        }
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getTransactionOnWeekly(String type, UUID userId) {
        LocalDate now = LocalDate.now();
        LocalDate startDay = now.minusDays(7);
        List<TransactionResponseDto> list = getTransactionListByDate(startDay, now, type, userId);
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getTransactionOnMonthly(String type, UUID userId) {
        LocalDate now = LocalDate.now();
        LocalDate startDay = now.minusMonths(1);
        List<TransactionResponseDto> list = getTransactionListByDate(startDay, now, type, userId);
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getTransactionOnYearly(String type, UUID userId) {
        LocalDate now = LocalDate.now();
        LocalDate startDay = now.minusMonths(12);
        List<TransactionResponseDto> list = getTransactionListByDate(startDay, now, type, userId);
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getTransactionListByAnyDate(LocalDate startDate, LocalDate endDate, String type, UUID userId) {
        List<TransactionResponseDto> list = getTransactionListByDate(startDate, endDate, type, userId);
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    public ApiResponse getAllTransactionsByType(String type, UUID userId) {
        List<TransactionEntity> byTransactionType = transactionRepository.findByUserEntity_Id(userId);
        List<TransactionResponseDto> list = byTransactionType.stream().map(TransactionResponseDto::from).filter(
                (t) -> type.equalsIgnoreCase(t.getTransactionType())
        ).toList();
        String fileUrl = fileService.writeExcelFile(list);
        return getApiResponse(list,fileUrl);
    }

    private List<TransactionResponseDto> getTransactionListByDate(LocalDate startDate, LocalDate endDate, String type, UUID userId) {
        List<TransactionEntity> byCreatedDateBetween = transactionRepository.findByCreatedDateBetweenAndUserEntity_Id(startDate, endDate, userId);
        List<TransactionResponseDto> list = byCreatedDateBetween.stream().map(TransactionResponseDto::from).toList();
        if (type.equalsIgnoreCase(CategoryTypeEnum.INPUT.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.INPUT.name())).toList();
        } else if (type.equalsIgnoreCase(CategoryTypeEnum.OUTPUT.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.OUTPUT.name())).toList();
        } else if (type.equalsIgnoreCase(CategoryTypeEnum.UNKNOWN.name())) {
            list = list.stream().filter((t) ->
                    t.getTransactionType().equals(CategoryTypeEnum.UNKNOWN.name())).toList();
        }
        return list;
    }

    private ApiResponse getApiResponse(List<TransactionResponseDto> list,String fileUrl) {
        int count = list.size();
        double amount = list.stream().parallel()
                .mapToDouble(dto -> {
                    if (dto.getTransactionType().equalsIgnoreCase(CategoryTypeEnum.INPUT.name())) {
                        return dto.getAmount();
                    } else if (dto.getTransactionType().equalsIgnoreCase(CategoryTypeEnum.OUTPUT.name())) {
                        return -dto.getAmount();
                    } else {
                        return 0.0;
                    }
                })
                .sum();
        TransactionStatisticDto transactionStatisticDto = new TransactionStatisticDto(list, count, amount,fileUrl);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                transactionStatisticDto
        );
    }
}
