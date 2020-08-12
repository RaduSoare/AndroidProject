package com.example.try1;

public class Location {

    private String locationName;
    private String locationSpecific;
    private String locationAdress;
    private int thumbnailID;
    private String description;
    private String thumbnailLink;


    public Location(String locationName, String locationSpecific, String locationAdress, int thumbanilID, String description, String thumbnailLink) {
        this.locationName = locationName;
        this.locationSpecific = locationSpecific;
        this.locationAdress = locationAdress;
        this.thumbnailID = thumbanilID;
        this.description = description;
        this.thumbnailLink = thumbnailLink;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationSpecific() {
        return locationSpecific;
    }

    public void setLocationSpecific(String locationSpecific) {
        this.locationSpecific = locationSpecific;
    }

    public String getLocationAdress() {
        return locationAdress;
    }

    public void setLocationAdress(String locationAdress) {
        this.locationAdress = locationAdress;
    }

    public int getThumbnailID() {
        return thumbnailID;
    }

    public void setThumbnailID(int thumbnailID) {
        this.thumbnailID = thumbnailID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }
}
