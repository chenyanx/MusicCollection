package com.example.jeremychen.musicapp.utils;

/**
 * Created by jeremychen on 2018/7/25.
 */

public class Music {

    private String artistName;
    private String musicName;
    private String onlineMusicUrl;
    private String picUrl;
    private String collectionPrice;
    private String trackPrice;
    private String country;
    private String currency;
    private String kindMusic;
    private String releaseDate;
    private String trackViewUrl;
    private int trackTime;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(int trackTime) {
        this.trackTime = trackTime;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getOnlineMusicUrl() {
        return onlineMusicUrl;
    }

    public void setOnlineMusicUrl(String onlineMusicUrl) {
        this.onlineMusicUrl = onlineMusicUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getKindMusic() {
        return kindMusic;
    }

    public void setKindMusic(String kindMusic) {
        this.kindMusic = kindMusic;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }
}
