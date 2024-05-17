import React from "react";
import CarouselItem from "./CarouselItem";

const CategoryCarousel = () => {
  return (
    <div style={{ display: "flex", justifyContent: "center", gap: "2rem" }}>
      <CarouselItem />
      <CarouselItem />
      <CarouselItem />
      <CarouselItem />
      <CarouselItem />
    </div>
  );
};

export default CategoryCarousel;
