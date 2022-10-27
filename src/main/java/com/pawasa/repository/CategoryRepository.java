package com.pawasa.repository;

import com.pawasa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
//    @Query(value = "select c from Category c where c.parentCategory.id = ?1")
//    Set<Category> findChildListCategory(Long categoryId);
    List<Category> findByParentCategory(Category parentCategory);


}
