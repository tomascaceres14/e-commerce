import React from "react";
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
          <ProductCard key={product.id} product={product} />
        ))}

        <ProductCard />
        <ProductCard />
        <ProductCard />
        <ProductCard />
      </div>
    </div>
  );
};

export default Suggestions;
