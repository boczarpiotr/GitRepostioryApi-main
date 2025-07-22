package com.boczar.RepositoryAPI.util;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GithubController {

    private final ResponseService responseService;

    public GithubController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping(value = "/getRepos/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJson(@PathVariable String name) throws IOException {
        return responseService.getResponseByName(name);
    }
}
