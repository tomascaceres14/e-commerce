import React from "react";
import Header from "../../components/ui/Header";
import Hero from "./Hero";
import CategoryCarousel from "./CategoryCarousel";
import Suggestions from "./Suggestions";
import ArticlesSection from "./ArticlesSection";
import Footer from "./Footer";

const Home = () => {
  return (
    <div>
      <Header />
      <Hero />
      <CategoryCarousel />
      <Suggestions />
      <Suggestions />
      <ArticlesSection />
      <Footer />
    </div>
  );
};

export default Home;
