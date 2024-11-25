package com.backendstyle.myapp.service.mapper;

import com.backendstyle.myapp.domain.Persona;
import com.backendstyle.myapp.service.dto.PersonaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Persona} and its DTO {@link PersonaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonaMapper extends EntityMapper<PersonaDTO, Persona> {}
