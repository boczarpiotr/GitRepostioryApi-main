package com.boczar.RepositoryAPI.util;

import com.boczar.RepositoryAPI.model.Branch;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BranchService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Branch[] getBranchesByRepoName(String login, String repoName) {
        String url = "https://api.github.com/repos/" + login + "/" + repoName + "/branches";
        return restTemplate.getForObject(url, Branch[].class);
    }
}
