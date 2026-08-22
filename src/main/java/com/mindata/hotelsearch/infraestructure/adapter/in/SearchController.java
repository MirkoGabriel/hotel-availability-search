package com.mindata.hotelsearch.infraestructure.adapter.in;

import com.mindata.hotelsearch.application.port.in.CreateSearchUseCase;
import com.mindata.hotelsearch.application.port.in.GetSearchCountUseCase;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchCountResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchIdResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.mapper.SearchWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    private final CreateSearchUseCase createSearchUseCase;
    private final GetSearchCountUseCase getSearchCountUseCase;
    private final SearchWebMapper searchWebMapper;

    public SearchController(CreateSearchUseCase createSearchUseCase,
                            GetSearchCountUseCase getSearchCountUseCase,
                            SearchWebMapper searchWebMapper) {
        this.createSearchUseCase = createSearchUseCase;
        this.getSearchCountUseCase = getSearchCountUseCase;
        this.searchWebMapper = searchWebMapper;
    }

    @PostMapping("/search")
    @Operation(summary = "Create a hotel availability search")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search accepted"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<SearchIdResponseDto> createSearch(@Valid @RequestBody SearchRequestDto request) {
        SearchCriteria criteria = searchWebMapper.toCriteria(request);
        String searchId = createSearchUseCase.execute(criteria);
        return ResponseEntity.ok(new SearchIdResponseDto(searchId));
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
        SearchCountResult result = getSearchCountUseCase.execute(searchId);
        return ResponseEntity.ok(searchWebMapper.toCountResponse(result));
    }
}
