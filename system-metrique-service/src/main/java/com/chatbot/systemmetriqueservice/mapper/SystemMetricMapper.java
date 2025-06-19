package com.chatbot.systemmetriqueservice.mapper;

import com.chatbot.commonlibrary.dtos.SystemMetricDTO;
import com.chatbot.systemmetriqueservice.model.SystemMetric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SystemMetricMapper {

    SystemMetric toEntity(SystemMetricDTO dto);
    @Mapping(target = "adminDTO", ignore = true)
    SystemMetricDTO toDto(SystemMetric entity);
}