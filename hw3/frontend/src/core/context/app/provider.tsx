import React from "react";
import AppContext from "./context";
import AppStorage from "@/core/storages/AppStorage";

interface Props {
  children?: React.ReactNode;
}

const AppContextProvider: React.FC<Props> = ({ children }) => {
  const storage = React.useMemo(() => new AppStorage(), []);
  return <AppContext.Provider value={storage}>{children}</AppContext.Provider>;
};

export default AppContextProvider;
