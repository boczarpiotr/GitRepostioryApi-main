package com.boczar.RepositoryAPI.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    ResponseService responseService;

    @GetMapping(value = "/getRepos/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJson(@PathVariable String name, @RequestHeader HttpHeaders headers) throws IOException {
        return responseService.getResponseByName(name);
    }
}
