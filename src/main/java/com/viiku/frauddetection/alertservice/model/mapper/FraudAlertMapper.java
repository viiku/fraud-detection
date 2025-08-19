package com.viiku.frauddetection.alertservice.model.mapper;

import com.viiku.frauddetection.common.model.mapper.BaseMapper;
import com.viiku.frauddetection.alertservice.model.dto.FraudAlertDto;
import com.viiku.frauddetection.alertservice.model.entity.FraudAlertEntity;
import org.springframework.stereotype.Component;

@Component
public class FraudAlertMapper implements BaseMapper<FraudAlertDto, FraudAlertEntity> {

    @Override
    public FraudAlertEntity mapToEntity(FraudAlertDto dto) {
        return null;
    }

    @Override
    public FraudAlertDto mapToDto(FraudAlertEntity entity) {
        return null;
    }
}
