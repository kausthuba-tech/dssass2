package com.dss.assignment2.repository;

import com.dss.assignment2.model.*;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EduCostStatRepo extends MongoRepository<EduCostStat, String> {

    @Query("{'year' : ?0, 'state' : ?1, 'type' : ?2, 'length' : ?3, 'expense' : ?4}")
    EduCostStatQueryOne findCost(Integer year, String state, String type, String length, String expense);

    @Aggregation(pipeline = {
            "{$match: {year: ?0, state: ?1, type: ?2, length: ?3}}",
            "{$group: {_id: '$expense', totalCost: {$sum: '$value'}}}"
    })
    List<TotalExpensesCost> findTotalCostByYearStateTypeLength(Integer year, String state, String type, String length);

    @Aggregation(pipeline = {
            "{$match: {year: ?0, type: ?1, length: ?2}}",
            "{$group: {_id: '$state', totalExpense: {$sum: '$value'}}}",
            "{$sort: {totalExpense: -1}}",
            "{$limit: 5}",
            "{$project: {state: '$_id', _id: 0, totalExpense: 1}}"
    })
    List<StateExpense> findTop5ExpensiveStates(Integer year, String type, String length);

    @Aggregation(pipeline = {
            "{$match: {year: ?0, type: ?1, length: ?2}}",
            "{$group: {_id: '$state', totalExpense: {$sum: '$value'}}}",
            "{$sort: {totalExpense: 1}}",
            "{$limit: 5}",
            "{$project: {state: '$_id', _id: 0, totalExpense: 1}}"
    })
    List<StateExpense> findTop5EconomicStates(Integer year, String type, String length);

    @Aggregation(pipeline = {
            "{$match: {year: ?0, type: ?1, length: ?2}}",
            "{$group: {_id: '$state', totalExpense: {$sum: '$value'}}}",
            "{$project: {state: '$_id', _id: 0, totalExpense: 1}}"
    })
    List<StateExpense> findStatsByYearAndTypeAndLength(Integer year, String type, String length);

}
