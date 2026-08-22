package com.mindata.hotelsearch.infraestructure.adapter.out.memory;

import com.mindata.hotelsearch.application.port.out.SaveSearchPort;
import com.mindata.hotelsearch.application.port.out.SearchQueryPort;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySearchRepo implements SaveSearchPort, SearchQueryPort {
    private final Map<String, Search> store = new ConcurrentHashMap<>();

    //guarda en memoria
    @Override
    public void save(Search search) {
        store.put(search.searchId(), search);
    }

    //encuantra la busqueda por id en la memoria
    @Override
    public Optional<Search> findById(String searchId) {
        return Optional.ofNullable(store.get(searchId));
    }

    //simula cuantas busquedas hay en memoria
    @Override
    public long countByCriteria(SearchCriteria criteria) {
        return store.values().stream()
                .filter(search -> search.criteria().equals(criteria))
                .count();
    }
}
