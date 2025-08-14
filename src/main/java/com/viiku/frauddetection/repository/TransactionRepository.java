package com.viiku.frauddetection.repository;

import com.viiku.frauddetection.models.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("""
    SELECT t FROM TransactionEntity t
    WHERE t.accountId = :accountId
      AND t.timestamp > :localDateTime
""")
    List<TransactionEntity> findRecentTransactionsByAccount(
            @Param("accountId") String accountId,
            @Param("localDateTime") LocalDateTime localDateTime);


    Long countByAccountIdAndTimestampAfter(String accountId, LocalDateTime since);
}
