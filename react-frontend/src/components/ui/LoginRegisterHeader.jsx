import React from "react";
import { Link } from "react-router-dom";

const LoginRegisterHeader = () => {
  return (
    <header style={{ padding: "0.5rem 1rem", background: "aquamarine" }}>
      <Link to={"/"}>L O G O</Link>
    </header>
  );
};

export default LoginRegisterHeader;
