package com.apps.kawaii.helpme.Models;

/**
 * Created by Samer on 23/05/2015.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */

public class Help {

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("logitude")
    public double logitude;
    @SerializedName("time_created")
    public String time_created;
    @SerializedName("category")
    public String category;
    @SerializedName("status")
    public int status;
    @SerializedName("description")
    public String description;
    @SerializedName("respondent_id")
    public String respondent_id;
    @SerializedName("asker_id")
    public int asker_id;
    @SerializedName("help_id")
    public String help_id;


}