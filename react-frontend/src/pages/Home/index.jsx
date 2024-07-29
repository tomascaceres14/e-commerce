import React, { useEffect, useState } from "react";
import Header from "../../components/ui/Header";
import Hero from "./Hero";
import CategoryCarousel from "./CategoryCarousel";
import Suggestions from "./Suggestions";
import ArticlesSection from "./ArticlesSection";
import Footer from "./Footer";
import {
  fetchArticles,
  fetchCategories,
  fetchProducts,
} from "../../services/api";

const Home = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        const [productsData, categoriesData, articlesData] = await Promise.all([
          fetchProducts(),
          fetchCategories(),
          fetchArticles(),
        ]);
        setProducts(productsData);
        setCategories(categoriesData);
        setCategories(articlesData);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    loadData();
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <Header categories={categories} />
      <Hero />
      <CategoryCarousel />
      <Suggestions products={products} />
      <Suggestions products={products} />
      <ArticlesSection articles={articles} />
      <Footer />
    </div>
  );
};

export default Home;
