import React from "react";
import { Snackbar, Alert, type AlertColor } from "@mui/material";
import { NotificationsContext } from "./context";

const DEFAULT_AUTO_HIDE_DURATION = 3000;
const DEFAULT_SEVERITY = "info";

interface State {
  open: boolean;
  message: string;
  severity: AlertColor;
  autoHideDuration: number;
}

interface Props {
  children: React.ReactNode;
}

export const NotificationsProvider: React.FC<Props> = ({ children }) => {
  const [state, setState] = React.useState<State>({
    open: false,
    message: "",
    severity: DEFAULT_SEVERITY,
    autoHideDuration: DEFAULT_AUTO_HIDE_DURATION,
  });

  const show = React.useCallback(
    (
      message: string,
      severity: AlertColor = DEFAULT_SEVERITY,
      autoHideDuration: number = DEFAULT_AUTO_HIDE_DURATION
    ) => {
      setState({ open: true, message, severity, autoHideDuration });
    },
    []
  );

  const handleClose = (_: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === "clickaway") {
      return;
    }
    setState((prev) => ({ ...prev, open: false }));
  };

  const contextValue = React.useMemo(() => ({ show }), [show]);

  return (
    <NotificationsContext.Provider value={contextValue}>
      {children}
      <Snackbar
        open={state.open}
        autoHideDuration={state.autoHideDuration}
        onClose={handleClose}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert
          onClose={handleClose}
          severity={state.severity}
          variant="filled"
          sx={{ width: "100%" }}
        >
          {state.message}
        </Alert>
      </Snackbar>
    </NotificationsContext.Provider>
  );
};
