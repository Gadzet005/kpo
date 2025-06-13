import {
  Alert,
  Box,
  Button,
  Container,
  Stack,
  Typography,
} from "@mui/material";
import Header from "../../common/Header";
import { mainHeader } from "@/core/config/header.config";
import { Routes } from "@/core/routing/routes";
import { Loading } from "@/core/utils/loading";
import React from "react";
import type { Order } from "@/core/models/Order";
import { observer } from "mobx-react-lite";
import LoadingCircle from "../../common/LoadingCircle";
import OrderView from "./OrderView";
import { api } from "@/core/utils/api";
import OrderClient from "@/core/clients/OrderClient";
import useAppContext from "@/core/context/app";
import OrderCreationForm from "./OrderCreationForm";
import useNotifications from "@/core/context/notifications";

const Orders = () => {
  const appContext = useAppContext();
  const notifications = useNotifications();
  const client = React.useMemo(
    () => new OrderClient(api(), appContext.user.get()),
    [appContext.user]
  );

  const [orders] = React.useState(new Loading<Order[]>());
  const [creationFromOpen, setCreationFromOpen] = React.useState(false);

  const loadOrders = React.useCallback(async () => {
    const result = await client.getUserOrders();
    if (result.success) {
      orders.success(result.data);
    } else {
      orders.error(`Ошибка при получении заказов: ${result.code}`);
    }
  }, [client, orders]);

  const handleOrderCreate = React.useCallback(
    async (amount: number, description: string) => {
      const result = await client.createOrder(amount, description);
      if (result.success) {
        notifications.success("Заказ успешно создан");
      } else {
        notifications.error(`Ошибка при создании заказа: ${result.code}`);
      }

      loadOrders();
      setCreationFromOpen(false);
    },
    [client, loadOrders, notifications]
  );

  React.useEffect(() => {
    loadOrders();
  }, [loadOrders]);

  return (
    <Box>
      <Header items={mainHeader()} active={Routes.Orders} />
      <Box sx={{ p: 2 }}>
        <OrderCreationForm
          onClose={() => setCreationFromOpen(false)}
          onCreate={handleOrderCreate}
          open={creationFromOpen}
        />
        <Container maxWidth="md">
          <Stack spacing={3}>
            <Typography sx={{ textAlign: "center" }} variant="h3">
              Заказы
            </Typography>

            <Button
              sx={{ alignSelf: "center", px: 5 }}
              variant="contained"
              onClick={() => setCreationFromOpen(true)}
            >
              Создать заказ
            </Button>

            <Container maxWidth="md">
              <OrderList list={orders} />
            </Container>
          </Stack>
        </Container>
      </Box>
    </Box>
  );
};

const OrderList = observer(({ list }: { list: Loading<Order[]> }) => {
  if (list.isLoading) {
    return <LoadingCircle />;
  }

  if (list.isError) {
    return <Alert severity="error">{list.errorMessage}</Alert>;
  }

  if (list.data.length === 0) {
    return <Alert severity="info">Заказы отсутствуют</Alert>;
  }

  return list.data.map((order) => <OrderView key={order.id} order={order} />);
});

export default Orders;
