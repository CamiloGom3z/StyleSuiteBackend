package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.Servicios;
import com.backendstyle.myapp.service.dto.ServiciosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servicios} and its DTO {@link ServiciosDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiciosMapper extends EntityMapper<ServiciosDTO, Servicios> {}
