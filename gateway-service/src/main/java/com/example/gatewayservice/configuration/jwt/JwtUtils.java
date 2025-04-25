package com.example.gatewayservice.configuration.jwt;

import static com.example.gatewayservice.utility.constants.ExceptionConstants.INVALID_SUBJECT_EXTRACTION;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {

    public static String extractSubjectFromRequest(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String jwtToken = authHeader.substring(7);

        String[] jwtParts = jwtToken.split("\\.");
        if (jwtParts.length != 3) {
            throw new IllegalArgumentException(INVALID_SUBJECT_EXTRACTION);
        }
        String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]));
        return extractFieldFromJson(payloadJson);
    }

    private static String extractFieldFromJson(String json) {
        int fieldIndex = json.indexOf("\"sub\":");
        if (fieldIndex == -1) {
            return null;
        }
        int valueStart = json.indexOf("\"", fieldIndex + "sub".length() + 3) + 1;
        int valueEnd = json.indexOf("\"", valueStart);
        return json.substring(valueStart, valueEnd);
    }

}
