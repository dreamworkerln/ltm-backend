package ru.geekbrains.handmade.ltmbackend.core.services.base;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public abstract class BaseRepoAccessService<T> {

    //protected EntityGraph defaultEntityGraph = EntityGraphs.empty();


    private final CustomRepository<T, Long> baseRepository;

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    public BaseService(CustomRepository<T, Long> baseRepository) {
//        this.baseRepository = baseRepository;
//    }

    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    public Optional<T> findById(Long id, EntityGraph entityGraph) {
        return baseRepository.findById(id, entityGraph);
    }

//    public Optional<T> findByIdEager(Long id) {
//        return baseRepository.findById(id, EntityGraphs.empty());
//    }


    public T findByIdOrError(Long id) {
        return baseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entity by id: " + id + " not found"));
    }

    public T findByIdOrError(Long id, EntityGraph entityGraph) {
        return baseRepository.findById(id, entityGraph)
            .orElseThrow(() -> new IllegalArgumentException("Entity by id: " + id + " not found"));
    }

    public Optional<T> findOne(Specification<T> spec) {
        return baseRepository.findOne(spec);
    }

    public List<T> findAllById(List<Long> listId) {
        return baseRepository.findAllById(listId);
    }

    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    public Page<T> findAll(Specification<T> spec, PageRequest pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    public T save(T t) {
        return baseRepository.save(t);
    }

    public List<T> saveAll(Iterable<T> list) {
        return baseRepository.saveAll(list);
    }

    public void delete(T t) {
        baseRepository.delete(t);
    }

}
