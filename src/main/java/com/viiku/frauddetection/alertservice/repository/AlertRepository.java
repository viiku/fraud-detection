package com.viiku.frauddetection.alertservice.repository;

import com.viiku.frauddetection.alertservice.model.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, Long> {

    List<AlertEntity> findByAccountIdOrderByCreatedAtDesc(String accountId);

    List<AlertEntity> findByStatusOrderByCreatedAtDesc(String status);

//    @Query("SELECT COUNT(fa) FROM FraudAlert fa WHERE fa.createdAt >= :since")
//    Long countAlertsCreatedSince(@Param("since") LocalDateTime since);

    List<AlertEntity> findByRiskLevelAndStatus(String riskLevel, String status);
}
