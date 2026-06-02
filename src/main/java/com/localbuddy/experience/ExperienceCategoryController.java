package com.localbuddy.experience;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience-categories")
public class ExperienceCategoryController {

    private final ExperienceCategoryService experienceCategoryService;

    public ExperienceCategoryController(ExperienceCategoryService experienceCategoryService) {
        this.experienceCategoryService = experienceCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExperienceCategoryResponse>> getActiveCategories() {
        return ResponseEntity.ok(experienceCategoryService.getActiveCategories());
    }
}