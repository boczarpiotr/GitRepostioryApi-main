package com.boczar.RepositoryAPI.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString

public class Response {
    private String login;
    private List<SingleResponse> repositories;
}

