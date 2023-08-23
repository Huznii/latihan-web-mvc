package com.bootcamp.springmvc.controller;

import com.bootcamp.springmvc.entity.ProductEntity;
import com.bootcamp.springmvc.model.ProductDto;
import com.bootcamp.springmvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("product/index");

        String judul = "List Product";
        view.addObject("dataJudul", judul);

        // get data dari service, service => repo, repo => database
        List<ProductEntity> dataProduct = service.getAll();
        // kirim data ke view
        view.addObject("listProduct", dataProduct);
        // menghitung jumlah data
        int jmlData = dataProduct.size();
        // kirim data ke view
        view.addObject("jmlData", jmlData);
        return view;
    }

    @GetMapping("add")
    public ModelAndView add() {
        return new ModelAndView("product/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute ProductDto request){
        this.service.save(request);
        return new ModelAndView("redirect:/product");

    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id) {
        ProductEntity productEntity = this.service.getById(id);
        if (productEntity == null) {
            return new ModelAndView("redirect:/product");
        }
        ModelAndView view = new ModelAndView("product/edit");
        view.addObject("product", productEntity);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute ProductDto request) {
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/product");
    }


    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute ProductDto request) {
        ProductEntity productDto = service.getById(request.getId());
        if (productDto == null) {
            return new ModelAndView("redirect:/product");
        }
        this.service.delete(request.getId());
        return new ModelAndView("redirect:/product");
    }
}
