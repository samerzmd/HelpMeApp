package com.samerzmd.testthismap;

import serverReqeusts.Help;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class helpMarker implements Parcelable {
	MarkerOptions marker;
	Help help;
	protected int helpIcon = R.drawable.ic_launcher;
	public helpMarker(Help help ) {
	this.help=help;
	this.marker=new MarkerOptions();
	}
	public MarkerOptions getHelpOnMap() {
		marker.title(help.title).position(new LatLng(help.latitude, help.longitude)).snippet(String.valueOf(help.id));
		return marker;
	}

    protected helpMarker(Parcel in) {
        marker = (MarkerOptions) in.readValue(MarkerOptions.class.getClassLoader());
        help = (Help) in.readValue(Help.class.getClassLoader());
        helpIcon = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(marker);
        dest.writeValue(help);
        dest.writeInt(helpIcon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<helpMarker> CREATOR = new Parcelable.Creator<helpMarker>() {
        @Override
        public helpMarker createFromParcel(Parcel in) {
            return new helpMarker(in);
        }

        @Override
        public helpMarker[] newArray(int size) {
            return new helpMarker[size];
        }
    };
}