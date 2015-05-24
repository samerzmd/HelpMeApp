package com.apps.kawaii.helpme.Models;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 30/04/2015.
 */
@Builder
public @Data class UserInfo {
    public int image;
    public String info;
    public String subInfo;

    public UserInfo(int image, String info, String subInfo) {
        this.image = image;
        this.info = info;
        this.subInfo = subInfo;
    }
}
