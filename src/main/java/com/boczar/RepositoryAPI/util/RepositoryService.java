package com.boczar.RepositoryAPI.util;

import com.boczar.RepositoryAPI.model.exceptions.GithubException;
import com.boczar.RepositoryAPI.model.exceptions.NotFoundException;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RepositoryService {

    public List<String> getRepoNamesByUserName(String username) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL("https://api.github.com/users/" + username + "/repos");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/vnd.github+json");
            connection.setRequestProperty("User-Agent", "RepoApiClient");

            int responseCode = connection.getResponseCode();

            if (responseCode == 404) {
                throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
            } else if (responseCode == 403) {
                throw new GithubException(HttpStatus.FORBIDDEN, "API rate limit exceeded");
            } else if (responseCode != 200) {
                throw new GithubException(HttpStatus.valueOf(responseCode), "GitHub API error: " + responseCode);
            }

            try (InputStream inputStream = connection.getInputStream()) {
                String body = new String(inputStream.readAllBytes());

                JSONArray jsonArray = new JSONArray(body);

                return IntStream.range(0, jsonArray.length()).mapToObj(jsonArray::getJSONObject).filter(jsonObject -> !jsonObject.optBoolean("fork")).map(jsonObject -> jsonObject.optString("name")).collect(Collectors.toList());
            }

        } catch (IOException e) {
            throw new GithubException(HttpStatus.INTERNAL_SERVER_ERROR, "Problem with GitHub connection: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

