package com.backendstyle.myapp.repository;

import com.backendstyle.myapp.domain.Servicios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Servicios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long> {}
