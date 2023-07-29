package org.example.app;

import org.example.model.contract.Cart;
import org.example.model.contract.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.example.utility.JsonParser.parseCartJsonResponse;

public class TestMartCartService implements CartService<Cart> {

    private static final Logger logger = LoggerFactory.getLogger(TestMartCartService.class);
    private static final String BASE_URL = "https://dummyjson.com/carts";

    @Override
    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<>();
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                 carts = parseCartJsonResponse(response.toString());
            } else {
                logger.info("Failed to get all carts. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        return carts;
    }


    @Override
    public Cart getCart(Integer cartId) {
        Cart cart = null;
        try {
            URL url = new URL(BASE_URL + "/" + cartId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                cart = parseCartJsonResponse(response.toString()).get(0);

            } else {
               logger.info("Failed to get cart with ID " + cartId + ". Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        return cart;
    }

    @Override
    public List<Cart> getUserCarts(Integer userId) {
        return null;
    }
}

