import React from "react";
import AppContext from "./context";
import type AppStorage from "@/core/storages/AppStorage";

function useAppContext(): AppStorage {
    const s = React.useContext(AppContext);
    if (s === null) {
        throw new Error("AppContext is null");
    }
    return s;
}

export default useAppContext;
