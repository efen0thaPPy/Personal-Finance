package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.Repo.CategoryRepository;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/api")
public class CategoryService {
    @Autowired
    private CategoryRepository repo;


    public List<CategoryModel> getCategories() {
       return repo.findAll();
    }

    public List<CategoryModel> getCategoriesByType(TransactionType type) {
        return repo.findByType(type);
    }


}
