package com.scrappers.churchService.localDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class LocalDatabase {
    private final AppCompatActivity context;
    private final String file;

    
    /**
     * Instantiate a new JSON database local file
     * @param context the context of the activity
     * @param file the local file to save data to & fetch it from
     */
    public LocalDatabase(AppCompatActivity context,String file){
        this.context=context;
        this.file=file;
    }

    /**
     * Writes data in the form of JSONArray under the object name
     * @param name the JSONObject that holds that JSONArray that have the local data in place
     * @param isRememberMe true if the user signed in - false otherwise
     */
    public void writeData(String name,boolean isRememberMe)  {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            jsonObject.put(name, jsonArray);
            jsonArray.put(new JSONObject().put("name",name));
            jsonArray.put(new JSONObject().put("isRememberMe",isRememberMe));
            try(BufferedWriter fos=new BufferedWriter(new FileWriter(new File(context.getFilesDir()+file)))){
                fos.write(jsonObject.getString(name));
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }catch (JSONException ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * reads the data fetched from the local file & parse it to a JSONArray
     * @return data in the form of JSONArray
     */
    public JSONArray readData(){
        try(BufferedReader fis=new BufferedReader(new FileReader(new File(context.getFilesDir()+file)))){
            return new JSONArray(fis.readLine());
        }catch (IOException | JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

}
