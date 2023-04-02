package com.dss.assignment2.model;

public class StateExpense {
    private String state;
    private Integer totalExpense;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Integer totalExpense) {
        this.totalExpense = totalExpense;
    }

    public StateExpense(String state, Integer totalExpense) {
        this.state = state;
        this.totalExpense = totalExpense;
    }

}
