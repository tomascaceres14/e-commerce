package com.tomasdev.akhanta.service;

import org.springframework.data.domain.Page;

public interface iService<T> {
    T save(T req);
    Page<T> findAll(int page);
    T findById(String id);
    T updateById(String id, T req);
    void deleteById(String id);
}