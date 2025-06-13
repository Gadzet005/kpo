import useAppContext from "@/core/context/app";
import { Routes } from "@/core/routing/routes";
import {
  Box,
  Button,
  Container,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import AccountView from "./AccountView";
import Header from "@/components/common/Header";
import { mainHeader } from "@/core/config/header.config";

const Profile = () => {
  const appContext = useAppContext();
  const user = appContext.user.get();
  const navigate = useNavigate();

  function logout() {
    user.logout();
    appContext.user.save(user);
    navigate(Routes.Login);
  }

  return (
    <Box>
      <Header items={mainHeader()} active={Routes.Profile} />
      <Box sx={{ p: 2 }}>
        <Box sx={{ display: "flex", justifyContent: "center" }}>
          <Container maxWidth="sm">
            <Stack spacing={3}>
              <Typography variant="h3" sx={{ textAlign: "center" }}>
                Профиль
              </Typography>
              <Stack spacing={1}>
                <TextField
                  slotProps={{
                    input: {
                      readOnly: true,
                    },
                  }}
                  label="Имя"
                  value={user.name}
                  variant="outlined"
                />
                <Button variant="contained" color="error" onClick={logout}>
                  Выйти
                </Button>
              </Stack>
              <Typography variant="h3" sx={{ textAlign: "center" }}>
                Счет
              </Typography>
              <AccountView />
            </Stack>
          </Container>
        </Box>
      </Box>
    </Box>
  );
};

export default Profile;
