import LoadingCircle from "@/components/common/LoadingCircle";
import PaymentsClient from "@/core/clients/PaymentsClient";
import useAppContext from "@/core/context/app";
import useNotifications from "@/core/context/notifications";
import { api } from "@/core/utils/api";
import { ErrorCodes } from "@/core/utils/error_codes";
import { Loading } from "@/core/utils/loading";
import { Alert, Button, Stack, Typography } from "@mui/material";
import { observer } from "mobx-react-lite";
import React from "react";

interface AccountInfo {
  exists: boolean;
  balance: number;
}

const AccountView: React.FC = observer(() => {
  const appContext = useAppContext();
  const notifications = useNotifications();
  const client = React.useMemo(
    () => new PaymentsClient(api(), appContext.user.get()),
    [appContext.user]
  );

  const [balance] = React.useState(new Loading<AccountInfo>());

  const handleCreateAccount = () => {
    client.createAccount().then((result) => {
      if (result.success) {
        balance.success({
          exists: true,
          balance: 0,
        });
        notifications.success("Счет успешно создан");
      } else {
        notifications.error(`Ошибка при создании счета: ${result.code}`);
      }
    });
  };
  const handleDeposit = () => {
    client.deposit(1000).then((result) => {
      if (result.success) {
        balance.success({
          exists: true,
          balance: result.data,
        });
        notifications.success("Счет успешно пополнен");
      } else {
        notifications.error(`Ошибка при пополнении счета: ${result.code}`);
      }
    });
  };

  React.useEffect(() => {
    client.getBalance().then((result) => {
      if (result.success) {
        balance.success({
          exists: true,
          balance: result.data,
        });
      } else if (result.code === ErrorCodes.NotFound) {
        balance.success({
          exists: false,
          balance: 0,
        });
      } else {
        balance.error(`Ошибка при получении данных счета: ${result.code}`);
      }
    });
  }, [balance, client]);

  if (balance.isLoading) {
    return <LoadingCircle />;
  }

  if (balance.isError) {
    return <Alert severity="error">{balance.errorMessage}</Alert>;
  }

  if (!balance.data.exists) {
    return (
      <Stack spacing={1}>
        <Alert severity="info">У вас еще нет счета</Alert>
        <Button variant="contained" onClick={handleCreateAccount}>
          Создать
        </Button>
      </Stack>
    );
  }

  return (
    <Stack spacing={2}>
      <Typography variant="h4">Баланс: {balance.data.balance}</Typography>
      <Button variant="contained" onClick={handleDeposit}>
        Пополнить на 1000
      </Button>
    </Stack>
  );
});

export default AccountView;
