package com.backendstyle.myapp.service.impl;

import com.backendstyle.myapp.domain.Establecimiento;
import com.backendstyle.myapp.repository.EstablecimientoRepository;
import com.backendstyle.myapp.service.EstablecimientoService;
import com.backendstyle.myapp.service.dto.EstablecimientoDTO;
import com.backendstyle.myapp.service.mapper.EstablecimientoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Establecimiento}.
 */
@Service
@Transactional
public class EstablecimientoServiceImpl implements EstablecimientoService {

    private final Logger log = LoggerFactory.getLogger(EstablecimientoServiceImpl.class);

    private final EstablecimientoRepository establecimientoRepository;

    private final EstablecimientoMapper establecimientoMapper;

    public EstablecimientoServiceImpl(EstablecimientoRepository establecimientoRepository, EstablecimientoMapper establecimientoMapper) {
        this.establecimientoRepository = establecimientoRepository;
        this.establecimientoMapper = establecimientoMapper;
    }

    @Override
    public EstablecimientoDTO save(EstablecimientoDTO establecimientoDTO) {
        log.debug("Request to save Establecimiento : {}", establecimientoDTO);
        Establecimiento establecimiento = establecimientoMapper.toEntity(establecimientoDTO);
        establecimiento = establecimientoRepository.save(establecimiento);
        return establecimientoMapper.toDto(establecimiento);
    }

    @Override
    public EstablecimientoDTO update(EstablecimientoDTO establecimientoDTO) {
        log.debug("Request to save Establecimiento : {}", establecimientoDTO);
        Establecimiento establecimiento = establecimientoMapper.toEntity(establecimientoDTO);
        establecimiento = establecimientoRepository.save(establecimiento);
        return establecimientoMapper.toDto(establecimiento);
    }

    @Override
    public Optional<EstablecimientoDTO> partialUpdate(EstablecimientoDTO establecimientoDTO) {
        log.debug("Request to partially update Establecimiento : {}", establecimientoDTO);

        return establecimientoRepository
            .findById(establecimientoDTO.getId())
            .map(existingEstablecimiento -> {
                establecimientoMapper.partialUpdate(existingEstablecimiento, establecimientoDTO);

                return existingEstablecimiento;
            })
            .map(establecimientoRepository::save)
            .map(establecimientoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstablecimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Establecimientos");
        return establecimientoRepository.findAll(pageable).map(establecimientoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstablecimientoDTO> findOne(Long id) {
        log.debug("Request to get Establecimiento : {}", id);
        return establecimientoRepository.findById(id).map(establecimientoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Establecimiento : {}", id);
        establecimientoRepository.deleteById(id);
    }
}
