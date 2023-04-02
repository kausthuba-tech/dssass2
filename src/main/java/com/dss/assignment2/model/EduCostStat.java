package com.dss.assignment2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EduCostStat")
public class EduCostStat {

    @Id
    private String id;
    private Integer year;
    private String state;
    private String type;
    private String length;
    private String expense;
    private Integer value;

    public EduCostStat() {
    }

    public EduCostStat(String id, Integer year, String state, String type, String length, String expense, Integer value) {
        this.id = id;
        this.year = year;
        this.state = state;
        this.type = type;
        this.length = length;
        this.expense = expense;
        this.value = value;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EduCostStat{" +
                "id='" + id + '\'' +
                ", year=" + year +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", length='" + length + '\'' +
                ", expense='" + expense + '\'' +
                ", value=" + value +
                '}';
    }
}
