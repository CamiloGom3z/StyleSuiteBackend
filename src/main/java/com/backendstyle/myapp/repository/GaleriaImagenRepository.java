package com.backendstyle.myapp.repository;

import com.backendstyle.myapp.domain.GaleriaImagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GaleriaImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GaleriaImagenRepository extends JpaRepository<GaleriaImagen, Long> {}
