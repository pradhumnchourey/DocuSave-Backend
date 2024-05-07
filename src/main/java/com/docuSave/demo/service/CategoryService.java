package com.docuSave.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docuSave.demo.model.Category;
import com.docuSave.demo.repo.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAllCategories();
    }

    public Category getCategoryById(int categoryId){
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
