package com.example.hahua.busscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c =null;

    //private constructor so object creation outside is not allowed
    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }

    //returns single instance of databse
    public static DatabaseAccess getInstance(Context context){
        if(instance ==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //opening the database
    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    //Close
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    //method to query and return
    //get stop address from name
    public String getAddress(String name){
        //WHERE sp_name = '"+name+"'

        c=db.rawQuery("SELECT sp_address FROM stops WHERE sp_name ='"+name+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String address =c.getString(0);
            buffer.append(""+address);
        }
        return buffer.toString();
    }

    public String getTimes(String name){
        c=db.rawQuery("SELECT p_arrivaltime, sp_name FROM partof,stops WHERE p_stopID = sp_stopID AND sp_name ='"+name+"' GROUP BY p_arrivaltime",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String address =c.getString(0);
            buffer.append(""+address);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String getTimesID(int ID){
        c=db.rawQuery("SELECT p_arrivaltime FROM partof WHERE p_stopID = '"+ID+"'GROUP BY p_arrivaltime",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String times =c.getString(0);
            buffer.append(""+times);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String getRouteInfo(String name,String bname){
        c=db.rawQuery("SELECT DISTINCT sp_stopID,sp_name,p_arrivaltime FROM stops,partof,routes,runson,schedule,service,bus WHERE p_stopID = sp_stopID AND p_routeID = r_routeID AND r_routeID = ro_routeID AND ro_scheduleID = s_scheduleID AND s_busID = b_busID AND r_routename = '"+name+"' AND b_busname= '"+bname+"' AND p_scheduleID = '1'ORDER BY p_set",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            int ID = c.getInt(0);

            String sname = c.getString(1);
            String times = c.getString(2);
            buffer.append(bname+"|"+ID+"|"+sname+"|"+times);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String giveStop(int ID, String name,String address){
        if(ID<0 ) {
            return "failed insertion";
        }
        if(name == null || name.isEmpty()) {
            return "incomplete input";
        }
        if(address == null || address.isEmpty()) {
            return "incomplete input";
        }
        ContentValues contentValues=new ContentValues();
        contentValues.put("sp_stopID",ID);
        contentValues.put("sp_name",name);
        contentValues.put("sp_address",address);
        long result =db.insert("stops",null,contentValues);
        StringBuffer buffer = new StringBuffer();
        if(result >0) {
            buffer.append("Succesfully inserted at row "+result);
        }else{
            buffer.append("Encountered error");
        }
        return buffer.toString();
    }

    public String deleteData(String id){
        long result =db.delete("stops", "sp_stopID= ?",new String[]{id});
        long temp = db.delete ("partof", "p_stopID = ?",new String[]{id});
        StringBuffer buffer = new StringBuffer();
        result = result + temp;
        if(result ==0){
            buffer.append("Nothing was deleted");
        }else if(result >0){
            buffer.append(result+" rows deleted");
        }else {
            buffer.append("Encountered error");
        }
        return buffer.toString();
    }

    public String updateData(String ID,String name, String address){
        int id = Integer.parseInt(ID);
        ContentValues contentValues=new ContentValues();
        contentValues.put("sp_stopID",id);
        if(name != null && !name.isEmpty()) {
            contentValues.put("sp_name", name);
        }
        if(address != null && !address.isEmpty()) {
            contentValues.put("sp_address", address);
        }
        long result= db.update("stops",contentValues,"sp_stopID ="+id,null);
        StringBuffer buffer = new StringBuffer();
        if(result >0) {
           // result--;
            buffer.append("Successfully updated "+result+" rows");
        }else{
            buffer.append("Encountered error");
        }
        return buffer.toString();
    }
}
