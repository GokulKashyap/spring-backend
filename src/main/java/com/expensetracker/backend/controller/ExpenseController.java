package com.expensetracker.backend.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.expensetracker.backend.dto.ExpenseRequest;
import com.expensetracker.backend.entity.Expense;
import com.expensetracker.backend.repository.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
@CrossOrigin
public class ExpenseController {

    @Autowired 
    private ExpenseRepository expenseRepository;

    @GetMapping
public ResponseEntity<List<Expense>> getUserExpenses(HttpServletRequest request) {
    String email = (String) request.getAttribute("email");
    List<Expense> expenses = expenseRepository.findByUserEmail(email);
    return ResponseEntity.ok(expenses);
}

    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest, HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    Expense expense = Expense.builder()
            .title(expenseRequest.getTitle())
            .amount(expenseRequest.getAmount())
            .category(expenseRequest.getCategory())
            .date(expenseRequest.getDate())
            .userEmail(email)
            .build();

    expenseRepository.save(expense);

    return ResponseEntity.ok("Expense saved!");

    }
    @PutMapping("/{id}")
public ResponseEntity<?> updateExpense(@PathVariable Long id,
                                       @RequestBody ExpenseRequest req,
                                       HttpServletRequest request) {
    String email = (String) request.getAttribute("email");
    Optional<Expense> optional = expenseRepository.findById(id);

    if (optional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Expense exp = optional.get();
    if (!exp.getUserEmail().equals(email)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    exp.setTitle(req.getTitle());
    exp.setAmount(req.getAmount());
    exp.setCategory(req.getCategory());
    exp.setDate(req.getDate());

    expenseRepository.save(exp);
    return ResponseEntity.ok("Updated successfully");
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteExpense(@PathVariable Long id, HttpServletRequest request) {
    String email = (String) request.getAttribute("email");

    Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Expense not found"));

    if (!expense.getUserEmail().equals(email)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to delete this expense.");
    }

    expenseRepository.delete(expense);

    return ResponseEntity.ok("Expense deleted successfully");
}



}
