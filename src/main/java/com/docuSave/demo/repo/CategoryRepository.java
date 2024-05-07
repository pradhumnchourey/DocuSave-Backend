package com.docuSave.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.docuSave.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query("SELECT NEW com.docuSave.demo.model.Category(c.categoryId, c.categoryName) FROM Category c")
    List<Category> findAllCategories();
}
