package com.example.hahua.busscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MainActivity extends AppCompatActivity{
    static Connection conn = null;
    static PreparedStatement prep = null;
    static ResultSet rs = null;
    String temp="M1 - Merced West";

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.main_menu);

    }



    public void goBack(View view){
        setContentView(R.layout.main_menu);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText);
        String message = editText.getText().toString();

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String address = databaseAccess.getAddress(message);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, address);
        startActivity(intent);
    }

    public void getStop(View view){
        setContentView(R.layout.activity_main);
    }

    public void gotoArrival(View view){
        setContentView(R.layout.arrival_menu);
    }

    public void gotoRoute(View view) {
        setContentView(R.layout.route_info_menu);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"M1 - Merced West", "M2 - R Street Shuttle", "M3 - M Street Shuttle", "M4 - G Street Shuttle", "M5 - Merced South-East", "M6 - Olive Loops", "UC - UC Merced"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        temp = "M1 - Merced West";
                        break;
                    case 1:
                        temp = "M2 - R Street Shuttle";
                        break;
                    case 2:
                        temp = "M3 - M Street Shuttle";
                        break;
                    case 3:
                        temp = "M4 - G Street Shuttle";
                        break;
                    case 4:
                        temp = "M5 - Merced South-East";
                        break;
                    case 5:
                        temp = "M6 - Olive Loops";
                        break;
                    case 6:
                        temp = "UC - UC Merced";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void gotoInsertStop(View view) { setContentView(R.layout.stopinsert_menu);}

    public void gotoUpdateStop(View view) {setContentView(R.layout.stopupdate_menu);}

    // Called when Arrival Times text box filled called
    public void getTimes(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText2);
        String message = editText.getText().toString();

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String address = databaseAccess.getTimes(message);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, address);
        startActivity(intent);
    }

    public void getTimesID(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText3);
        String message = editText.getText().toString();
        int finalID = -1;
        if(editText.getText().toString().length() > 0) {
            finalID =Integer.parseInt(message);
        }

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String address = databaseAccess.getTimesID(finalID);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, address);
        startActivity(intent);
    }

    public void getRoute(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText4);
        String message = editText.getText().toString();


        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();



        String bname = temp;

        String address = databaseAccess.getRouteInfo(message,bname);


        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, address);
        startActivity(intent);
    }

    public void giveStop(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText5);

        String ID = editText.getText().toString();
        int finalID = -1;
        if(editText.getText().toString().length() > 0) {
            finalID =Integer.parseInt(ID);
        }
        EditText editText1 = (EditText)findViewById(R.id.editText6);
        String name = editText1.getText().toString();

        EditText editText2= (EditText)findViewById(R.id.editText7);
        String address = editText2.getText().toString();

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String message = databaseAccess.giveStop(finalID,name,address);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void deleteStop(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText8);

        String ID = editText.getText().toString();


        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String message = databaseAccess.deleteData(ID);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void updateStops(View view){
        Intent intent = new Intent(this, DisplayMesage.class);
        EditText editText = (EditText)findViewById(R.id.editText9);

        String ID = editText.getText().toString();
        int finalID = -1;
        if(editText.getText().toString().length() > 0) {
            finalID =Integer.parseInt(ID);
        }
        EditText editText1 = (EditText)findViewById(R.id.editText10);
        String name = editText1.getText().toString();

        EditText editText2= (EditText)findViewById(R.id.editText11);
        String address = editText2.getText().toString();

        DatabaseAccess databaseAccess =DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String message = databaseAccess.updateData(ID,name,address);

        databaseAccess.close();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
