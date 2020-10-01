package com.scrappers.churchService.localDatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileImage {
    private final AppCompatActivity context;
    private  Intent rawData;
    private  String path;

    public ProfileImage(AppCompatActivity context, @NonNull Intent rawData){
        this.context=context;
        this.rawData=rawData;
    }
    public ProfileImage(AppCompatActivity context , String path){
        this.context=context;
        this.path=path;
    }

    @NonNull
    private Intent getRawData() {
        return rawData;
    }
    @NonNull
    public Bitmap getProfileImageFromRawData(){
        String finalPath = Environment.getExternalStorageDirectory()+"/"+Objects.requireNonNull(
                Objects.requireNonNull(
                        getRawData().getData()).getPath()).replace("document/primary:","");

        return BitmapFactory.decodeFile(finalPath);
    }
    public void saveProfileImage(){
        try{
            getProfileImageFromRawData().compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(new File(context.getFilesDir(),"user/profileImage.png")));
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    }
    public Bitmap getProfileImageFromFile() {
        System.out.println(path);
        return BitmapFactory.decodeFile(path);
    }

    public String getPath() {
        return path;
    }

}
