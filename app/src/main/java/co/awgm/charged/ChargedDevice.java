package co.awgm.charged;

/**
 * Created by Andrew on 23/11/2017.
 */

public class ChargedDevice {





    private int ID;
    private String nickName;
    private String chargeType;
    private String imageType;
    private String make;
    private String model;
    private String chargeTime;
    private String type;


    public int getID() { return this.ID; }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNickName() { return this.nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getChargeType() { return this.chargeType; }

    public void setChargeType(String chargeType) { this.chargeType = chargeType; }

    public String getImageType() { return this.imageType; }

    public void setImageType(String imageType) { this.imageType = imageType; }

    public String getMake() { return this.make; }

    public void setMake(String make) { this.make = make; }

    public String getModel() { return this.model; }

    public void setModel(String model) { this.model = model; }

    public String getChargeTime() { return this.chargeTime; }

    public void setChargeTime(String chargeTime) { this.chargeTime = chargeTime; }

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    public ChargedDevice(){
        //Default Constructor
    }

    public ChargedDevice(
            String _nickName,
            String _chargeType,
            String _chargeTime,
            String _make,
            String _model,
            String _imageType)
    {
        nickName = _nickName;
        chargeType = _chargeType;
        chargeTime = _chargeTime;
        make = _make;
        model = _model;
        imageType = _imageType;
    }

}
