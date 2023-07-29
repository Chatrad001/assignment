package org.example.app;

import org.example.model.contract.User;
import org.example.model.contract.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.example.utility.JsonParser.parseUsersJsonResponse;

public class TestMartUserService implements UserService<User> {
    private static final Logger logger = LoggerFactory.getLogger(TestMartUserService.class);
    private static final String BASE_URL = "https://dummyjson.com/users";

    @Override
    public List<User> getAllUsers() {
        String url = BASE_URL;
        return fetchUsers(url);
    }

    @Override
    public User getUser(Integer userId) {
        String url = BASE_URL + "/" + userId;
        List<User> users = fetchUsers(url);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> searchUsers(String query) {
        String url = BASE_URL + "/search?q=" + query;
        return fetchUsers(url);
    }

    // Helper method to fetch users from the API and parse JSON responses using JsonParser
    private List<User> fetchUsers(String url) {
        List<User> users = new ArrayList<>();
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
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

                 users = parseUsersJsonResponse(response.toString());
            } else {
                logger.info("Failed to get users. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        return users;
    }

}
