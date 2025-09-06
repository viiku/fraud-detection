package com.viiku.frauddetection.alertservice.model.mapper;

import com.viiku.frauddetection.common.model.mapper.BaseMapper;
import com.viiku.frauddetection.alertservice.model.dto.AlertDto;
import com.viiku.frauddetection.alertservice.model.entity.AlertEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper implements BaseMapper<AlertDto, AlertEntity> {

    @Override
    public AlertEntity mapToEntity(AlertDto dto) {
        return null;
    }

    @Override
    public AlertDto mapToDto(AlertEntity entity) {
        return null;
    }
}
