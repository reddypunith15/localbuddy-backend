package com.localbuddy.experience;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    Optional<City> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByNameIgnoreCase(String name);

    List<City> findByActiveTrueOrderByDisplayOrderAscNameAsc();

    List<City> findAllByOrderByDisplayOrderAscNameAsc();
}
