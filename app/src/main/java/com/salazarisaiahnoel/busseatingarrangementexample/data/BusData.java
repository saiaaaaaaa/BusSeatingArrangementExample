package com.salazarisaiahnoel.busseatingarrangementexample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BusData extends SQLiteOpenHelper {

    final static String database = "buses";

    public BusData(Context c){
        super(c, database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE buslist(busname text)";
        String q2 = "CREATE TABLE busseats(busname text, seatnumber text, name text)";
        db.execSQL(q);
        db.execSQL(q2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }

    public void editSeat(String busname, String seatnumber, String name){
        List<String> a = getSeats();
        if (a.size() == 0){
            addSeat(busname, seatnumber, name);
        } else {
            for (int b = 0; b < a.size(); b++){
                String[] c = a.get(b).split("==");
                if (c[0].equalsIgnoreCase(busname) && c[1].equalsIgnoreCase(seatnumber) && !c[2].equalsIgnoreCase("Empty")){
                    //noinspection SuspiciousListRemoveInLoop
                    a.remove(b);
                    SQLiteDatabase db = getReadableDatabase();
                    String q = "DROP TABLE busseats";
                    db.execSQL(q);
                    String q1 = "CREATE TABLE busseats(busname text, seatnumber text, name text)";
                    db.execSQL(q1);
                    for (int d = 0; d < a.size(); d++){
                        String[] e = a.get(d).split("==");
                        addSeat(e[0], e[1], e[2]);
                    }
                    addSeat(busname, seatnumber, name);
                } else {
                    addSeat(busname, seatnumber, name);
                }
            }
        }
    }

    public void deleteSeat(String busname, String seatnumber, String name){
        List<String> a = getSeats();
        for (int b = 0; b < a.size(); b++){
            if (a.contains(busname + "==" + seatnumber + "==" + name)){
                a.remove(busname + "==" + seatnumber + "==" + name);
                SQLiteDatabase db = getReadableDatabase();
                String q = "DROP TABLE busseats";
                db.execSQL(q);
                String q1 = "CREATE TABLE busseats(busname text, seatnumber text, name text)";
                db.execSQL(q1);
                for (int d = 0; d < a.size(); d++){
                    String[] e = a.get(d).split("==");
                    addSeat(e[0], e[1], e[2]);
                }
            }
        }
    }

    public void deleteBus(int position){
        List<String> a = getBuses();
        String busname = a.get(position);
        a.remove(position);
        String[] aa = new String[a.size()];
        for (int c = 0; c < a.size(); c++){
            aa[c] = a.get(c).split(" ")[1];
        }
        BigInteger[] bi = new BigInteger[aa.length];
        for (int d = 0; d < aa.length; d++){
            bi[d] = new BigInteger(aa[d]);
        }
        SQLiteDatabase db = getReadableDatabase();
        String q = "DROP TABLE buslist";
        db.execSQL(q);
        String q1 = "CREATE TABLE buslist(busname text)";
        db.execSQL(q1);
        Arrays.sort(bi);
        for (int d = 0; d < a.size(); d++){
            addBusLegacy("Bus " + bi[d]);
        }
        List<String> b = getSeats();
        Iterator<String> iterator = b.iterator();
        while (iterator.hasNext()){
            String bi1 = iterator.next();
            if (bi1.contains(busname)){
                iterator.remove();
            }
        }
        SQLiteDatabase db1 = getReadableDatabase();
        String q1w = "DROP TABLE busseats";
        db1.execSQL(q1w);
        String q11 = "CREATE TABLE busseats(busname text, seatnumber text, name text)";
        db1.execSQL(q11);
        for (int c = 0; c < b.size(); c++){
            String[] d = b.get(c).split("==");
            addSeat(d[0], d[1], d[2]);
        }
    }

    public void sortBuses(){
        List<String> a = getBuses();
        String[] aa = new String[a.size()];
        for (int c = 0; c < a.size(); c++){
            aa[c] = a.get(c).split(" ")[1];
        }
        BigInteger[] bi = new BigInteger[aa.length];
        for (int d = 0; d < aa.length; d++){
            bi[d] = new BigInteger(aa[d]);
        }
        SQLiteDatabase db = getReadableDatabase();
        String q = "DROP TABLE buslist";
        db.execSQL(q);
        String q1 = "CREATE TABLE buslist(busname text)";
        db.execSQL(q1);
        Arrays.sort(bi);
        for (int d = 0; d < a.size(); d++){
            addBusLegacy("Bus " + bi[d]);
        }
    }


    public void addBusLegacy(String busname){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("busname", busname);

        db.insert("buslist", null, cv);
    }

    public void addBus(String busname){
        SQLiteDatabase db = getReadableDatabase();

        int busNumber = findAvailableBusNumber(db);

        ContentValues cv = new ContentValues();
        cv.put("busname", busname + " " + busNumber);

        db.insert("buslist", null, cv);
    }

    int findAvailableBusNumber(SQLiteDatabase db) {
        int busNumber = 1;

        Cursor cursor = db.rawQuery("SELECT busname FROM buslist", null);
        while (cursor.moveToNext()) {
            String busName = cursor.getString(0);
            String[] parts = busName.split(" ");
            if (parts.length > 1) {
                try {
                    int number = Integer.parseInt(parts[1]);
                    if (number == busNumber) {
                        busNumber++;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        cursor.close();
        return busNumber;
    }

    public void addSeat(String busname, String seatnumber, String name){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("busname", busname);
        cv.put("seatnumber", seatnumber);
        cv.put("name", name);

        db.insert("busseats", null, cv);
    }

    public List<String> getBuses(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> a = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM buslist", null);
        while (c.moveToNext()){
            a.add(c.getString(0));
        }
        c.close();
        return a;
    }

    public List<String> getSeats(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> a = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM busseats", null);
        while (c.moveToNext()){
            a.add(c.getString(0) + "==" + c.getString(1) + "==" + c.getString(2));
        }
        c.close();
        return a;
    }
}
