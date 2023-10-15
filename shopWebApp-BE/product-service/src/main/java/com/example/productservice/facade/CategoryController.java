package com.example.productservice.facade;

import com.example.productservice.entity.CategoryDTO;
import com.example.productservice.entity.Response;
import com.example.productservice.exceptions.ObjectExistsDBException;
import com.example.productservice.mediator.CategoryMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryMediator categoryMediator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> getCategory() {
        return categoryMediator.getCategory();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryMediator.createCategory(categoryDTO);
        } catch (ObjectExistsDBException e) {
           return ResponseEntity.status(400).body(new Response("Object already exists in the database."));
        }
        return ResponseEntity.ok(new Response("Operation end with success."));
    }
}
