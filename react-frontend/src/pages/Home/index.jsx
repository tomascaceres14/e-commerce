import React from "react";
import Header from "../../components/ui/Header";
import Hero from "./Hero";
import CategoryCarousel from "./CategoryCarousel";
import Suggestions from "./Suggestions";

const Home = () => {
  return (
    <div>
      <Header />
      <Hero />
      <CategoryCarousel />
      <Suggestions />
      <Suggestions />
    </div>
  );
};

export default Home;
