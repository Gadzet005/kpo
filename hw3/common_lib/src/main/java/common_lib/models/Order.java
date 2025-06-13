package common_lib.models;

import java.time.Instant;

public record Order(int id, int userId, int amount, String description, OrderStatus status, Instant createdAt) {}
