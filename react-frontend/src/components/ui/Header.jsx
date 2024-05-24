import React from "react";
import "./Header.css";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <header className="home-header">
      <Link to={"/"}>
        <img src="" alt="logo" />
      </Link>
      <nav>
        <ul className="navbar-ul">
          <li>
            <Link to={"/"} href="#">
              Cateogries
            </Link>
          </li>
          <li>
            <Link to={"/"} href="#">
              Products
            </Link>
          </li>
        
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
