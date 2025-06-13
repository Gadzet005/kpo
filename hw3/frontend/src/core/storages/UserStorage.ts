import User from "@/core/models/User";
import { makeAutoObservable } from "mobx";

const LOCAL_STORAGE_KEY = "user";

class UserStorage {
    private _user: User | null = null;

    constructor() {
        makeAutoObservable(this);
    }

    get(): User {
        if (this._user) {
            return this._user;
        }

        const s = localStorage.getItem(LOCAL_STORAGE_KEY);
        this._user = this.load(s ?? "");
        return this._user;
    }

    save(user: User) {
        localStorage.setItem(LOCAL_STORAGE_KEY, this.dump(user));
        this._user = user;
    }

    private dump(user: User) {
        if (!user.isAuthenticated) {
            return "";
        }
        return JSON.stringify({
            id: user.id,
            name: user.name,
        });
    }

    private load(s: string) {
        const user = new User();
        if (s) {
            const parsed = JSON.parse(s);
            user.login(parsed.id, parsed.name);
        }
        return user;
    }
}

export default UserStorage;
