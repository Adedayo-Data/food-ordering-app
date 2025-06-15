package com.zosh.model;

import lombok.Data;

@Data //So we can get getter and setter methods for this embedded table class
public class ContactInformation {

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
