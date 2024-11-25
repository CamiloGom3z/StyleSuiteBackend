package com.backendstyle.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiciosMapperTest {

    private ServiciosMapper serviciosMapper;

    @BeforeEach
    public void setUp() {
        serviciosMapper = new ServiciosMapperImpl();
    }
}
