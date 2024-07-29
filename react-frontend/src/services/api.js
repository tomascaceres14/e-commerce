import axios from "axios";

const API_URL = "http://localhost:8080/home";

export const fetchProducts = async () => {
  try {
    const response = await axios.get(`${API_URL}/products`);
    return response.data.content;
  } catch (error) {
    throw new Error("Error fetching products");
  }
};

export const fetchCategories = async () => {
  try {
    const response = await axios.get(`${API_URL}/categories`);
    return response.data;
  } catch (error) {
    throw new Error(error);
  }
};

export const fetchArticles = async () => {
  try {
    const response = await axios.get(`${API_URL}/articles`);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching articles");
  }
};
