package com.expensetracker.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.backend.entity.Expense;
import java.util.*;
public interface ExpenseRepository extends JpaRepository<Expense,Long>{
    List<Expense> findByUserEmail(String email);
}
