package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.Establecimiento;
import com.backendstyle.myapp.service.dto.EstablecimientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Establecimiento} and its DTO {@link EstablecimientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstablecimientoMapper extends EntityMapper<EstablecimientoDTO, Establecimiento> {}
