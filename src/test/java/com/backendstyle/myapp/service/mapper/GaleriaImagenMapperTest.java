package com.backendstyle.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GaleriaImagenMapperTest {

    private GaleriaImagenMapper galeriaImagenMapper;

    @BeforeEach
    public void setUp() {
        galeriaImagenMapper = new GaleriaImagenMapperImpl();
    }
}
