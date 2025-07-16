package com.boczar.RepositoryAPI.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Commit {
    private String sha;
    private String url;
}
