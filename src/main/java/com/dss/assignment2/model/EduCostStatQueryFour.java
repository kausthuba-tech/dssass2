package com.dss.assignment2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "EduCostStatQueryFour")
public class EduCostStatQueryFour {

    @Id
    private String id;
    private int year;
    private String type;
    private String length;
    private List<StateExpenseGrowth> top5StateExpenseGrowths;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<StateExpenseGrowth> getTop5StateExpenseGrowths() {
        return top5StateExpenseGrowths;
    }

    public void setTop5StateExpenseGrowths(List<StateExpenseGrowth> top5StateExpenseGrowths) {
        this.top5StateExpenseGrowths = top5StateExpenseGrowths;
    }
}

