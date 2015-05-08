package com.apps.kawaii.helpme.Models;

import android.graphics.drawable.Drawable;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 30/04/2015.
 */
@Builder
public @Data class UserInfo {
    public Drawable image;
    public String info;
    public String subInfo;
}
