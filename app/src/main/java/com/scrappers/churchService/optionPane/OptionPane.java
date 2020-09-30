package com.scrappers.churchService.optionPane;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class OptionPane {

    private final AppCompatActivity context;
    private View inflater;
    private AlertDialog alertDialog;

    public OptionPane(AppCompatActivity context){
        this.context=context;
    }
    public void showDialog(int designedLayout,int gravity){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        inflater=context.getLayoutInflater().inflate(designedLayout,null);
        builder.setView(inflater);

        alertDialog=builder.create();
        assert  alertDialog.getWindow() !=null;
        alertDialog.getWindow().setGravity(gravity);
        alertDialog.show();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public View getInflater() {
        return inflater;
    }
}