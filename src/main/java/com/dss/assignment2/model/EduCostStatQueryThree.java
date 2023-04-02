package com.dss.assignment2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EduCostStatQueryThree")
public class EduCostStatQueryThree {

    @Id
    private String id;

    private Integer year;
    private String type;
    private String length;
    private List<StateExpense> top5States;

    public EduCostStatQueryThree() {
    }

    public EduCostStatQueryThree(String id, Integer year, String type, String length, List<StateExpense> top5States) {
        this.id = id;
        this.year = year;
        this.type = type;
        this.length = length;
        this.top5States = top5States;
    }

    public EduCostStatQueryThree(Integer year, String type, String length, List<StateExpense> top5States) {
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

    public List<StateExpense> getTop5States() {
        return top5States;
    }

    public void setTop5States(List<StateExpense> top5States) {
        this.top5States = top5States;
    }

}

