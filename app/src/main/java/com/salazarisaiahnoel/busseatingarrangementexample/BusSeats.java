package com.salazarisaiahnoel.busseatingarrangementexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.salazarisaiahnoel.busseatingarrangementexample.adapters.SeatAdapter;
import com.salazarisaiahnoel.busseatingarrangementexample.data.BusData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BusSeats extends AppCompatActivity {

    Toolbar t;
    static BusData bd;
    static List<String> seats;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static String busname = "";
    @SuppressLint("StaticFieldLeak")
    static SeatAdapter sa;
    ImageView add;
    @SuppressLint("StaticFieldLeak")
    static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seats);

        c = this;
        bd = new BusData(this);

        busname = bd.getBuses().get(getIntent().getIntExtra("pos", 0));

        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        Objects.requireNonNull(getSupportActionBar()).setTitle(bd.getBuses().get(getIntent().getIntExtra("pos", 0)));

        add = t.findViewById(R.id.seats_add);

        seats = new ArrayList<>();
        rv = findViewById(R.id.rv);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        checkSeats();

        add.setOnClickListener(v -> {
            LayoutInflater li = LayoutInflater.from(BusSeats.this);
            View v1 = li.inflate(R.layout.add_seat_dialog, null);
            Button b = v1.findViewById(R.id.add_button);
            TextView t = v1.findViewById(R.id.add_bus_name);
            EditText e1 = v1.findViewById(R.id.add_seat_number);
            EditText e2 = v1.findViewById(R.id.add_name);
            t.setText(busname);
            AlertDialog.Builder adb = new AlertDialog.Builder(BusSeats.this)
                    .setView(v1);
            AlertDialog ad = adb.create();
            Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b.setOnClickListener(v2 -> {
                String seatnumber = e1.getText().toString().trim();
                String seatname = fixSpaces(e2.getText().toString().trim());
                boolean validnum = false;
                boolean validname = false;

                if (hasInvalid(seatname) || TextUtils.isEmpty(seatname)) {
                    if (hasInvalid(seatname)) {
                        e2.setError("Name must not contain numbers or symbols.");
                    }
                    if (TextUtils.isEmpty(seatname)) {
                        e2.setError("Name must not be empty.");
                    }
                } else {
                    validname = true;
                }

                for (String a : bd.getSeats()) {
                    System.out.println("CONTAINS:" + a);
                }

                if (TextUtils.isEmpty(seatnumber)) {
                    e1.setError("Seat number must not be empty.");
                } else if (Integer.parseInt(seatnumber) > 45 || Integer.parseInt(seatnumber) <= 0) {
                    e1.setError("Seat number must only be between 1-45.");
                } else {
                    validnum = true;
                    for (int a = 0; a < bd.getSeats().size(); a++) {
                        if (bd.getSeats().get(a).contains(busname + "==" + seatnumber)) {
                            validnum = false;
                            e1.setError("Seat is already taken.");
                            break;
                        }
                    }
                }

                if (validnum && validname) {
                    bd.addSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                }
            });
        });
    }

    String fixSpaces(String a){
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for (int b = 0; b < a.length(); b++){
            if (a.charAt(b) == ' '){
                counter++;
            } else {
                if (counter > 1){
                    res.append(" ").append(a.charAt(b));
                    counter = 0;
                } else {
                    res.append(a.charAt(b));
                }
            }
        }
        return res.toString();
    }

    boolean hasInvalid(String a){
        String inv = "~`!@#$%^&*()_-+=[]{}\\|;:'\",.<>/?1234567890";
        for (int b = 0; b < a.length(); b++){
            for (int c = 0; c < inv.length(); c++){
                if (a.charAt(b) == inv.charAt(c)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkSeats(){
        seats.clear();

        for (int a = 0; a < 44; a += 4){
            StringBuilder qe = new StringBuilder();
            if (a == 40){
                for (int b = a; b < (a + 5); b++){
                    boolean toBreak = false;
                    int c = 0;
                    while (true){
                        if (c >= bd.getSeats().size()){
                            break;
                        } else if (bd.getSeats().get(c).split("==")[0].equals(busname) && Integer.parseInt(bd.getSeats().get(c).split("==")[1]) - 1 == b){
                            qe.append(bd.getSeats().get(c)).append(";");
                            toBreak = true;
                            break;
                        } else {
                            c++;
                        }
                    }
                    if (!toBreak){
                        qe.append(busname).append("==").append(b + 1).append("==Empty;");
                    }
                }
            } else {
                for (int b = a; b < (a + 4); b++){
                    boolean toBreak = false;
                    int c = 0;
                    while (true){
                        if (c >= bd.getSeats().size()){
                            break;
                        } else if (bd.getSeats().get(c).split("==")[0].equals(busname) && Integer.parseInt(bd.getSeats().get(c).split("==")[1]) - 1 == b){
                            qe.append(bd.getSeats().get(c)).append(";");
                            toBreak = true;
                            break;
                        } else {
                            c++;
                        }
                    }
                    if (!toBreak){
                        qe.append(busname).append("==").append(b + 1).append("==Empty;");
                    }
                }
            }
            seats.add(qe.toString());
        }

        sa = new SeatAdapter(c, seats);
        rv.setAdapter(sa);

        for (String a : seats){
            System.out.println(a);
        }
    }
}