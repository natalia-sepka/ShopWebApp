package com.example.gateway.filter;

import org.example.entity.Endpoint;
import org.example.entity.HttpMethod;
import org.example.entity.Role;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public Set<Endpoint> openApiEndpoints = new HashSet<>(List.of(
            new Endpoint("/auth/logout", HttpMethod.GET, Role.GUEST),
            new Endpoint("/auth/register", HttpMethod.POST, Role.GUEST),
            new Endpoint("/auth/login", HttpMethod.POST, Role.GUEST),
            new Endpoint("/auth/validate", HttpMethod.GET, Role.GUEST),
            new Endpoint("/auth/activate", HttpMethod.GET, Role.GUEST),
            new Endpoint("/auth/authorize", HttpMethod.GET, Role.GUEST),
            new Endpoint("/auth/reset-password", HttpMethod.PATCH, Role.GUEST),
            new Endpoint("/auth/reset-password", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/gateway", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/auto-login", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/logged-in", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/product", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/product", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/product", HttpMethod.DELETE, Role.GUEST),
            new Endpoint("/api/v1/category", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/category", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/basket", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/basket", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/basket", HttpMethod.DELETE, Role.GUEST),
            new Endpoint("/api/v1/order", HttpMethod.POST, Role.GUEST),
            new Endpoint("/api/v1/order", HttpMethod.POST, Role.ADMIN),
            new Endpoint("/api/v1/order", HttpMethod.POST, Role.USER),
            new Endpoint("/api/v1/order", HttpMethod.GET, Role.USER),
            new Endpoint("/api/v1/order", HttpMethod.GET, Role.ADMIN),
            new Endpoint("/api/v1/order", HttpMethod.GET, Role.GUEST),
            new Endpoint("/api/v1/image", HttpMethod.DELETE, Role.ADMIN)
    ));

    private Set<Endpoint> adminEndpoints = new HashSet<>();

    public void addEndpoints(List<Endpoint> endpointsList) {
        for (Endpoint endpoint: endpointsList) {
            if (endpoint.getRole().name().equals(Role.ADMIN.name())) {
                adminEndpoints.add(endpoint);
            }
        }
        adminEndpoints.forEach( value -> {
            System.out.println(value.getUrl());
            System.out.println(value.getHttpMethod());
        });
    }

    public Predicate<ServerHttpRequest> isSecure =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(value -> request.getURI()
                            .getPath()
                            .contains(value.getUrl())
                    && request.getMethod().name().equals(value.getHttpMethod().name()));
    public Predicate<ServerHttpRequest> isAdmin =
            request -> adminEndpoints
                    .stream()
                    .anyMatch(value -> request.getURI()
                            .getPath()
                            .contains(value.getUrl())
                    && request.getMethod().name().equals(value.getHttpMethod().name()));
}
