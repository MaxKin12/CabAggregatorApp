package com.example.ratesservice.configuration.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import wiremock.com.google.common.net.HttpHeaders;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WireMockStubs {

    public static void setGetResponseStub(String requestUrl, String pathParameter, String json) {
        stubFor(
                WireMock.get(requestUrl + pathParameter)
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(200)
                                .withBody(json)));
    }

}
