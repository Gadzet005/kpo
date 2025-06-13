import { ThemeProvider } from "@emotion/react";
import { CssBaseline } from "@mui/material";
import { BrowserRouter } from "react-router-dom";
import AppContextProvider from "./core/context/app/provider";
import { NotificationsProvider } from "./core/context/notifications/provider";
import AppRouter from "./core/routing/AppRouter";
import { defaultRoute, pageRoutes } from "./core/routing/routes";
import appTheme from "./core/theme/appTheme";

function App() {
  return (
    <ThemeProvider theme={appTheme}>
      <CssBaseline />
      <AppContextProvider>
        <NotificationsProvider>
          <BrowserRouter>
            <AppRouter routes={pageRoutes} defaultRoute={defaultRoute} />
          </BrowserRouter>
        </NotificationsProvider>
      </AppContextProvider>
    </ThemeProvider>
  );
}

export default App;
