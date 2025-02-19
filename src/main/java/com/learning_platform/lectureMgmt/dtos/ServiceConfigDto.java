package com.learning_platform.lectureMgmt.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
@Builder
@Getter
@Setter
public class ServiceConfigDto {

    private String baseUrl;
    private Map<String, EndpointConfig> endpoints;

    public static class EndpointConfig {
        private String endPointPath;
        private HttpMethod methodType;

        EndpointConfig(String endPointPath, HttpMethod methodType) {
            this.endPointPath = endPointPath;
            this.methodType = methodType;
        }


        public String getEndPointPath() {
            return this.endPointPath;
        }

        public HttpMethod getMethodType() {
            return this.methodType;
        }

        public void setEndPointPath(String endPointPath) {
            this.endPointPath = endPointPath;
        }

        public void setMethodType(HttpMethod methodType) {
            this.methodType = methodType;
        }


    }

}
