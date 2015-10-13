package com.example.boss.customspinnet;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MyLog";
    static ArrayList<Map<String, Object>> spData = new ArrayList<Map<String, Object>>(){{
//        add(new HashMap<String, Object>(){{ put("Name","HBH-DS970"); put("Address","00:16:B8:07:C2:B5"); }});
//        add(new HashMap<String, Object>(){{ put("Name","Smart Wireless Headset pro"); put("Address","00:EB:2D:8B:E1:6A"); }});
//        add(new HashMap<String, Object>(){{ put("Name","HC-06"); put("Address","20:13:04:23:07:20"); }});
    }};

    Spinner spinner;
    static SimpleAdapter spnAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 11) {
//            requestFeature();
//        }
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.my_actionbar);

        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        spnAdapter = new SimpleAdapter(this, spData, android.R.layout.simple_spinner_item, new String[]{"Name", "Address"}, new int[]{android.R.id.text1, android.R.id.text2});
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spnAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, String.valueOf(parent.getSelectedItemPosition()));
                Map<String, Object> m = (Map<String, Object>) parent.getSelectedItem();
                Log.d(LOG_TAG, "*** Выбрано устройство " + m.get("Name") + " MAC: " +  m.get("Address"));
                Toast.makeText(getApplicationContext(), m.get("Name") + " " +  m.get("Address"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG_TAG, String.valueOf(parent.getSelectedItemPosition()));
            }
        });
    }

    private void requestFeature() {
        try {
            Field fieldImpl = ActionBarActivity.class.getDeclaredField("mImpl");
            fieldImpl.setAccessible(true);
            Object impl = fieldImpl.get(this);

            Class cls = Class.forName("android.support.v7.app.ActionBarActivityDelegate");

            Field fieldHasActionBar = cls.getDeclaredField("mHasActionBar");
            fieldHasActionBar.setAccessible(true);
            fieldHasActionBar.setBoolean(impl, true);

        } catch (NoSuchFieldException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        }
    }
    }
