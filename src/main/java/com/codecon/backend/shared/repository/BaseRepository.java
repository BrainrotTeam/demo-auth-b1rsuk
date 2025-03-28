package com.codecon.backend.shared.repository;

import com.codecon.backend.shared.model.BaseModel;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@Transactional
public interface BaseRepository<E extends BaseModel> extends PagingAndSortingRepository<E, Long> {
    E save(E entity);
    List<E> findAllByIdIn(List<Long> ids);
    List<E> findAll();
    Optional<E> findById(Long id);

    void deleteById(Long id);
    void deleteAllById(Long id);

}
