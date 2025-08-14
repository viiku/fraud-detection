package com.viiku.frauddetection.models.mappers;

import com.viiku.frauddetection.common.model.mapper.BaseMapper;
import com.viiku.frauddetection.models.dtos.FraudAlertDto;
import com.viiku.frauddetection.models.entities.FraudAlertEntity;
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
