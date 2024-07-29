import React, { useEffect } from "react";
import ProductCard from "./ProductCard";

const Suggestions = ({ products }) => {
  return (
    <div style={{ textAlign: "center" }}>
      <h3>Mas vendidos esta semana</h3>
      <div
        style={{
          display: "flex",
          justifyContent: "space-around",
        }}
      >
        {products.map((product) => (
          <ProductCard product={product} />
        ))}
      </div>
    </div>
  );
};

export default Suggestions;
