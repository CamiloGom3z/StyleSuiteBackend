package com.backendstyle.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.backendstyle.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GaleriaImagenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GaleriaImagen.class);
        GaleriaImagen galeriaImagen1 = new GaleriaImagen();
        galeriaImagen1.setId(1L);
        GaleriaImagen galeriaImagen2 = new GaleriaImagen();
        galeriaImagen2.setId(galeriaImagen1.getId());
        assertThat(galeriaImagen1).isEqualTo(galeriaImagen2);
        galeriaImagen2.setId(2L);
        assertThat(galeriaImagen1).isNotEqualTo(galeriaImagen2);
        galeriaImagen1.setId(null);
        assertThat(galeriaImagen1).isNotEqualTo(galeriaImagen2);
    }
}
