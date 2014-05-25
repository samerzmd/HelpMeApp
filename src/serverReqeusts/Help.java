package serverReqeusts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Help implements Parcelable {
	@SerializedName("id")
	public int id;
	
	@SerializedName("title")
	public String title;
	
	@SerializedName("details")
	public String details;
	
	@SerializedName("whoRequestsId")
	public int whoRequestsId;
	
	@SerializedName("whoAnswersId")
	public int whoAnswersId;
	
	@SerializedName("longitude")
	public Double longitude;
	
	@SerializedName("latitude")
	public Double latitude;
	
	@SerializedName("status")
	public int status;

    protected Help(Parcel in) {
        id = in.readInt();
        title = in.readString();
        details = in.readString();
        whoRequestsId = in.readInt();
        whoAnswersId = in.readInt();
        longitude = in.readByte() == 0x00 ? null : in.readDouble();
        latitude = in.readByte() == 0x00 ? null : in.readDouble();
        status = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(details);
        dest.writeInt(whoRequestsId);
        dest.writeInt(whoAnswersId);
        if (longitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(latitude);
        }
        dest.writeInt(status);
    }

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