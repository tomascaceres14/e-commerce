import React from "react";
import ArticleItem from "./ArticleItem";

const ArticlesSection = () => {
  return (
    <section
      style={{ display: "flex", flexDirection: "column", alignItems: "center" }}
    >
      <h2 style={{ textAlign: "center" }}>Articulos</h2>
      <ArticleItem />
      <ArticleItem />
      <ArticleItem />
      <button style={{ padding: "0.7rem 4rem" }}>Ver mas...</button>
    </section>
  );
};

export default ArticlesSection;
