package com.gta.smart.smarthome;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/20.
 */
public class UserHeadPortait implements Parcelable {
    public Bitmap userIcon;
    public void setBitmap(Bitmap bitmap) {
        userIcon = bitmap;
    }

    public static final Parcelable.Creator<UserHeadPortait> CREATOR = new Creator<UserHeadPortait>() {
        @Override
        public UserHeadPortait createFromParcel(Parcel source) {
            UserHeadPortait userHeadPortait = new UserHeadPortait();
            userHeadPortait.userIcon = source.readParcelable(Bitmap.class.getClassLoader());
            return userHeadPortait;
        }

        @Override
        public UserHeadPortait[] newArray(int size) {
            return new UserHeadPortait[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userIcon, PARCELABLE_WRITE_RETURN_VALUE);
    }
}
