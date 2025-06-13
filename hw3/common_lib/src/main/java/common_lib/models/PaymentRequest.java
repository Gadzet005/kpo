package common_lib.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record PaymentRequest(int orderId, int userId, int amount) {}