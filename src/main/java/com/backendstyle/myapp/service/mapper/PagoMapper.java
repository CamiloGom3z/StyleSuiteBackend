package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.Pago;
import com.backendstyle.myapp.service.dto.PagoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pago} and its DTO {@link PagoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagoMapper extends EntityMapper<PagoDTO, Pago> {}
