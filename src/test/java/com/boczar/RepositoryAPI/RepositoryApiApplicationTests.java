package com.boczar.RepositoryAPI;

import com.boczar.RepositoryAPI.util.GithubService;
import com.boczar.RepositoryAPI.util.ResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RepositoryApiApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final GithubService githubService;
	private final ResponseService responseService;

	@Autowired
	public RepositoryApiApplicationTests(
			GithubService githubService,
			ResponseService responseService) {
		this.githubService = githubService;
		this.responseService = responseService;
	}

	@Test
	public void happyPath() throws IOException {
		List<String> repoNames = githubService.getRepoNamesByUserName("elo");
		assertNotNull(repoNames, "There should be repositories");
		assertFalse(repoNames.isEmpty(), "User named 'elo' should have some repositories");

		String expectedResponse = "{\"login\":\"elo\",\"repositories\":[{\"repositoryName\":\"hello-world\",\"branches\":[{\"name\":\"master\",\"commit\":{\"sha\":\"0254b836dc39d017f347b0a0fab11dfbb44e04a8\",\"url\":\"https://api.github.com/repos/elo/hello-world/commits/0254b836dc39d017f347b0a0fab11dfbb44e04a8\"}},{\"name\":\"readme-edits\",\"commit\":{\"sha\":\"da091b95705b39574d4f578e577a414671fc3965\",\"url\":\"https://api.github.com/repos/elo/hello-world/commits/da091b95705b39574d4f578e577a414671fc3965\"}}]},{\"repositoryName\":\"testing\",\"branches\":[]}]}";

		String actualResponse = responseService.getResponseByName("elo").replaceAll("\\s+", "");
		assertEquals(actualResponse, expectedResponse);

		String url = "http://localhost:" + port + "/getRepos/elo";
		ResponseEntity<String> httpResponse = restTemplate.getForEntity(url, String.class);

		assertEquals(httpResponse.getStatusCode(), HttpStatus.OK, "HTTP response code should be 200");

		String body = Objects.requireNonNull(httpResponse.getBody()).replaceAll("\\s+", "");
		assertEquals(body, expectedResponse, "HTTP body should match the expected response");
	}
}