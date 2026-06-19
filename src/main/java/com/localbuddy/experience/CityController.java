package com.localbuddy.experience;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@Tag(name = "Cities", description = "Public list of cities where experiences can be offered")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(
            summary = "List active cities",
            description = "Returns the active cities a host can offer an experience in. Used to populate the city selector."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active cities retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CityResponse>> getActiveCities() {
        return ResponseEntity.ok(cityService.getActiveCities());
    }
}
