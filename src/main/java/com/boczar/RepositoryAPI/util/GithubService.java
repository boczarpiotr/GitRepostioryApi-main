package com.boczar.RepositoryAPI.util;

import com.boczar.RepositoryAPI.model.exceptions.GithubException;
import com.boczar.RepositoryAPI.model.exceptions.NotFoundException;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class GithubService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public List<String> getRepoNamesByUserName(String username) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + username + "/repos"))
                .header("Accept", "application/vnd.github+json")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            return switch (statusCode) {
                case 200 -> {
                    JSONArray jsonArray = new JSONArray(response.body());

                    yield IntStream.range(0, jsonArray.length())
                            .mapToObj(jsonArray::getJSONObject)
                            .filter(obj -> !obj.optBoolean("fork", false))
                            .map(obj -> obj.optString("name", ""))
                            .filter(name -> !name.isEmpty())
                            .collect(Collectors.toList());
                }
                case 404 -> throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
                case 403 -> throw new GithubException(HttpStatus.FORBIDDEN, "API rate limit exceeded");
                default -> throw new GithubException(HttpStatus.valueOf(statusCode), "GitHub API error: " + statusCode);
            };

        } catch (IOException | InterruptedException e) {
            throw new GithubException(HttpStatus.INTERNAL_SERVER_ERROR, "Problem with GitHub connection: " + e.getMessage());
        }
    }
}
