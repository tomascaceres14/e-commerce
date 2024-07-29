import React from "react";

const ArticleItem = () => {
  return (
    <article
      style={{
        display: "flex",
        padding: "1rem 8rem",
        gap: "1rem",
      }}
    >
      <img
        src="#"
        alt="article"
        style={{
          padding: "3rem 5rem",
          background: "lightgrey",
        }}
      />
      <div>
        <h3>Articulo nuevo</h3>
        <p>
          Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eum iure
          sapiente sunt est tempora perspiciatis vero voluptates impedit,
          repellendus veniam nihil debitis expedita exercitationem hic inventore
          suscipit quod libero incidunt! Minus, laborum nobis! Nulla illum
          tempora et atque voluptas tenetur! Quam, corrupti dolorum. Error quis
          perspiciatis animi tempore accusamus dolores quam quibusdam harum
          pariatur? Sunt distinctio nobis maxime doloremque perspiciatis!
        </p>
      </div>
    </article>
  );
};

export default ArticleItem;
