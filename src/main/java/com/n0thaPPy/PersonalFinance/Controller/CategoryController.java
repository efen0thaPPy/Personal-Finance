package com.n0thaPPy.PersonalFinance.Controller;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.Service.CategoryService;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
   private CategoryService service;


    @GetMapping("/categories")
    public ResponseEntity<List<CategoryModel>> getCategories()
    {
       List<CategoryModel>categories=service.getCategories();

       return ResponseEntity.ok(categories);
    }
    @GetMapping("/category/{type}")
    public ResponseEntity<List<CategoryModel>>getCategory(@PathVariable TransactionType type)
    {
       List<CategoryModel>categories=service.getCategoriesByType(type);

       return ResponseEntity.ok(categories);
    }
    @GetMapping("/transactionTypes")
    public TransactionType[] getTypes()
    {
       return TransactionType.values();

    }



}

