import React from "react";
import LoginRegisterHeader from "../../components/ui/LoginRegisterHeader";

export const Login = () => {
  return (
    <>
      <LoginRegisterHeader />

      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          height: "100vh",
        }}
      >
        <form
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <label>Correo electrónico</label>
          <input type="email" />
          <label>Contraseña</label>
          <input type="password" />
          <input type="button" className="" value={"send"}></input>
        </form>
        <a style={{ textDecoration: "underline", cursor: "pointer" }}>
          No tienes cuenta? Registrate.
        </a>
      </div>
    </>
  );
};
