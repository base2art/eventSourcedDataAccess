package com.company;

import lombok.Data;

import java.util.List;

@Data
public class ProjectVersionData {
    private String[] files;
    private List<String> knownContributors;
    private List<Build> buildHistory;
}
