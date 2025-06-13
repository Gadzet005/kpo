import { OrderStatus, type Order } from "@/core/models/Order";
import { Card, CardContent, Typography, Grid } from "@mui/material";

function orderStatusToString(status: OrderStatus): string {
  switch (status) {
    case OrderStatus.New:
      return "Новый";
    case OrderStatus.Finished:
      return "Завершен";
    case OrderStatus.Canceled:
      return "Отменен";
    default:
      return "Неизвестный статус";
  }
}

interface Props {
  order: Order;
}

const OrderView: React.FC<Props> = ({ order }) => {
  return (
    <Card sx={{ margin: 2 }}>
      <CardContent>
        <Typography variant="h6" component="h2">
          Заказ #{order.id}
        </Typography>
        <Grid container spacing={2}>
          <Grid size={{ xs: 6 }}>
            <Typography variant="body1" color="textSecondary">
              Сумма: {order.amount}
            </Typography>
          </Grid>
          <Grid size={{ xs: 6 }}>
            <Typography variant="body1" color="textSecondary">
              Статус: {orderStatusToString(order.status)}
            </Typography>
          </Grid>
          <Grid size={{ xs: 6 }}>
            <Typography variant="body1" color="textSecondary">
              Описание: {order.description}
            </Typography>
          </Grid>
          <Grid size={{ xs: 6 }}>
            <Typography variant="body1" color="textSecondary">
              Создан: {order.createdAt.toLocaleString()}
            </Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default OrderView;
