package com.learning_platform.lectureMgmt.services.graphqlResolver.queries.externalResolver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.learning_platform.dtos.UserDto;
import com.learning_platform.lectureMgmt.models.ClassroomModel;
import com.learning_platform.lectureMgmt.services.grpcClients.AuthServiceGrpcClient;
import com.learning_platform.grpc.GetUsersInfoReply;

@Controller
public class UserResolver {

    @Autowired
    private AuthServiceGrpcClient authServiceGrpcClient;

    @SchemaMapping(typeName = "Classroom", field = "students")
    public List<UserDto> getStudents(ClassroomModel classroom) {
        System.out.println("classroom.getStudentIds() " + classroom.getStudentIds());
        if (classroom.getStudentIds() == null || classroom.getStudentIds().isEmpty()) {
            return new ArrayList<>();
        }
        GetUsersInfoReply reply = authServiceGrpcClient.getUsersInfo(classroom.getStudentIds());
        System.out.println("reply " + reply);
        return reply.getUserInfoList().stream().map(userInfo -> UserDto.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .build()).collect(Collectors.toList());
    }
}
