package com.localbuddy.experience;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExperienceCategoryService {

    private final ExperienceCategoryRepository experienceCategoryRepository;

    public ExperienceCategoryService(ExperienceCategoryRepository experienceCategoryRepository) {
        this.experienceCategoryRepository = experienceCategoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ExperienceCategoryResponse> getActiveCategories() {
        return experienceCategoryRepository.findByActiveTrueOrderByDisplayOrderAscNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ExperienceCategoryResponse toResponse(ExperienceCategory category) {
        return new ExperienceCategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getDisplayOrder()
        );
    }
}