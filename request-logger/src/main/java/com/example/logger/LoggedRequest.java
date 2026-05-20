package com.example.logger;

import java.util.Map;

public record LoggedRequest(
        String method,
        String uri,
        String queryString,
        String remoteAddr,
        Map<String, String> headers,
        String body
) {}
