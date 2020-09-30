package com.scrappers.churchService.signOut;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scrappers.churchService.localDatabase.LocalDatabase;
import com.scrappers.churchService.mainScreens.RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SignOut {
    private final AppCompatActivity context;
    private String servantName= "";

    /**
     * Define a context for a sign out procedure
     * @param context holder activity context
     */
    public SignOut(AppCompatActivity context){
        this.context=context;
    }

    /**
     * starts sign out by deleting local Data & opening the register activity
     */
    public void startSession(){
        context.startActivity(new Intent(context, RegisterActivity.class));
        context.finish();
        LocalDatabase localDatabase =new LocalDatabase(context,"/user/user.json");
        try {
            servantName = localDatabase.readData(0,"name").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*removing local user files*/
        localDatabase.removeLocalFiles();
    }

    /**
     * delete the user data from the FirebaseDatabase
     */
    public void deleteRealTimeDataBase(){
        DatabaseReference userNode= FirebaseDatabase.getInstance().getReference().child("Gnod").child("servants").child(servantName);
        /*removing realTime Database file for this user*/
        userNode.removeValue();
        /*Message*/
        Toast.makeText(context,"تم مسح بيانات الخادم",Toast.LENGTH_LONG).show();
    }
}
