package com.apps.kawaii.helpme.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Samer on 08/05/2015.
 */

public class Help implements Parcelable {

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



    protected Help(Parcel in) {
        id = in.readInt();
        title = in.readString();
        latitude = in.readDouble();
        logitude = in.readDouble();
        time_created = in.readString();
        category = in.readString();
        status = in.readInt();
        description = in.readString();
        respondent_id = in.readString();
        asker_id = in.readInt();
        help_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeDouble(latitude);
        dest.writeDouble(logitude);
        dest.writeString(time_created);
        dest.writeString(category);
        dest.writeInt(status);
        dest.writeString(description);
        dest.writeString(respondent_id);
        dest.writeInt(asker_id);
        dest.writeString(help_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Help> CREATOR = new Parcelable.Creator<Help>() {
        @Override
        public Help createFromParcel(Parcel in) {
            return new Help(in);
        }

        @Override
        public Help[] newArray(int size) {
            return new Help[size];
        }
    };
}