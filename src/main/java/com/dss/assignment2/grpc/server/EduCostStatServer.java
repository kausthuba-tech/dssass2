package com.dss.assignment2.grpc.server;
import com.dss.assignment2.grpc.service.EduCostStatGrpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class EduCostStatServer {


    @Value("${grpc.server.port}")
    private int port;

    @Autowired
    private EduCostStatGrpcServiceImpl eduCostStatGrpcService;

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port)
                .addService(eduCostStatGrpcService)
                .build();

        server.start();
        System.out.println("gRPC server started on port " + port);
        server.awaitTermination();
    }
}
