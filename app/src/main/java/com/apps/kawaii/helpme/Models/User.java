package com.apps.kawaii.helpme.Models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */
@Builder
public @Data class User {
    @SerializedName("id")
    public int id;
    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("rate")
    public String rate;
    @SerializedName("about")
    public String about;
    @SerializedName("gender")
    public String gender;
    @SerializedName("avatar")
    public String avatar;
}
