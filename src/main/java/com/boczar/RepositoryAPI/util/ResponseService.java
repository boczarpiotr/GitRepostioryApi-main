package com.boczar.RepositoryAPI.util;

import com.boczar.RepositoryAPI.model.Branch;
import com.boczar.RepositoryAPI.model.Response;
import com.boczar.RepositoryAPI.model.SingleResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {

    private final BranchService branchService;
    private final GithubService githubService;

    public ResponseService(BranchService branchService, GithubService githubService) {
        this.branchService = branchService;
        this.githubService = githubService;
    }

    public String getResponseByName(String name) throws IOException {
        List<SingleResponse> listOfSingleResponses = new ArrayList<>();

        List<String> repos = githubService.getRepoNamesByUserName(name);

        for (String repo : repos) {
            Branch[] branches = branchService.getBranchesByRepoName(name, repo);
            SingleResponse singleResponse = new SingleResponse();
            singleResponse.setRepositoryName(repo);
            singleResponse.setBranches(branches);

            listOfSingleResponses.add(singleResponse);
        }
        Response response = new Response();
        response.setLogin(name);
        response.setRepositories(listOfSingleResponses);

        return new Gson().toJson(response);
    }
}


