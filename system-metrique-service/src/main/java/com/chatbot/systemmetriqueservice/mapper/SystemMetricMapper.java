package com.chatbot.commonlibrary.mapper;

import com.chatbot.commonlibrary.dtos.SystemMetricDTO;
import com.chatbot.systemmetriqueservice.model.SystemMetric;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemMetricMapper {
    SystemMetricDTO toDto(SystemMetric metric);
    SystemMetric toEntity(SystemMetricDTO dto);
}