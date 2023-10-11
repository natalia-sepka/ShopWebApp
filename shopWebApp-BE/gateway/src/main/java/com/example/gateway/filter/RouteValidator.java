package com.example.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/auth/logout",
            "/auth/validate",
            "/auth/register",
            "/auth/activate",
            "/auth/reset-password",
            "/auth/auto-login",
            "/auth/logged-in"
    );

    public Predicate<ServerHttpRequest> isSecure =
            request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> request.getURI()
                        .getPath()
                        .contains(uri));
}
