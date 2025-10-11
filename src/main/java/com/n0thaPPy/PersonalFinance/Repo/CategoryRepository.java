package com.n0thaPPy.PersonalFinance.Repo;

import com.n0thaPPy.PersonalFinance.Model.CategoryModel;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel,Integer> {

    List<CategoryModel> findByType(TransactionType type);
}
