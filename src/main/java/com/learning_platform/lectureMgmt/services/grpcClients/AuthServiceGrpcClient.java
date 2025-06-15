package com.learning_platform.lectureMgmt.services.grpcClients;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learning_platform.grpc.GetUsersInfoGrpc;
import com.learning_platform.grpc.GetUsersInfoRequest;
import com.learning_platform.grpc.GetUsersInfoReply;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class AuthServiceGrpcClient {

    @GrpcClient("userService")
    private GetUsersInfoGrpc.GetUsersInfoBlockingStub getUsersInfoBlockingStub;

    public GetUsersInfoReply getUsersInfo(List<String> userIds) {
        GetUsersInfoRequest request = GetUsersInfoRequest.newBuilder()
                .addAllUserIds(userIds)
                .build();
        return getUsersInfoBlockingStub.getUsersInfo(request);
    }

}
