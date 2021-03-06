package co.awgm.charged;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew on 30/10/2017.
 */

public class ChargedPlace implements Parcelable{




    private int ID;
    private String locationCode;    //0
    private String name;            //1
    private String lat;             //2
    private String lng;             //3
    private String signage;         //4
    private String info;            //5
    private String iconFileName;    //6
    private String containerId;     //7
    private String containerName;   //8
    private String categoryId;      //9
    private String categoryName;    //12
    private String keywords;        //13


    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
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

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


    public ChargedPlace(){

    }

    public ChargedPlace(
            String newLocationCode,
            String newLat,
            String newLng,
            String newName,
            String newSignage,
            String newInfo,
            String newIcon,
            String newContainerID,
            String newContainerName,
            String newCategoryID,
            String newCategoryName,
            String newKeywords) {
        locationCode = newLocationCode;
        lat = newLat;
        lng = newLng;
        name = newName;
        signage = newSignage;
        info = newInfo;
        iconFileName = newIcon;
        containerId = newContainerID;
        containerName = newContainerName;
        categoryId = newCategoryID;
        categoryName = newCategoryName;
        keywords = newKeywords;
    }

       /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ID);
        out.writeString(locationCode);
        out.writeString(lat);
        out.writeString(lng);
        out.writeString(name);
        out.writeString(signage);
        out.writeString(info);
        out.writeString(iconFileName);
        out.writeString(containerId);
        out.writeString(containerName);
        out.writeString(categoryId);
        out.writeString(categoryName);
        out.writeString(keywords);
    }

    public static final Parcelable.Creator<ChargedPlace> CREATOR = new Parcelable.Creator<ChargedPlace>() {
        public ChargedPlace createFromParcel(Parcel in) {
            return new ChargedPlace(in);
        }

        public ChargedPlace[] newArray(int size) {
            return new ChargedPlace[size];
        }
    };

    private ChargedPlace (Parcel in) {
        ID = in.readInt();
        locationCode = in.readString();
        lat = in.readString();
        lng = in.readString();
        name = in.readString();
        signage = in.readString();
        info = in.readString();
        iconFileName = in.readString();
        containerId = in.readString();
        containerName = in.readString();
        categoryId = in.readString();
        categoryName = in.readString();
        keywords = in.readString();

    }
    }





