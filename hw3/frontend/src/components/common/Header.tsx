import { Box, Typography } from "@mui/material";
import type React from "react";
import { Link } from "react-router-dom";

interface HeaderItem {
  title: string;
  link: string;
}

interface Props {
  items: HeaderItem[];
  active?: string;
}

const Header: React.FC<Props> = ({ items, active }) => {
  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        justifyContent: "center",
        py: 2,
        px: 4,
        gap: 2,
      }}
    >
      {items.map((item) => (
        <Link
          key={item.title}
          to={item.link}
          style={{
            color: item.link === active ? "blue" : "black",
            textDecoration: "none",
          }}
        >
          <Typography variant="h6">{item.title}</Typography>
        </Link>
      ))}
    </Box>
  );
};

export default Header;
