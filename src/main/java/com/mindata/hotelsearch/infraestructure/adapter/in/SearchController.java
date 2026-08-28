package com.mindata.hotelsearch.infraestructure.adapter.in;

import com.mindata.hotelsearch.application.services.CreateSearchService;
import com.mindata.hotelsearch.application.services.GetSearchCountService;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchCountResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchIdResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.mapper.SearchHotelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Validated
@Tag(name = "Hotel Search", description = "Hotel search operations")
public class SearchController {
    private final CreateSearchService createSearchService;
    private final GetSearchCountService getSearchCountService;
    private final SearchHotelMapper searchHotelMapper;

    public SearchController(CreateSearchService createSearchService,
                            GetSearchCountService getSearchCountService,
                            SearchHotelMapper searchHotelMapper) {
        this.createSearchService = createSearchService;
        this.getSearchCountService = getSearchCountService;
        this.searchHotelMapper = searchHotelMapper;
    }

    @PostMapping("/search")
    @Operation(summary = "Create a hotel availability search")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Search created"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<SearchIdResponseDto> createSearch(@Valid @RequestBody SearchRequestDto request) {
        SearchCriteria criteria = searchHotelMapper.toCriteria(request);
        String searchId = createSearchService.execute(criteria);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SearchIdResponseDto(searchId));
    }

    @GetMapping("/count")
    @Operation(summary = "Get identical search count by searchId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search count returned"),
            @ApiResponse(responseCode = "400", description = "Invalid searchId parameter"),
            @ApiResponse(responseCode = "404", description = "Search not found")
    })
    public ResponseEntity<SearchCountResponseDto> getSearchCount(
            @RequestParam("searchId") @NotBlank(message = "searchId must not be blank") String searchId) {
        SearchCountResult result = getSearchCountService.execute(searchId);
        return ResponseEntity.ok(searchHotelMapper.toCountResponse(result));
    }
}
