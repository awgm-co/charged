package co.awgm.charged;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Andrew on 30/10/2017.
 */

class ChargedPlace {

    String locationCode;
    LatLng latLng;
    String lat;
    String lng;
    String name;
    String signage;
    String info;
    String iconFileName;
    String containerId;
    String containerName;
    String categoryId;
    String categoryHandle;
    String keywords;
    Marker mapMarker;


    public String getLat() { return this.lat; }

    public void setLat(String lat) { this.lat = lat; }

    public String getLng() { return this.lng; }

    public void setLng(String lng) { this.lng = lng; }

    public LatLng getLatLng() { return this.latLng;}

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignage() {
        return this.signage;
    }

    public void setSignage(String signage) {
        this.signage = signage;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIconFileName() {
        return this.iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getContainerId() {
        return this.containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return this.containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryHandle() {
        return this.categoryHandle;
    }

    public void setCategoryHandle(String categoryHandle) {
        this.categoryHandle = categoryHandle;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Marker getMapMarker() {
        return this.mapMarker;
    }

    public void setMapMarker(Marker mapMarker) {
        this.mapMarker = mapMarker;
    }

    public ChargedPlace(){

    }
    public ChargedPlace(
            String newLocationCode,
            String newName,
            String newLat,
            String newLng,
            String newSignage,
            String newInfo,
            String newIcon,
            String newContainerID,
            String newContainerName,
            String newCategoryID,
            String newCategoryHandle,
            String newKeywords){
        this.locationCode = newLocationCode;
        this.name = newName;
        this.lat = newLat;
        this.lng = newLng;
        this.signage = newSignage;
        this.info = newInfo;
        this.iconFileName = newIcon;
        this.containerId = newContainerID;
        this.containerName = newContainerName;
        this.categoryId = newCategoryID;
        this.categoryHandle = newCategoryHandle;
        this.keywords = newKeywords;
    }
    public ChargedPlace(
            String newLocationCode,
            String newName,
            LatLng newLatLng,
            String newSignage,
            String newInfo,
            String newIcon,
            String newContainerID,
            String newContainerName,
            String newCategoryID,
            String newCategoryHandle,
            String newKeywords){
        this.locationCode = newLocationCode;
        this.name = newName;
        this.latLng = newLatLng;
        this.signage = newSignage;
        this.info = newInfo;
        this.iconFileName = newIcon;
        this.containerId = newContainerID;
        this.containerName = newContainerName;
        this.categoryId = newCategoryID;
        this.categoryHandle = newCategoryHandle;
        this.keywords = newKeywords;
    }




}
