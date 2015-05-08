package com.apps.kawaii.helpme.Models;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */
@Builder
public @Data class Help {
    public String title;
    public String description;
    public Date dateToEnd;
    public User helpRequester;
    public User helpProvider;
    public HelpStatus currentStatus;

    public enum HelpStatus {available,done,notAvailable}

    public Help(){

    }
}
