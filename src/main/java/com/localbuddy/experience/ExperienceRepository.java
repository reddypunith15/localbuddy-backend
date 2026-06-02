package com.localbuddy.experience;

import com.localbuddy.localprofile.LocalProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, UUID> {

    Optional<Experience> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Experience> findByLocalProfile(LocalProfile localProfile);

    List<Experience> findByLocalProfileId(UUID localProfileId);

    List<Experience> findByStatus(ExperienceStatus status);

    List<Experience> findByCityIgnoreCaseAndStatus(String city, ExperienceStatus status);

    List<Experience> findByCityIgnoreCaseAndCategorySlugAndStatus(
            String city,
            String categorySlug,
            ExperienceStatus status
    );

    List<Experience> findByCategorySlugAndStatus(
            String categorySlug,
            ExperienceStatus status
    );

    long countByStatus(ExperienceStatus status);

    @EntityGraph(attributePaths = {"localProfile", "localProfile.user"})
    Optional<Experience> findWithLocalProfileAndUserById(UUID id);
}