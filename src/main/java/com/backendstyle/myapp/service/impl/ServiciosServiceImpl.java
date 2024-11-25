package com.backendstyle.myapp.service.impl;

import com.backendstyle.myapp.domain.Servicios;
import com.backendstyle.myapp.repository.ServiciosRepository;
import com.backendstyle.myapp.service.ServiciosService;
import com.backendstyle.myapp.service.dto.ServiciosDTO;
import com.backendstyle.myapp.service.mapper.ServiciosMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Servicios}.
 */
@Service
@Transactional
public class ServiciosServiceImpl implements ServiciosService {

    private final Logger log = LoggerFactory.getLogger(ServiciosServiceImpl.class);

    private final ServiciosRepository serviciosRepository;

    private final ServiciosMapper serviciosMapper;

    public ServiciosServiceImpl(ServiciosRepository serviciosRepository, ServiciosMapper serviciosMapper) {
        this.serviciosRepository = serviciosRepository;
        this.serviciosMapper = serviciosMapper;
    }

    @Override
    public ServiciosDTO save(ServiciosDTO serviciosDTO) {
        log.debug("Request to save Servicios : {}", serviciosDTO);
        Servicios servicios = serviciosMapper.toEntity(serviciosDTO);
        servicios = serviciosRepository.save(servicios);
        return serviciosMapper.toDto(servicios);
    }

    @Override
    public ServiciosDTO update(ServiciosDTO serviciosDTO) {
        log.debug("Request to save Servicios : {}", serviciosDTO);
        Servicios servicios = serviciosMapper.toEntity(serviciosDTO);
        servicios = serviciosRepository.save(servicios);
        return serviciosMapper.toDto(servicios);
    }

    @Override
    public Optional<ServiciosDTO> partialUpdate(ServiciosDTO serviciosDTO) {
        log.debug("Request to partially update Servicios : {}", serviciosDTO);

        return serviciosRepository
            .findById(serviciosDTO.getId())
            .map(existingServicios -> {
                serviciosMapper.partialUpdate(existingServicios, serviciosDTO);

                return existingServicios;
            })
            .map(serviciosRepository::save)
            .map(serviciosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiciosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servicios");
        return serviciosRepository.findAll(pageable).map(serviciosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiciosDTO> findOne(Long id) {
        log.debug("Request to get Servicios : {}", id);
        return serviciosRepository.findById(id).map(serviciosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servicios : {}", id);
        serviciosRepository.deleteById(id);
    }
}
