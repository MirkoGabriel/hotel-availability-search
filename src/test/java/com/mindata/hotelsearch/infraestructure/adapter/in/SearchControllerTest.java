package com.mindata.hotelsearch.infraestructure.adapter.in;

import com.mindata.hotelsearch.application.port.CreateSearchUseCase;
import com.mindata.hotelsearch.application.port.GetSearchCountUseCase;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.exception.GlobalExceptionHandler;
import com.mindata.hotelsearch.infraestructure.adapter.in.mapper.SearchHotelMapperImpl;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
@Import({SearchHotelMapperImpl.class, GlobalExceptionHandler.class})
class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateSearchUseCase createSearchUseCase;

    @MockitoBean
    private GetSearchCountUseCase getSearchCountUseCase;

    @Test
    void shouldCreateSearch() throws Exception {
        when(createSearchUseCase.execute(any(SearchCriteria.class))).thenReturn("search-123");

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "hotelId": "1234aBc",
                                  "checkIn": "04/09/2026",
                                  "checkOut": "11/09/2026",
                                  "ages": [30, 29, 1, 3]
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.searchId").value("search-123"));
    }

    @Test
    void shouldReturnBadRequestForInvalidPayload() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "hotelId": "",
                                  "checkIn": "31/12/2023",
                                  "checkOut": "29/12/2023",
                                  "ages": [-1]
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturnSearchCount() throws Exception {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));
        Search search = new Search("search-123", criteria);
        when(getSearchCountUseCase.execute("search-123")).thenReturn(new SearchCountResult(search, 2L));

        mockMvc.perform(get("/count").param("searchId", "search-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value("search-123"))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.search.hotelId").value("1234aBc"))
                .andExpect(jsonPath("$.search.checkIn").value("29/12/2023"));
    }
}
