package co.com.astransportes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.com.astransportes.R;
import co.com.astransportes.activities.client.RegisterClientActivity;
import co.com.astransportes.activities.driver.RegisterDriverActivity;
import co.com.astransportes.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {

    SharedPreferences mPreference;

    Button mBtnGoToLogin, mBtnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.createToolbar(SelectOptionAuthActivity.this, "Selecciona una opcion", true);

        mPreference = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mBtnGoToLogin = findViewById(R.id.btnGoToLogin);
        mBtnGoToRegister = findViewById(R.id.btnGoToRegister);
        mBtnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        mBtnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
    }

    private void goToRegister() {
        String selectedUser = mPreference.getString("user", "");
        if (selectedUser.equals("client")){
            Intent intentClient = new Intent(SelectOptionAuthActivity.this, RegisterClientActivity.class);
            startActivity(intentClient);
        }else if(selectedUser.equals("driver")){
            Intent intentDriver = new Intent(SelectOptionAuthActivity.this, RegisterDriverActivity.class);
            startActivity(intentDriver);
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}