import type { PageRoute } from "@/core/routing/types";
import Login from "@/components/pages/Login";
import Profile from "@/components/pages/Profile";
import Orders from "@/components/pages/Orders";

export enum Routes {
    Login = "/",
    Profile = "/profile",
    Orders = "/orders",
}

export const pageRoutes: PageRoute[] = [
    {
        path: Routes.Login,
        element: Login,
    },
    {
        path: Routes.Profile,
        element: Profile,
        authOnly: true,
    },
    {
        path: Routes.Orders,
        element: Orders,
        authOnly: true,
    },
];

export const defaultRoute = Routes.Login;
