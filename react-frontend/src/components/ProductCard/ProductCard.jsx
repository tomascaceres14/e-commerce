import React from "react";

const ProductCard = () => {
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        alignItems: "center",
        border: "1px solid black",
        width: "15rem",
        height: "20rem",
      }}
    >
      <img src="" alt="product" />
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <p style={{ color: "lightgreen", fontWeight: "bold" }}>ARS$999</p>
        <p>Product Title</p>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "1rem",
          }}
        >
          <button>+Cart</button>
          <button>Details</button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
