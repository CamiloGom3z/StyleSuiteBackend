package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.GaleriaImagen;
import com.backendstyle.myapp.service.dto.GaleriaImagenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GaleriaImagen} and its DTO {@link GaleriaImagenDTO}.
 */
@Mapper(componentModel = "spring")
public interface GaleriaImagenMapper extends EntityMapper<GaleriaImagenDTO, GaleriaImagen> {}
