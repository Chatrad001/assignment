package org.example.app;

import org.example.model.contract.Category;
import org.example.model.contract.Product;
import org.example.model.contract.ProductService;
import org.example.utility.JsonParser;

import java.util.List;

public class TestMartProductService implements ProductService<Product, Category> {

    private static final String BASE_URL = "https://dummyjson.com/products";

    @Override
    public List<Product> getAllProducts() {
        return fetchProducts(BASE_URL);
    }

    @Override
    public List<Product> getAllProducts(int limit, int skip, String... fields) {
        String urlWithParams = BASE_URL + "?limit=" + limit + "&skip=" + skip;
        if (fields.length > 0) {
            String fieldsParam = String.join(",", fields);
            urlWithParams += "&select=" + fieldsParam;
        }
        return fetchProducts(urlWithParams);
    }

    @Override
    public Product getProduct(Integer productId) {
        String url = BASE_URL + "/" + productId;
        return fetchProduct(url);
    }

    @Override
    public List<Product> searchProducts(String query) {
        String url = BASE_URL + "/search?q=" + query;
        return fetchProducts(url);
    }

    @Override
    public List<Category> getCategories() {
        String url = BASE_URL + "/categories";
        return fetchCategories(url);
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        String url = BASE_URL + "/category/" + categoryName;
        return fetchProducts(url);
    }

    // Helper methods to fetch data from the API and parse JSON responses using JsonParser
    private List<Product> fetchProducts(String url) {
        // Use JsonParser to parse JSON response and create Product objects
        String jsonResponse = getJsonResponse(url);
        return JsonParser.parseProductsJsonResponse(jsonResponse);
    }

    private Product fetchProduct(String url) {
        // Use JsonParser to parse JSON response and create Product object
        String jsonResponse = getJsonResponse(url);
        return JsonParser.parseProductJsonResponse(jsonResponse);
    }

    private List<Category> fetchCategories(String url) {
        // ... (HTTP request code as before)
        // Use JsonParser to parse JSON response and create Category objects
        String jsonResponse = getJsonResponse(url);
        return JsonParser.parseCategoriesJsonResponse(jsonResponse);
    }
    private String getJsonResponse(String apiUrl) {
        // For simplicity, we just return dummy JSON data.
        // Replace this method with actual HTTP request code using your preferred HTTP client library.
        return "[{\"id\":\"1\",\"title\":\"Product A\",\"price\":100,\"discountPercentage\":0.1,\"rating\":4.5,\"brand\":\"Brand X\",\"category\":\"Category 1\",\"thumbnail\":\"thumbnailA\",\"images\":[\"imageA1.jpg\",\"imageA2.jpg\"]}," +
                "{\"id\":\"2\",\"title\":\"Product B\",\"price\":150,\"discountPercentage\":0.2,\"rating\":4.2,\"brand\":\"Brand Y\",\"category\":\"Category 2\",\"thumbnail\":\"thumbnailB\",\"images\":[\"imageB1.jpg\",\"imageB2.jpg\"]}]";
    }
}
