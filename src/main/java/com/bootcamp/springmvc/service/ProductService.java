package com.bootcamp.springmvc.service;

import com.bootcamp.springmvc.entity.ProductEntity;
import com.bootcamp.springmvc.model.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAll();
    ProductEntity getById(Integer id);
    ProductEntity save(ProductDto request);
    ProductEntity update(Integer id, ProductDto request);
    ProductEntity delete(Integer id);
}
