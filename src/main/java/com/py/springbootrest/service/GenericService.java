package com.py.springbootrest.service;

import com.py.springbootrest.dto.CustomResponse;
import org.springframework.data.domain.Page;

public interface GenericService<T> {

    CustomResponse<Page<T>> findAll(int offset, int page, String orderBy, String orderDir);
    CustomResponse<T> findById(Long id);
    CustomResponse<T> create(T entity);
    CustomResponse<T> update(Long id, T entity);
    CustomResponse<Void> delete(Long id);
}
