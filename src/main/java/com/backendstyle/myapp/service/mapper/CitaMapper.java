package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.Cita;
import com.backendstyle.myapp.service.dto.CitaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cita} and its DTO {@link CitaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitaMapper extends EntityMapper<CitaDTO, Cita> {}
