package com.example.demoretrofit.DataModel;

public class JobDataModel {
    private String name;
    private String jobName;

    public JobDataModel(String name, String jobName) {
        this.name = name;
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
