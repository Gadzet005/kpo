export enum OrderStatus {
    New,
    Finished,
    Canceled,
}

export interface Order {
    id: number;
    userId: number;
    amount: number;
    status: OrderStatus;
    description: string;
    createdAt: Date;
}
