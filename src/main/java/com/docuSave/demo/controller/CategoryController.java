package com.docuSave.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docuSave.demo.model.Category;
import com.docuSave.demo.service.CategoryService;

@RestController
@CrossOrigin("http://192.168.29.43:8080")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/home")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }

}
