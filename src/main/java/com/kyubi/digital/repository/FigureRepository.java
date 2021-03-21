package com.kyubi.digital.repository;

import com.kyubi.digital.domain.Figure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Figure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FigureRepository extends JpaRepository<Figure, Long> {
}
