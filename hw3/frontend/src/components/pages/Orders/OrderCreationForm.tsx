import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Stack,
} from "@mui/material";
import { Field, Form, Formik } from "formik";
import { TextField } from "formik-mui";
import * as yup from "yup";

const validationSchema = yup.object().shape({
  amount: yup
    .number()
    .required("Обязательное поле")
    .min(0, "Сумма не может быть отрицательной"),
  description: yup
    .string()
    .required("Обязательное поле")
    .max(255, "Максимум 255 символов"),
});

interface Props {
  onClose: () => void;
  onCreate: (amount: number, description: string) => void;
  open: boolean;
}

const OrderCreationForm: React.FC<Props> = ({ onClose, onCreate, open }) => {
  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle variant="h4">Создание заказа</DialogTitle>
      <Formik
        initialValues={{
          amount: 0,
          description: "",
        }}
        validationSchema={validationSchema}
        onSubmit={(values) => {
          console.log("Submitting order:", values);
          onCreate(values.amount, values.description);
        }}
      >
        <Form>
          <DialogContent>
            <Stack spacing={2}>
              <Field
                name="amount"
                label="Сумма"
                type="number"
                component={TextField}
                fullWidth
              />
              <Field
                name="description"
                label="Описание"
                component={TextField}
                fullWidth
              />
            </Stack>
          </DialogContent>
          <DialogActions sx={{ p: 2 }}>
            <Button onClick={onClose} color="primary" variant="outlined">
              Закрыть
            </Button>
            <Button color="primary" variant="contained" type="submit">
              Создать
            </Button>
          </DialogActions>
        </Form>
      </Formik>
    </Dialog>
  );
};

export default OrderCreationForm;
