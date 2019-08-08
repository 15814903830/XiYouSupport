package com.chengfan.xiyou.widget.ninegridview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data source
 * NOTE:T class must implemente the interface of Parcelable
 */

public class NineGridBean implements Parcelable {
    private String thumbUrl;

    public NineGridBean(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }


    @Override
    public String toString() {
        return "NineGridBean{" +
                "thumbUrl='" + thumbUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbUrl);
    }

    protected NineGridBean(Parcel in) {
        this.thumbUrl = in.readString();

    }

    public static final Creator<NineGridBean> CREATOR = new Creator<NineGridBean>() {
        @Override
        public NineGridBean createFromParcel(Parcel source) {
            return new NineGridBean(source);
        }

        @Override
        public NineGridBean[] newArray(int size) {
            return new NineGridBean[size];
        }
    };
}
