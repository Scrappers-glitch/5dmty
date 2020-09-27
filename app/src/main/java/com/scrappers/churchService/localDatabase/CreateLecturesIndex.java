package com.scrappers.churchService.localDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.System.out;

public class CreateLecturesIndex {
    private final AppCompatActivity context;

    /**
     * create lectures index with length for firebase database
     * @param context class context of use
     */
    public CreateLecturesIndex(AppCompatActivity context){
        this.context=context;
    }

    /**
     * add a new lecture
     */
    public void addNewLecture(){
        out.println(new File(context.getFilesDir()+"/user/lectures").mkdirs());
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(new File(context.getFilesDir()+"/user/lectures/Lecture "+(getLecturesLength()-1))))){
            bufferedWriter.write("lecture");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * reads the data fetched from the local file & parse it to a JSONArray
     * @return data in the form of JSONArray
     */
    public int getLecturesLength(){
        try {
            return Objects.requireNonNull(new File(context.getFilesDir() + "/user/lectures").listFiles()).length;
        }catch (NullPointerException e){
            System.err.println(e.getMessage());
            return 0;
        }
    }
}
