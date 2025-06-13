import useAppContext from "@/core/context/app";
import useNotifications from "@/core/context/notifications";
import { Routes } from "@/core/routing/routes";
import { hash } from "@/core/utils/hash";
import { Box, Button, Container, Stack, Typography } from "@mui/material";
import { Field, Form, Formik } from "formik";
import { TextField } from "formik-mui";
import { useNavigate } from "react-router-dom";
import * as yup from "yup";

const LoginSchema = yup.object().shape({
  name: yup
    .string()
    .required("Обязательное поле")
    .max(
      255,
      "Все таки не любое имя... Оно должно быть не длиннее 255 символов"
    )
    .min(3, "Все таки не любое имя... Оно должно быть не короче 3 символов"),
});

const Login = () => {
  const appContext = useAppContext();
  const notifications = useNotifications();
  const navigate = useNavigate();

  return (
    <Box sx={{ p: 2 }}>
      <Box sx={{ display: "flex", justifyContent: "center" }}>
        <Container maxWidth="sm">
          <Stack spacing={2}>
            <Typography sx={{ textAlign: "center" }} variant="h2">
              Вход
            </Typography>
            <Formik
              initialValues={{
                name: "",
              }}
              onSubmit={function (values) {
                const user = appContext.user.get();
                user.login(hash(values.name), values.name);
                appContext.user.save(user);

                notifications.success(`Добро пожаловать, ${user.name}!`);
                navigate(Routes.Profile);
              }}
              validationSchema={LoginSchema}
            >
              <Form>
                <Stack spacing={1}>
                  <Field
                    name="name"
                    type="text"
                    label="Имя пользователя"
                    helperText="Можно ввести любое имя"
                    component={TextField}
                  />
                  <Button variant="contained" type="submit">
                    Войти
                  </Button>
                </Stack>
              </Form>
            </Formik>
          </Stack>
        </Container>
      </Box>
    </Box>
  );
};

export default Login;
