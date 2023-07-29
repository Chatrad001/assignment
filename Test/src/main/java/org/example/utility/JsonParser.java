package org.example.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.TestMartUserService;
import org.example.model.contract.Cart;
import org.example.model.contract.Category;
import org.example.model.contract.Product;
import org.example.model.contract.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Product> parseProductsJsonResponse(String jsonResponse) {
        List<Product> products = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            if (root.isArray()) {
                for (JsonNode productNode : root) {
                    Product product = objectMapper.convertValue(productNode, Product.class);
                    products.add(product);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return products;
    }

    public static Product parseProductJsonResponse(String jsonResponse) {
        Product product = null;
        try {
            product = objectMapper.readValue(jsonResponse, Product.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return product;
    }

    public static List<Category> parseCategoriesJsonResponse(String jsonResponse) {
        List<Category> categories = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            if (root.isArray()) {
                for (JsonNode categoryNode : root) {
                    Category category = objectMapper.convertValue(categoryNode, Category.class);
                    categories.add(category);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return categories;
    }

    public static List<Cart> parseCartJsonResponse(String jsonResponse) {
        List<Cart> carts = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            if (root.isArray()) {
                for (JsonNode cartNode : root) {
                    String id = cartNode.get("id").asText();
                    String title = cartNode.get("title").asText();
                    String description = cartNode.get("description").asText();
                    int userId = cartNode.get("userId").asInt();

                    Cart cart = new Cart(id, title, description, userId);
                    carts.add(cart);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return carts;
    }
    public static List<User> parseUsersJsonResponse(String jsonResponse) {
        List<User> users = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            if (root.isArray()) {
                for (JsonNode userNode : root) {
                    Integer id = userNode.get("id").asInt();
                    String username = userNode.get("username").asText();
                    String email = userNode.get("email").asText();
                    String firstName = userNode.get("firstName").asText();
                    String lastName = userNode.get("lastName").asText();

                    User user = new User(id, username, email, firstName, lastName);
                    users.add(user);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(),e);
        }
        return users;
    }

}
