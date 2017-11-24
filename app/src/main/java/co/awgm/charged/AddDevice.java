package co.awgm.charged;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Andrew on 24/11/2017.
 */

public class AddDevice extends AppCompatActivity {
    Spinner chargeSpinner;
    Spinner chargeTimeSpinner;
    String[] chargeType = {"USB-C","Micro USB-B","240v","Apple Lighting"};
    String[] chargeTime ={"1 Hour","2 Hours","3 Hours","4 Hours","5 Hours","6 Hours","7 Hours+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddDevice.this, android.R.layout.simple_spinner_item, chargeType);

        chargeSpinner = (Spinner) findViewById(R.id.reqCharger);
        chargeSpinner.setAdapter(adapter1);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddDevice.this, android.R.layout.simple_spinner_item, chargeTime);

        chargeTimeSpinner = (Spinner) findViewById(R.id.chargeTime);
        chargeTimeSpinner.setAdapter(adapter2);
    }


    public void onSubmit(){
        //(TextView)findViewById(TEXT1).getText().toString();
        EditText nameHolder = (EditText)findViewById(R.id.deviceName);
        String name = nameHolder.getText().toString();

        EditText deviceMakeHolder = (EditText)findViewById(R.id.deviceMake);
        String make = deviceMakeHolder.getText().toString();

        EditText deviceModelHolder = (EditText)findViewById(R.id.deviceModel);
        String model = deviceModelHolder.getText().toString();

        RadioGroup deviceSelectGroup = (RadioGroup)findViewById(R.id.deviceSelectGroup);
        int selectedRadio = deviceSelectGroup.getCheckedRadioButtonId();

        Spinner chargeTypeHolder = (Spinner)findViewById(R.id.reqCharger);
        String chargeType = chargeTypeHolder.getSelectedItem().toString();

        Spinner chargeTimeHolder = (Spinner)findViewById(R.id.chargeTime);
        String chargeTime = chargeTimeHolder.getSelectedItem().toString();

        ChargedDevice fillDevice = new ChargedDevice();
        fillDevice.setNickName(name);
        fillDevice.setChargeType(chargeType);
        fillDevice.setChargeTime(chargeTime);
        fillDevice.setMake(make);
        fillDevice.setModel(model);
        fillDevice.setType(selectedRadio);


    }
}
