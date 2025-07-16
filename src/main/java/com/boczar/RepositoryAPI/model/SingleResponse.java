package com.boczar.RepositoryAPI.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class SingleResponse {
    private String repositoryName;
    private Branch[] branches;
}

