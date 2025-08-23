package com.viiku.frauddetection.transactionservice.controllers;

import com.viiku.frauddetection.common.model.dto.response.CustomResponse;
import com.viiku.frauddetection.transactionservice.models.dtos.request.TransactionRequest;
import com.viiku.frauddetection.transactionservice.models.dtos.response.TransactionResponse;
import com.viiku.frauddetection.transactionservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for transactions apis
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "Submit a new transaction for processing")
    public ResponseEntity<CustomResponse<TransactionResponse>> submitTransaction(
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse processedTransaction = transactionService.processTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomResponse.success(
                        processedTransaction,
                        "Successfully Processed Transactions",
                        HttpStatus.CREATED.value())
                );
    }

    @GetMapping("/account/{accountId}/recent")
    @Operation(summary = "Get recent transactions for an account")
    public ResponseEntity<List<TransactionResponse>> getRecentTransactions(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        // Service returns Spring Data Page<TransactionResponse>
        List<TransactionResponse> transactionResponseList = transactionService.getRecentTransactions(accountId, pageNumber, pageSize);

        // Convert into CustomPage
//        CustomPage<TransactionResponse> customPage = CustomPage.of(page.getContent(), page);
//
//        // Wrap into CustomPagingResponse
//        CustomPagingResponse<TransactionResponse> response = CustomPagingResponse.<TransactionResponse>builder()
//                .content(customPage.getContent())
//                .pageNumber(customPage.getPageNumber())
//                .pageSize(customPage.getPageSize())
//                .totalElementCount(customPage.getTotalElementCount())
//                .totalPageCount(customPage.getTotalPageCount())
//                .build();

        return ResponseEntity.ok(transactionResponseList);
    }
}
