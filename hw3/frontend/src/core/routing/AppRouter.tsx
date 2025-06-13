import React from "react";
import type { PageRoute } from "./types";
import { observer } from "mobx-react-lite";
import useAppContext from "@/core/context/app";
import { Navigate, Route, Routes } from "react-router-dom";

interface Props {
  routes: PageRoute[];
  defaultRoute: string;
}

const AppRouter: React.FC<Props> = observer(({ routes, defaultRoute }) => {
  const appContext = useAppContext();

  const Authenticated = React.useCallback(
    ({ children }: { children: React.ReactNode }) => {
      const user = appContext.user.get();
      if (!user.isAuthenticated) {
        return <Navigate to={defaultRoute} />;
      }
      return children;
    },
    [appContext.user, defaultRoute]
  );

  return (
    <Routes>
      {routes.map((route) => {
        return (
          <Route
            key={route.path}
            path={route.path}
            element={
              route.authOnly ? (
                <Authenticated>
                  <route.element />
                </Authenticated>
              ) : (
                <route.element />
              )
            }
          />
        );
      })}
      <Route path="*" element={<Navigate to={defaultRoute} />} />
    </Routes>
  );
});

export default AppRouter;
