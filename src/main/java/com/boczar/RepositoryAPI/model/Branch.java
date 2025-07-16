package com.boczar.RepositoryAPI.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Branch {
    private String name;
    private Commit commit;
    private String protected_;
}

