import type { AxiosInstance } from "axios";
import axios from "axios";
import { API_URL } from "../config/api.config";

export function api(): AxiosInstance {
    return axios.create({
        baseURL: API_URL,
        headers: {
            "Content-Type": "application/json",
        },
    });
}
