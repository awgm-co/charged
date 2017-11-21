package co.awgm.charged;

/**
 * Created by Andrew on 30/10/2017.
 */

public class ChargedPlace {




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
    private String categoryHandle;  //12
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

    public String getCategoryHandle() {
        return this.categoryHandle;
    }

    public void setCategoryHandle(String categoryHandle) {
        this.categoryHandle = categoryHandle;
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
            String newCategoryHandle,
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
        categoryHandle = newCategoryHandle;
        keywords = newKeywords;
    }
    }





