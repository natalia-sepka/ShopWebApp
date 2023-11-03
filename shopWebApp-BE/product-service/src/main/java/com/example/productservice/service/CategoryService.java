package com.example.productservice.service;

import com.example.productservice.entity.Category;
import com.example.productservice.entity.CategoryDTO;
import com.example.productservice.exceptions.ObjectExistsDBException;
import com.example.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    public void create(CategoryDTO categoryDTO) throws ObjectExistsDBException {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setShortId(UUID.randomUUID().toString().replace("-", "").substring(0, 12));

        categoryRepository.findByName(category.getName()).ifPresent(value -> {
            throw new ObjectExistsDBException("Category with this name already exists in the database");
        });
        categoryRepository.save(category);
    }

    public Optional<Category> findCategoryByShortId(String shortId) {
        return categoryRepository.findByShortId(shortId);
    }
}
