package co.com.astransportes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import co.com.astransportes.R;
import co.com.astransportes.activities.client.MapClientActivity;
import co.com.astransportes.activities.driver.MapDriverActivity;

public class MainActivity extends AppCompatActivity {

    Button mBtnDriver, mBtnClient;
    SharedPreferences mPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDriver = findViewById(R.id.btnDriver);
        mBtnClient = findViewById(R.id.btnClient);

        mPreference = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreference.edit();

        mBtnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectAuth();
                editor.putString("user", "driver");
                editor.apply();
            }
        });

        mBtnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectAuth();
                editor.putString("user", "client");
                editor.apply();
            }
        });
    }

    private void goToSelectAuth(){
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            String user = mPreference.getString("user", "");
            if (user.equals("client")){
                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                startActivity(intent);
            }
        }
    }
}