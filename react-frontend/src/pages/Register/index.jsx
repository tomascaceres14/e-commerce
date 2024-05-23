import React from "react";

export const Register = () => {
  return (
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
        <label>Nombre(s)</label>
        <input type="text" />
        <label>Apellido(s)</label>
        <input type="text" />
        <label>Correo electrónico</label>
        <input type="text" />
        <label>Numero de teléfono</label>
        <input type="text" />
        <label>Contraseña</label>
        <input type="password" />
        <label>Confirmar contraseña</label>
        <input type="password" />
        <input type="button" className="" value={"send"}></input>
      </form>
      <a style={{ textDecoration: "underline", cursor: "pointer" }}>
        Ya tienes cuenta? Inicia sesion.
      </a>
    </div>
  );
};
