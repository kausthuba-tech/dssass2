package com.dss.assignment2.service;

import com.dss.assignment2.model.*;
import com.dss.assignment2.repository.EduCostStatRepo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EduCostStatServiceImpl {

    private final EduCostStatRepo eduCostStatRepo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public EduCostStatServiceImpl(EduCostStatRepo eduCostStatRepo, MongoTemplate mongoTemplate) {
        this.eduCostStatRepo = eduCostStatRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @Value("${csv.file.path}")
    private String csvFilePath;

    public void createCollectionFromCsv() {
        if (mongoTemplate.collectionExists("EduCostStat")) {
            long count = eduCostStatRepo.count();
            if (count > 0) {
                System.out.println("Collection already exists and it has data, skipping data import.");
                return;
            }
        }
        List<EduCostStat> eduCostStats = new ArrayList<>();

        try(CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            boolean firstLine = true;
            while((nextLine = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                EduCostStat eduCostStat = new EduCostStat();
                eduCostStat.setYear(Integer.parseInt(nextLine[0]));
                eduCostStat.setState(nextLine[1]);
                eduCostStat.setType(nextLine[2]);
                eduCostStat.setLength(nextLine[3]);
                eduCostStat.setExpense(nextLine[4]);
                eduCostStat.setValue(Integer.parseInt(nextLine[5]));
                eduCostStats.add(eduCostStat);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return;
        }
        eduCostStatRepo.saveAll(eduCostStats);

    }

    public EduCostStatQueryOne saveEduCostStatQueryOne(Integer year, String state, String type, String length, String expense) {
        EduCostStatQueryOne result = eduCostStatRepo.findCost(year, state, type, length, expense);
        EduCostStatQueryOne queryOne = new EduCostStatQueryOne();
        queryOne.setId(year + "_" + state + "_" + type + "_" + length + "_" + expense);
        queryOne.setYear(year);
        queryOne.setState(state);
        queryOne.setType(type);
        queryOne.setLength(length);
        queryOne.setExpense(expense);
        queryOne.setValue(result.getValue());
        mongoTemplate.save(queryOne, "EduCostStatQueryOne");
        return queryOne;
    }

    public EduCostStatQueryTwo saveEduCostStatQueryTwo(Integer year, String type, String length) {
        List<StateExpense> stateExpenses = eduCostStatRepo.findTop5ExpensiveStates(year, type, length).stream()
                .map(document -> new StateExpense(document.getState(), document.getTotalExpense()))
                .collect(Collectors.toList());
        EduCostStatQueryTwo queryTwo = new EduCostStatQueryTwo();
        queryTwo.setId(year + "_" + type + "_" + length);
        queryTwo.setYear(year);
        queryTwo.setType(type);
        queryTwo.setLength(length);
        queryTwo.setTop5States(stateExpenses);
        mongoTemplate.save(queryTwo, "EduCostStatQueryTwo");
        return queryTwo;
    }

    public EduCostStatQueryThree saveEduCostStatQueryThree(Integer year, String type, String length) {
        List<StateExpense> stateExpenses = eduCostStatRepo.findTop5EconomicStates(year, type, length).stream()
                .map(document -> new StateExpense(document.getState(), document.getTotalExpense()))
                .collect(Collectors.toList());
        EduCostStatQueryThree queryThree = new EduCostStatQueryThree();
        queryThree.setId(year + "_" + type + "_" + length);
        queryThree.setYear(year);
        queryThree.setType(type);
        queryThree.setLength(length);
        queryThree.setTop5States(stateExpenses);
        mongoTemplate.save(queryThree, "EduCostStatQueryThree");
        return queryThree;
    }

    public EduCostStatQueryFour saveEduCostStatQueryFour(Integer baseYear, String type, String length) {
        int[] years = {baseYear - 1, baseYear - 3, baseYear - 5};
        List<StateExpenseGrowth> stateExpenseGrowths = new ArrayList<>();
        for (int year : years) {
            List<StateExpense> expenses = eduCostStatRepo.findStatsByYearAndTypeAndLength(year, type, length);
            for (StateExpense expense : expenses) {
                StateExpenseGrowth stateExpenseGrowth = new StateExpenseGrowth();
                stateExpenseGrowth.setState(expense.getState());
                stateExpenseGrowth.setTotalExpense(expense.getTotalExpense());
                List<TotalExpensesCost> baseExpenseValues = eduCostStatRepo.findTotalCostByYearStateTypeLength(baseYear, expense.getState(), type, length);
                int baseExpense = baseExpenseValues.stream().mapToInt(TotalExpensesCost::getTotalCost).sum();
                double growthRate = ((double) baseExpense - expense.getTotalExpense()) / (double) expense.getTotalExpense() * 100.0;
                stateExpenseGrowth.setGrowthRate(growthRate);
                stateExpenseGrowths.add(stateExpenseGrowth);
            }
        }
        List<StateExpenseGrowth> top5StateExpenseGrowths = stateExpenseGrowths.stream()
                .sorted(Comparator.comparingDouble(StateExpenseGrowth::getGrowthRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
        EduCostStatQueryFour queryFour = new EduCostStatQueryFour();
        queryFour.setId(baseYear + "_" + type + "_" + length);
        queryFour.setYear(baseYear);
        queryFour.setType(type);
        queryFour.setLength(length);
        queryFour.setTop5StateExpenseGrowths(top5StateExpenseGrowths);
        mongoTemplate.save(queryFour, "EduCostStatQueryFour");
        return queryFour;
    }

    public EduCostStatQueryFive saveEduCostStatQueryFive(Integer year, String type, String length) {
        List<StateExpense> stateExpenses = eduCostStatRepo.findStatsByYearAndTypeAndLength(year, type, length);

        Map<String, List<Integer>> regionExpenses = new HashMap<>();
        stateExpenses.forEach(se -> {
            String region = getRegionByState(se.getState());
            regionExpenses.computeIfAbsent(region, k -> new ArrayList<>()).add(se.getTotalExpense());
        });

        List<RegionAverageExpense> regionAverageExpenses = new ArrayList<>();
        regionExpenses.forEach((region, expenses) -> {
            double average = expenses.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            RegionAverageExpense regionAverageExpense = new RegionAverageExpense(region, average);
            regionAverageExpenses.add(regionAverageExpense);
        });
        EduCostStatQueryFive queryFive = new EduCostStatQueryFive();
        queryFive.setId(year + "_" + type + "_" + length);
        queryFive.setYear(year);
        queryFive.setType(type);
        queryFive.setLength(length);
        queryFive.setRegionExpenses(regionAverageExpenses);
        mongoTemplate.save(queryFive, "EduCostStatQueryFive");
        return queryFive;
    }

    private String getRegionByState(String state) {
        ArrayList<String> northeast = new ArrayList<>(Arrays.asList("Connecticut", "Maine", "Massachusetts", "New Hampshire", "New Jersey", "New York", "Pennsylvania", "Rhode Island", "Vermont"));
        ArrayList<String> midwest = new ArrayList<>(Arrays.asList("Illinois", "Indiana", "Iowa", "Kansas", "Michigan", "Minnesota", "Missouri", "Nebraska", "North Dakota", "Ohio", "South Dakota", "Wisconsin"));
        ArrayList<String> south = new ArrayList<>(Arrays.asList("Alabama", "Arkansas", "Delaware", "Florida", "Georgia", "Kentucky", "Louisiana", "Maryland", "Mississippi", "North Carolina", "Oklahoma", "South Carolina", "Tennessee", "Texas", "Virginia", "West Virginia"));
        ArrayList<String> west = new ArrayList<>(Arrays.asList("Alaska", "Arizona", "California", "Colorado", "Idaho", "Montana", "Nevada", "New Mexico", "Oregon", "Utah", "Washington", "Wyoming", "District of Columbia"));
        ArrayList<String> pacific = new ArrayList<>(Arrays.asList("Alaska", "Hawaii", "Oregon", "Washington"));

        if (northeast.contains(state)) {
            return "Northeast Region";
        } else if (midwest.contains(state)) {
            return "Midwest Region";
        } else if (south.contains(state)) {
            return "South Region";
        } else if (west.contains(state)) {
            return "West Region";
        } else if (pacific.contains(state)) {
            return "Pacific Region";
        } else {
            return "Unknown Region";
        }
    }

}

