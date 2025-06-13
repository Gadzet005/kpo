import { type AlertColor } from "@mui/material";
import { createContext } from "react";

export interface NotificationsContextType {
    show: (
        message: string,
        severity?: AlertColor,
        autoHideDuration?: number
    ) => void;
}

export const NotificationsContext =
    createContext<NotificationsContextType | null>(null);
