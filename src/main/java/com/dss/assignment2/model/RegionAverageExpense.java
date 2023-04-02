package com.dss.assignment2.model;

public class RegionAverageExpense {

    private String region;
    private double averageExpense;


    public RegionAverageExpense(String region, double averageExpense) {
;
        this.region = region;
        this.averageExpense = averageExpense;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getAverageExpense() {
        return averageExpense;
    }

    public void setAverageExpense(double averageExpense) {
        this.averageExpense = averageExpense;
    }
}
