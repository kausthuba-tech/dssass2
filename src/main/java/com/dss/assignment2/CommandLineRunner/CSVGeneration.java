package com.dss.assignment2.CommandLineRunner;

import com.dss.assignment2.service.EduCostStatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class CSVGeneration implements CommandLineRunner {

    @Autowired
    private EduCostStatServiceImpl eduCostStatService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        eduCostStatService.createCollectionFromCsv();
        Integer year = 2020;
        String state = "West Virginia";
        String type = "Private";
        String length = "4-year";
        String expense = "Fees/Tuition";
       // eduCostStatService.saveEduCostStatQueryOne(year, state, type, length, expense);
        //eduCostStatService.saveEduCostStatQueryTwo(year, type, length);
        //eduCostStatService.saveEduCostStatQueryThree(year, type, length);
        //eduCostStatService.saveEduCostStatQueryFour(year, type, length);
        //eduCostStatService.saveEduCostStatQueryFive(year, type, length);
    }
}
