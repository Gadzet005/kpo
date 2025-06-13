import { Box, CircularProgress } from "@mui/material";

const LoadingCircle = () => {
  return (
    <Box sx={{ display: "flex", justifyContent: "center" }}>
      <CircularProgress />
    </Box>
  );
};

export default LoadingCircle;
