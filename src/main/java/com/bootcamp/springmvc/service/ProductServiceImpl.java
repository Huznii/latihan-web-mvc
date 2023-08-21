package com.bootcamp.springmvc.service;

import com.bootcamp.springmvc.entity.ProductEntity;
import com.bootcamp.springmvc.model.ProductDto;
import com.bootcamp.springmvc.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepo repo;

    public ProductServiceImpl(ProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<ProductEntity> getAll() {
        return this.repo.findAll();
    }

    @Override
    public ProductEntity getById(Integer id) {
        return this.repo.findById(id).orElse(null);
    }

    @Override
    public ProductEntity save(ProductDto request) {
        ProductEntity result = new ProductEntity();
        // copy property
        BeanUtils.copyProperties(request,result);
        try{
            // proses simpan ke database
            this.repo.save(result);
            log.info("Save product to database is successfully");
            // kembalikan nilainya dengan object result
            return result;
        }catch (Exception ex) {
            // jika saat save terjadi error
            log.error("Save product to database is failed, error: {}", ex.getMessage());
            // kembalikan nilai null
            return null;
        }
    }

    @Override
    public ProductEntity update(Integer id, ProductDto request) {
        // get data by id dari database
        ProductEntity entity = this.repo.findById(id).orElse(null);

        // validasai data
        if(entity == null) {
            log.warn("Product with id: {} not found", id);
            return null;
        }

        // copy property
        BeanUtils.copyProperties(request, entity);
        try{
            // proses update ke database
            this.repo.save(entity);
            log.info("Update product to database is successfully");
            // kembalikan nilainya
            return entity;
        }catch (Exception ex) {
            // jika saat update terjadi error
            log.error("Update product to database is failed, error: {}", ex.getMessage());
            // kembalikan nilai null
            return null;
        }
    }

    @Override
    public ProductEntity delete(Integer id) {
        // get data dari database
        ProductEntity entity = this.repo.findById(id).orElse(null);
        if(entity == null) {
            log.warn("Product wit id: {} not found", id);
            return null;
        }

        // kalau data ketemu dengan id yang di param
        try{
            // proses delete ke database
            this.repo.delete(entity);
            log.info("Delete product from database is successfully");
            return entity;
        }catch (Exception ex){
            // jika saat delete terjadi error
            log.error("Delete product from database is failed, error: {}", ex.getMessage());
            return null;
        }
    }
}
