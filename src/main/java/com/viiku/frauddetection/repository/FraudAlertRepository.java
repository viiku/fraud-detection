package com.viiku.frauddetection.repository;

import com.viiku.frauddetection.models.entities.FraudAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FraudAlertRepository extends JpaRepository<FraudAlertEntity, Long> {

    List<FraudAlertEntity> findByAccountIdOrderByCreatedAtDesc(String accountId);

    List<FraudAlertEntity> findByStatusOrderByCreatedAtDesc(String status);

//    @Query("SELECT COUNT(fa) FROM FraudAlert fa WHERE fa.createdAt >= :since")
//    Long countAlertsCreatedSince(@Param("since") LocalDateTime since);

    List<FraudAlertEntity> findByRiskLevelAndStatus(String riskLevel, String status);
}
