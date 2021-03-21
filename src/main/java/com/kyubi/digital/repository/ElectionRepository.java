package com.kyubi.digital.repository;

import com.kyubi.digital.domain.Election;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Election entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
}
