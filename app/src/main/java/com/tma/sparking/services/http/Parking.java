package com.tma.sparking.services.http;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class Parking {
    @SerializedName("channel")
    private Channel mChannel;

    @SerializedName("feeds")
    private List<Feed> mFeeds;

    public Channel getChannel() {
        return mChannel;
    }

    public void setChannel(Channel channel) {
        mChannel = channel;
    }

    public List<Feed> getFeeds() {
        return mFeeds;
    }

    public void setFeeds(List<Feed> feeds) {
        mFeeds = feeds;
    }
}