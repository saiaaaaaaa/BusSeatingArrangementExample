package com.salazarisaiahnoel.busseatingarrangementexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.salazarisaiahnoel.busseatingarrangementexample.data.BusData;

import java.util.ArrayList;
import java.util.Objects;

public class BusList extends AppCompatActivity {

    Toolbar t;
    ListView lv;
    ArrayAdapter<String> aa;
    FloatingActionButton fab;
    TextView tv;
    BusData bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bus List");

        lv = findViewById(R.id.lv);
        aa = new ArrayAdapter<>(this, R.layout.bus_list_item, R.id.bus_list_item_tv, new ArrayList<>());
        lv.setAdapter(aa);
        bd = new BusData(this);
        tv = findViewById(R.id.hint_main);

        checkBuses();

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v -> addBus());

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(BusList.this, BusSeats.class);
            i.putExtra("pos", position);
            startActivity(i);
        });

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            LayoutInflater li = LayoutInflater.from(BusList.this);
            View v1 = li.inflate(R.layout.delete_bus_dialog, null);
            Button b1 = v1.findViewById(R.id.yes_button);
            Button b2 = v1.findViewById(R.id.no_button);
            AlertDialog.Builder adb = new AlertDialog.Builder(BusList.this)
                    .setView(v1);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b1.setOnClickListener(v -> {
                bd.deleteBus(position);
                checkBuses();
                ad.cancel();
            });
            b2.setOnClickListener(v -> ad.cancel());
            return true;
        });
    }

    void checkBuses(){
        aa.clear();
        bd.sortBuses();
        aa.addAll(bd.getBuses());
        if (aa.getCount() > 0){
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
    }

    void addBus(){
        checkBuses();
        bd.addBus("Bus");
        checkBuses();
    }
}