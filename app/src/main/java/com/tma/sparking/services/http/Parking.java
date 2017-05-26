package com.tma.sparking.services.http;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represent a parking, include channel and a list of feeds
 */
class Parking {
    @SerializedName("channel")
    private Channel mChannel;

    @SerializedName("feeds")
    private List<Feed> mFeeds;

    Channel getChannel() {
        return mChannel;
    }

    void setChannel(Channel channel) {
        mChannel = channel;
    }

    List<Feed> getFeeds() {
        return mFeeds;
    }

    void setFeeds(List<Feed> feeds) {
        mFeeds = feeds;
    }
}
