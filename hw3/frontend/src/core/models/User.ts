const notAuthError = new Error("User is not authenticated");

class User {
    private _id: number | null = null;
    private _name: string | null = null;
    private _isAuthenticated: boolean = false;

    login(id: number, name: string) {
        this._id = id;
        this._name = name;
        this._isAuthenticated = true;
    }

    logout() {
        this._id = null;
        this._name = null;
        this._isAuthenticated = false;
    }

    get id(): number {
        if (this._id === null) {
            throw notAuthError;
        }
        return this._id;
    }

    get name(): string {
        if (this._name === null) {
            throw notAuthError;
        }
        return this._name;
    }

    get isAuthenticated(): boolean {
        return this._isAuthenticated;
    }
}

export default User;
