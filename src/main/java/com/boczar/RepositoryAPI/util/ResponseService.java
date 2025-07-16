package com.boczar.RepositoryAPI.util;

import com.boczar.RepositoryAPI.model.Branch;
import com.boczar.RepositoryAPI.model.Response;
import com.boczar.RepositoryAPI.model.SingleResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseService {

    private final BranchService branchService;
    private final RepositoryService repositoryService;

    public ResponseService(BranchService branchService, RepositoryService repositoryService) {
        this.branchService = branchService;
        this.repositoryService = repositoryService;
    }

    public String getResponseByName(String name) throws IOException {
        List<SingleResponse> listOfSingleResponses = new ArrayList<>();

        List<String> repos = repositoryService.getRepoNamesByUserName(name);

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


