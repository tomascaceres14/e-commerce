import React from "react";
import "./Header.css";

const Header = () => {
  return (
    <header className="home-header">
      <img src="" alt="logo" />
      <nav>
        <ul className="navbar-ul">
          <li>
            <a href="#">Home</a>
          </li>
          <li>
            <a href="#">Products</a>
          </li>
          {/* <ul>
            <li><a href="#"></</a>li>
            <li><a href="#"></</a>li>
            <li><a href="#"></</a>li>
          </ul> */}
          <li>
            <a href="#">Blog</a>
          </li>
        </ul>
      </nav>
      <ul className="navbar-ul">
        <li>
          <a href="#">Cart</a>
        </li>
        <li>
          <a href="#">Login</a>/User
        </li>
      </ul>
    </header>
  );
};

export default Header;
