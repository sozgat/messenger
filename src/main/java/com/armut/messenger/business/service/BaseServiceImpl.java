package com.armut.messenger.business.service;

import com.armut.messenger.business.model.AbstractBaseModel;
import com.armut.messenger.business.repository.BaseJPARepository;

import java.util.List;

public class BaseServiceImpl<T extends AbstractBaseModel> implements BaseService<T> {

    private final BaseJPARepository baseJPARepository;

    public BaseServiceImpl(BaseJPARepository baseJPARepository) {
        this.baseJPARepository = baseJPARepository;
    }


    @Override
    public T findById(Long id) {
        return (T) baseJPARepository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return baseJPARepository.findAll();
    }

    @Override
    public T save(T entity) {
        return (T) baseJPARepository.save(entity);
    }

    @Override
    public T saveAndFlush(T entity) {
        return (T) baseJPARepository.saveAndFlush(entity);
    }

    @Override
    public void delete(T entity) {
        baseJPARepository.delete(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        baseJPARepository.deleteById(id);
    }
}
