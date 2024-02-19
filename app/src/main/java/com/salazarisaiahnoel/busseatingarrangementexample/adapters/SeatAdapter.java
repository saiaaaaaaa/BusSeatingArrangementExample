package com.salazarisaiahnoel.busseatingarrangementexample.adapters;

import static com.salazarisaiahnoel.busseatingarrangementexample.BusSeats.checkSeats;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.salazarisaiahnoel.busseatingarrangementexample.R;
import com.salazarisaiahnoel.busseatingarrangementexample.data.BusData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatHolder> {

    final Context c;
    final List<String> list;
    final BusData bd;

    public SeatAdapter(Context c, List<String> list){
        this.c = c;
        this.list = list;
        bd = new BusData(c);
    }

    @NonNull
    @Override
    public SeatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_row, parent, false);
        return new SeatHolder(v);
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

    @Override
    public void onBindViewHolder(@NonNull SeatHolder holder, int position) {
        String[] a = list.get(position).split(";");
        if (a.length > 4){
            List<Integer> seat = new ArrayList<>();
            for (int b = 0; b < a.length; b++){
                if (a[b].split("==")[2].equalsIgnoreCase("Empty")){
                    seat.add((b + 1));
                }
            }
            if (seat.contains(1)){
                holder.i1.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i1.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(2)){
                holder.i2.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i2.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(3)){
                holder.i3.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i3.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(4)){
                holder.i4.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i4.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(5)){
                holder.i5.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i5.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            holder.i1.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[0].split("==")[0]);
                e1.setText(a[0].split("==")[1]);
                e2.setText(a[0].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v218 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v217 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i2.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[1].split("==")[0]);
                e1.setText(a[1].split("==")[1]);
                e2.setText(a[1].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v216 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v215 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i3.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[2].split("==")[0]);
                e1.setText(a[2].split("==")[1]);
                e2.setText(a[2].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v214 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v213 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i4.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[3].split("==")[0]);
                e1.setText(a[3].split("==")[1]);
                e2.setText(a[3].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v212 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v211 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i5.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[4].split("==")[0]);
                e1.setText(a[4].split("==")[1]);
                e2.setText(a[4].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v210 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v29 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
        } else {
            List<Integer> seat = new ArrayList<>();
            for (int b = 0; b < a.length; b++){
                if (a[b].split("==")[2].equalsIgnoreCase("Empty")){
                    seat.add((b + 1));
                }
            }
            if (seat.contains(1)){
                holder.i1.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i1.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(2)){
                holder.i2.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i2.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            holder.i3.setImageDrawable(null);
            if (seat.contains(3)){
                holder.i4.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i4.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            if (seat.contains(4)){
                holder.i5.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512));
            } else {
                holder.i5.setImageDrawable(AppCompatResources.getDrawable(c, R.drawable.baseline_event_seat_512_red));
            }
            holder.i1.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[0].split("==")[0]);
                e1.setText(a[0].split("==")[1]);
                e2.setText(a[0].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v28 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v27 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i2.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[1].split("==")[0]);
                e1.setText(a[1].split("==")[1]);
                e2.setText(a[1].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v26 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v25 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i3.setOnClickListener(null);
            holder.i4.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[2].split("==")[0]);
                e1.setText(a[2].split("==")[1]);
                e2.setText(a[2].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v24 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v23 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
            holder.i5.setOnClickListener(v -> {
                LayoutInflater li = LayoutInflater.from(c);
                View v1 = li.inflate(R.layout.edit_seat_dialog, null);
                Button b1 = v1.findViewById(R.id.edit_button);
                Button b2 = v1.findViewById(R.id.delete_button);
                TextView t = v1.findViewById(R.id.edit_bus_name);
                TextView e1 = v1.findViewById(R.id.edit_seat_number);
                EditText e2 = v1.findViewById(R.id.edit_name);
                t.setText(a[3].split("==")[0]);
                e1.setText(a[3].split("==")[1]);
                e2.setText(a[3].split("==")[2]);
                AlertDialog.Builder adb = new AlertDialog.Builder(c)
                        .setView(v1);
                AlertDialog ad = adb.create();
                Objects.requireNonNull(ad.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ad.show();
                b1.setOnClickListener(v22 -> {
                    String seatname = fixSpaces(e2.getText().toString().trim());
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

                    if (validname) {
                        bd.editSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                        checkSeats();
                        ad.cancel();
                    }
                });
                b2.setOnClickListener(v2 -> {
                    bd.deleteSeat(t.getText().toString(), e1.getText().toString(), e2.getText().toString());
                    checkSeats();
                    ad.cancel();
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SeatHolder extends RecyclerView.ViewHolder {

        final ImageView i1;
        final ImageView i2;
        final ImageView i3;
        final ImageView i4;
        final ImageView i5;

        public SeatHolder(@NonNull View itemView) {
            super(itemView);

            i1 = itemView.findViewById(R.id.img1);
            i2 = itemView.findViewById(R.id.img2);
            i3 = itemView.findViewById(R.id.img3);
            i4 = itemView.findViewById(R.id.img4);
            i5 = itemView.findViewById(R.id.img5);
        }
    }
}
