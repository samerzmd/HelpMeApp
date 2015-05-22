package com.apps.kawaii.helpme.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */
@Builder
public @Data class Help {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("time_created")
    public Date time_created;
    @SerializedName("asker_id")
    public int asker_id;
    @SerializedName("respondent_id")
    public int respondent_id;
    @SerializedName("currentStatus")
    public HelpStatus currentStatus;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("logitude")
    public double logitude;

    public enum HelpStatus {available,done,notAvailable}

}
