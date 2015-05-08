package com.apps.kawaii.helpme.Models;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */
@Builder
public @Data class User {

    public String name;
    UserInfo[] userInfos;
    public int id;

}
