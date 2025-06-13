import { makeAutoObservable } from "mobx";

export enum LoadingStatus {
    Loading = "loading",
    Error = "error",
    Success = "success",
}

export class Loading<T> {
    private _status: LoadingStatus = LoadingStatus.Loading;
    private _data: T | null = null;
    private _errorMessage: string | null = null;

    constructor() {
        makeAutoObservable(this);
    }

    success(data: T) {
        this._status = LoadingStatus.Success;
        this._data = data;
    }

    error(errorMessage: string) {
        this._status = LoadingStatus.Error;
        this._errorMessage = errorMessage;
    }

    get status(): LoadingStatus {
        return this._status;
    }

    get data(): T {
        if (!this.isSuccess || this._data === null) {
            throw new Error("Can't get data");
        }
        return this._data;
    }

    get errorMessage(): string {
        if (!this.isError || this._errorMessage === null) {
            throw new Error("Can't get error message");
        }
        return this._errorMessage;
    }

    get isSuccess(): boolean {
        return this._status === LoadingStatus.Success;
    }

    get isError(): boolean {
        return this._status === LoadingStatus.Error;
    }

    get isLoading(): boolean {
        return this._status === LoadingStatus.Loading;
    }
}
