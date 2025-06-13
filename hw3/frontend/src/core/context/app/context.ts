import AppStorage from "@/core/storages/AppStorage";
import React from "react";

const AppContext = React.createContext<AppStorage | null>(null);

export default AppContext;
