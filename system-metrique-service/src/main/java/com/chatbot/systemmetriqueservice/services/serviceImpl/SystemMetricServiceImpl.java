package com.chatbot.systemmetriqueservice.services.serviceImpl;

import com.chatbot.commonlibrary.dtos.AdminDTO;
import com.chatbot.commonlibrary.dtos.SystemMetricDTO;
import com.chatbot.commonlibrary.enums.MetricCategory;
import com.chatbot.systemmetriqueservice.config.AdminFeignClient;
import com.chatbot.systemmetriqueservice.mapper.SystemMetricMapper;
import com.chatbot.systemmetriqueservice.model.SystemMetric;
import com.chatbot.systemmetriqueservice.repository.SystemMetricRepository;
import com.chatbot.systemmetriqueservice.services.SystemMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SystemMetricServiceImpl implements SystemMetricService {

    private final SystemMetricRepository repository;
    private final SystemMetricMapper mapper;
    private final AdminFeignClient adminClient;

    public SystemMetricServiceImpl(SystemMetricRepository repository, SystemMetricMapper mapper, AdminFeignClient adminClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.adminClient = adminClient;
    }

    @Override
    public SystemMetricDTO addMetric(SystemMetricDTO dto) {
        SystemMetric entity = mapper.toEntity(dto);
        entity.setTimestamp(Instant.now());

        // Appel au client pour enrichir
        AdminDTO adminDTO = adminClient.getAdminById(dto.getAdminId());

        // Sauvegarde de l'entité
        SystemMetric savedEntity = repository.save(entity);

        // Conversion en DTO
        SystemMetricDTO resultDto = mapper.toDto(savedEntity);

        // Injection manuelle de l’adminDTO car le mapper ne sait pas le faire
        resultDto.setAdminDTO(adminDTO);

        return resultDto;
    }


    @Override
    public List<SystemMetricDTO> getAllMetrics() {
        return repository.findAll().stream()
                .map(metric -> {
                    SystemMetricDTO dto = mapper.toDto(metric);
                    dto.setAdminDTO(adminClient.getAdminById(metric.getAdminId()));
                    return dto;
                }).toList();
    }
    @Override
    public List<SystemMetricDTO> getMetricsByAdmin(Long adminId) {
        return repository.findByAdminId(adminId).stream()
                .map(metric -> {
                    SystemMetricDTO dto = mapper.toDto(metric);
                    dto.setAdminDTO(adminClient.getAdminById(adminId)); // Appel au service Admin
                    return dto;
                })
                .toList();
    }

    @Override
    public List<SystemMetricDTO> getMetricsByCategory(MetricCategory category) {
        return repository.findByCategory(category)
                .stream()
                .map(metric -> {
                    SystemMetricDTO dto = mapper.toDto(metric);
                    dto.setAdminDTO(adminClient.getAdminById(metric.getAdminId()));
                    return dto;
                })
                .toList();
    }


}