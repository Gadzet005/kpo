import React from "react";
import { NotificationsContext } from "./context";

function useNotifications() {
    const context = React.useContext(NotificationsContext);
    if (context === null) {
        throw new Error("NotificationsContext is null");
    }

    return {
        success: (message: string) => context.show(message, "success"),
        error: (message: string) => context.show(message, "error"),
        warning: (message: string) => context.show(message, "warning"),
        info: (message: string) => context.show(message, "info"),
        show: context.show,
    };
}

export default useNotifications;
