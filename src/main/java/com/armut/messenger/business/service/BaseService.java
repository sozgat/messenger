package com.armut.messenger.business.service;

import com.armut.messenger.business.model.AbstractBaseModel;

import java.util.List;

public interface BaseService<T extends AbstractBaseModel> {

    T findById(Long primaryKey);
    List<T> findAll();
    T save(T entity);
    T saveAndFlush(T entity);
    void delete(T entity);
    void deleteById(Long id);

}
