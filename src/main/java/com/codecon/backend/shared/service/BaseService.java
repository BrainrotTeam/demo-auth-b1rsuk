package com.codecon.backend.shared.service;


import com.codecon.backend.shared.model.BaseModel;
import com.codecon.backend.shared.repository.BaseRepository;
import com.codecon.backend.shared.service.exception.EntityIdCannotBeNullException;
import com.codecon.backend.shared.service.exception.EntityIdUpdateNotAllowedException;
import com.codecon.backend.shared.service.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseService<T extends BaseModel> implements BaseRepository<T>  {

    BaseRepository<T> baseRepository;

    public T update(T entity, Long entityId) {
        validateNonNullId(entityId);

        T dbEntity = baseRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID: " + entityId + " not found"));
        updateEntityProperties(entity, dbEntity);

        return save(dbEntity);
    }

    public T create(T entity) {
        Long entityId = entity.getId();
        validateIdIsNull(entityId);

        return save(entity);
    }

    @Override
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Override
    public void deleteAllById(Long id) {
        Optional.ofNullable(id)
                .ifPresent(baseRepository::deleteAllById);
    }

    @Override
    public List<T> findAllByIdIn(List<Long> ids) {
        return ids.isEmpty()? new ArrayList<>() : baseRepository.findAllByIdIn(ids);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(baseRepository::findById);
    }

    @Override
    public void deleteById(Long id) {
        Optional.ofNullable(id)
                .ifPresent(baseRepository::deleteById);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return (List<T>) baseRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    private boolean isUpdate(T entity) {
        Long entityId = entity.getId();
        return Objects.nonNull(entityId);
    }

    public void validateNonNullId(Long id) {
        if (Objects.isNull(id)) {
            throw new EntityIdCannotBeNullException();
        }
    }

    public void validateIdIsNull(Long id) {
        if (!Objects.isNull(id)) {
            throw new EntityIdUpdateNotAllowedException();
        }
    }

    private void updateEntityProperties(T entity, T dbEntity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        modelMapper.map(entity, dbEntity);
    }


}
