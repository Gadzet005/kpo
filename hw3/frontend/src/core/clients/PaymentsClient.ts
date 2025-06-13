import { type AxiosInstance } from "axios";
import type User from "@/core/models/User";
import { success, type Result } from "@/core/utils/result";
import { defaultErrorMapper } from "../utils/error_mapping";

class PaymentsClient {
    private api: AxiosInstance;
    private user: User;

    constructor(api: AxiosInstance, user: User) {
        this.api = api;
        this.user = user;
    }

    async getBalance(): Promise<Result<number>> {
        try {
            const response = await this.api.get<{ balance: number }>(
                `/payments/balance/${this.user.id}`
            );
            return success(response.data.balance);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }

    async deposit(amount: number): Promise<Result<number>> {
        try {
            const response = await this.api.post<{ balance: number }>(
                "/payments/deposit",
                {
                    user_id: this.user.id,
                    amount,
                }
            );
            return success(response.data.balance);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }

    async createAccount(): Promise<Result<boolean>> {
        try {
            const response = await this.api.post<{ created: boolean }>(
                "/payments/create-account",
                {
                    user_id: this.user.id,
                }
            );
            return success(response.data.created);
        } catch (e: unknown) {
            console.error(e);
            return defaultErrorMapper().map(e);
        }
    }
}

export default PaymentsClient;
