import { Routes } from "../routing/routes";

export function mainHeader() {
    return [
        {
            title: "Профиль",
            link: Routes.Profile,
        },
        {
            title: "Заказы",
            link: Routes.Orders,
        },
    ];
}
