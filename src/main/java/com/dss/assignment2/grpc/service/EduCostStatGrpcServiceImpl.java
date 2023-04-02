package com.dss.assignment2.grpc.service;

import com.dss.assignment2.model.*;
import com.dss.assignment2.service.EduCostStatServiceImpl;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.stub.StreamObserver;
import com.dss.assignment2.proto.*;

import java.util.stream.Collectors;

@GRpcService
public class EduCostStatGrpcServiceImpl extends EduCostStatServiceGrpc.EduCostStatServiceImplBase {

    @Autowired
    private EduCostStatServiceImpl eduCostStatService;

    @Override
    public void saveEduCostStatQueryOne(SaveEduCostStatQueryOneRequest request, StreamObserver<SaveEduCostStatQueryOneResponse> responseObserver) {
        EduCostStatQueryOne queryOne = eduCostStatService.saveEduCostStatQueryOne(request.getYear(), request.getState(), request.getType(), request.getLength(), request.getExpense());

        // Convert the EduCostStatQueryOne object to a protobuf message
        com.dss.assignment2.proto.EduCostStatQueryOne eduCostStatQueryOneProto = com.dss.assignment2.proto.EduCostStatQueryOne.newBuilder()
                .setId(queryOne.getId())
                .setYear(queryOne.getYear())
                .setState(queryOne.getState())
                .setType(queryOne.getType())
                .setLength(queryOne.getLength())
                .setExpense(queryOne.getExpense())
                .setValue(queryOne.getValue())
                .build();

        // Build and send the response
        SaveEduCostStatQueryOneResponse response = SaveEduCostStatQueryOneResponse.newBuilder()
                .setEduCostStatQueryOne(eduCostStatQueryOneProto)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveEduCostStatQueryTwo(SaveEduCostStatQueryTwoRequest request, StreamObserver<SaveEduCostStatQueryTwoResponse> responseObserver) {
        EduCostStatQueryTwo queryTwo = eduCostStatService.saveEduCostStatQueryTwo(request.getYear(), request.getType(), request.getLength());

        // Convert the Java objects to protobuf messages
        SaveEduCostStatQueryTwoResponse.Builder responseBuilder = SaveEduCostStatQueryTwoResponse.newBuilder();

        com.dss.assignment2.proto.EduCostStatQueryTwo protoQueryTwo = com.dss.assignment2.proto.EduCostStatQueryTwo.newBuilder()
                .setId(queryTwo.getId())
                .setYear(queryTwo.getYear())
                .setType(queryTwo.getType())
                .setLength(queryTwo.getLength())
                .addAllTop5States(
                        queryTwo.getTop5States().stream()
                                .map(stateExpense -> StateExpense.newBuilder()
                                        .setState(stateExpense.getState())
                                        .setTotalExpense(stateExpense.getTotalExpense())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();

        responseBuilder.setEduCostStatQueryTwo(protoQueryTwo);

        // Build and send the response
        SaveEduCostStatQueryTwoResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveEduCostStatQueryThree(SaveEduCostStatQueryThreeRequest request, StreamObserver<SaveEduCostStatQueryThreeResponse> responseObserver) {
        EduCostStatQueryThree queryThree = eduCostStatService.saveEduCostStatQueryThree(request.getYear(), request.getType(), request.getLength());

        // Convert the Java objects to protobuf messages
        SaveEduCostStatQueryThreeResponse.Builder responseBuilder = SaveEduCostStatQueryThreeResponse.newBuilder();

        com.dss.assignment2.proto.EduCostStatQueryThree protoQueryThree = com.dss.assignment2.proto.EduCostStatQueryThree.newBuilder()
                .setId(queryThree.getId())
                .setYear(queryThree.getYear())
                .setType(queryThree.getType())
                .setLength(queryThree.getLength())
                .addAllTop5States(
                        queryThree.getTop5States().stream()
                                .map(stateExpense -> StateExpense.newBuilder()
                                        .setState(stateExpense.getState())
                                        .setTotalExpense(stateExpense.getTotalExpense())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();

        responseBuilder.setEduCostStatQueryThree(protoQueryThree);

        // Build and send the response
        SaveEduCostStatQueryThreeResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveEduCostStatQueryFour(SaveEduCostStatQueryFourRequest request, StreamObserver<SaveEduCostStatQueryFourResponse> responseObserver) {
        EduCostStatQueryFour queryFour = eduCostStatService.saveEduCostStatQueryFour(request.getBaseYear(), request.getType(), request.getLength());

        // Convert the Java objects to protobuf messages
        SaveEduCostStatQueryFourResponse.Builder responseBuilder = SaveEduCostStatQueryFourResponse.newBuilder();

        com.dss.assignment2.proto.EduCostStatQueryFour protoQueryFour = com.dss.assignment2.proto.EduCostStatQueryFour.newBuilder()
                .setId(queryFour.getId())
                .setYear(queryFour.getYear())
                .setType(queryFour.getType())
                .setLength(queryFour.getLength())
                .addAllTop5StateExpenseGrowths(
                        queryFour.getTop5StateExpenseGrowths().stream()
                                .map(stateExpenseGrowth -> StateExpenseGrowth.newBuilder()
                                        .setState(stateExpenseGrowth.getState())
                                        .setTotalExpense(stateExpenseGrowth.getTotalExpense())
                                        .setGrowthRate(stateExpenseGrowth.getGrowthRate())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();

        responseBuilder.setEduCostStatQueryFour(protoQueryFour);

        // Build and send the response
        SaveEduCostStatQueryFourResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveEduCostStatQueryFive(SaveEduCostStatQueryFiveRequest request, StreamObserver<SaveEduCostStatQueryFiveResponse> responseObserver) {
        EduCostStatQueryFive queryFive = eduCostStatService.saveEduCostStatQueryFive(request.getYear(), request.getType(), request.getLength());

        // Convert the Java objects to protobuf messages
        SaveEduCostStatQueryFiveResponse.Builder responseBuilder = SaveEduCostStatQueryFiveResponse.newBuilder();

        com.dss.assignment2.proto.EduCostStatQueryFive protoQueryFive = com.dss.assignment2.proto.EduCostStatQueryFive.newBuilder()
                .setId(queryFive.getId())
                .setYear(queryFive.getYear())
                .setType(queryFive.getType())
                .setLength(queryFive.getLength())
                .addAllRegionExpenses(
                        queryFive.getRegionExpenses().stream()
                                .map(regionAverageExpense -> RegionAverageExpense.newBuilder()
                                        .setRegion(regionAverageExpense.getRegion())
                                        .setAverageExpense(regionAverageExpense.getAverageExpense())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();

        responseBuilder.setEduCostStatQueryFive(protoQueryFive);

        // Build and send the response
        SaveEduCostStatQueryFiveResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
