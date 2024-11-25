package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.CategoriaProducto;
import com.backendstyle.myapp.service.dto.CategoriaProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaProducto} and its DTO {@link CategoriaProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaProductoMapper extends EntityMapper<CategoriaProductoDTO, CategoriaProducto> {}
