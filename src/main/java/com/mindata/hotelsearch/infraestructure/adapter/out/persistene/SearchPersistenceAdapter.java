package com.mindata.hotelsearch.infraestructure.adapter.out.persistene;

import com.mindata.hotelsearch.domain.port.SaveSearchPort;
import com.mindata.hotelsearch.domain.port.SearchQueryPort;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SearchPersistenceAdapter implements SaveSearchPort, SearchQueryPort {
    private final SearchJpaRepository searchJpaRepository;
    private final SearchEntityMapper searchEntityMapper;

    public SearchPersistenceAdapter(SearchJpaRepository searchJpaRepository, SearchEntityMapper searchEntityMapper) {
        this.searchJpaRepository = searchJpaRepository;
        this.searchEntityMapper = searchEntityMapper;
    }

    @Override
    @Transactional
    public void save(Search search) {
        SearchEntity entity = searchEntityMapper.toEntity(search);
        searchJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Search> findById(String searchId) {
        return searchJpaRepository.findById(searchId).map(searchEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByCriteria(SearchCriteria criteria) {
        String ages = searchEntityMapper.serializeAges(criteria.ages());
        return searchJpaRepository.countIdenticalSearches(
                criteria.hotelId(),
                criteria.checkIn(),
                criteria.checkOut(),
                ages
        );
    }
}
