import type { AxiosInstance } from "axios";
import type User from "../models/User";
import { OrderStatus, type Order } from "../models/Order";
import { defaultErrorMapper } from "../utils/error_mapping";
import { success, type Result } from "../utils/result";

interface OrderData {
    id: number;
    user_id: number;
    amount: number;
    status: string;
    description: string;
    created_at: string;
}

function parseOrderStatus(status: string): OrderStatus {
    switch (status) {
        case "NEW":
            return OrderStatus.New;
        case "FINISHED":
            return OrderStatus.Finished;
        case "CANCELLED":
            return OrderStatus.Canceled;
        default:
            throw new Error(`Unknown order status: ${status}`);
    }
}

function orderDataToOrder(order: OrderData): Order {
    return {
        id: order.id,
        userId: order.user_id,
        amount: order.amount,
        status: parseOrderStatus(order.status),
        description: order.description,
        createdAt: new Date(order.created_at),
    };
}

class OrderClient {
    private api: AxiosInstance;
    private user: User;

    constructor(api: AxiosInstance, user: User) {
        this.api = api;
        this.user = user;
    }

    async getOrder(id: number): Promise<Result<Order>> {
        try {
            const response = await this.api.get<OrderData>(`/orders/${id}`);
            const order = orderDataToOrder(response.data);
            return success(order);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }

    async getUserOrders(): Promise<Result<Order[]>> {
        try {
            const response = await this.api.get<OrderData[]>(
                `/orders/user-orders/${this.user.id}`
            );
            const orders = response.data.map(orderDataToOrder);
            return success(orders);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }

    async createOrder(
        amount: number,
        description: string
    ): Promise<Result<number>> {
        try {
            const response = await this.api.post<{ order_id: number }>(
                "/orders/create",
                {
                    user_id: this.user.id,
                    amount,
                    description,
                }
            );
            return success(response.data.order_id);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }
}

export default OrderClient;
