import React from "react";

const Header = () => {
  return (
    <header style={{display: "flex", flexDirection: "row", justifyContent: "space-between", padding: "1rem 2.5rem", background: "aquamarine"}}>
      <img src="" alt="logo" />
      <nav >
        <ul style={{display: "flex", flexDirection: "row", listStyle: "none", gap: "3rem"}}>
          <li>Home</li>
          <li>Products</li>
          <ul>
            <li></li>
            <li></li>
            <li></li>
          </ul>
          <li>Blog</li>
        </ul>
      </nav>
      <ul style={{display: "flex", flexDirection: "row", listStyle: "none", gap: "3rem"}}>
        <li>Cart</li>
        <li>Login/User</li>
      </ul>
    </header>
  );
};

export default Header;
