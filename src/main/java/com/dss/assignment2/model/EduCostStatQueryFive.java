package com.dss.assignment2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EduCostStatQueryFive")
public class EduCostStatQueryFive {
    @Id
    private String id;

    private Integer year;
    private String type;
    private String length;
    private List<RegionAverageExpense> regionExpenses;


    public EduCostStatQueryFive() {
    }

    public EduCostStatQueryFive(String id, Integer year, String type, String length,List<RegionAverageExpense> regionExpenses) {
        this.id = id;
        this.year = year;
        this.type = type;
        this.length = length;
        this.regionExpenses = regionExpenses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
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

    public List<RegionAverageExpense> getRegionExpenses() {
        return regionExpenses;
    }

    public void setRegionExpenses(List<RegionAverageExpense> regionExpenses) {
        this.regionExpenses = regionExpenses;
    }
}
