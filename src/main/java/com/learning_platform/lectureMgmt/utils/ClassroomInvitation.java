package com.learning_platform.lectureMgmt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import com.learning_platform.utils.EncodeInformation;

public class ClassroomInvitation {

    private ClassroomInvitation() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String generateInviteLink(String classroomId, String expiry) {
        Map<String, String> map = new HashMap<>();
        map.put("classroomId", classroomId);
        map.put("expiry", expiry);
        try {
            return EncodeInformation.encodeInformation(objectMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> decodeInviteLink(String inviteLink) {
        try {
            return objectMapper.readValue(EncodeInformation.decodeInformation(inviteLink),
                    new TypeReference<Map<String, String>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
