package com.expensetracker.backend.dto;

import lombok.Data;

@Data
public class ExpenseRequest {
    private String title;
    private double amount;
    private String category;
    private String date;
}
