package co.com.astransportes.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import co.com.astransportes.R;
import co.com.astransportes.activities.client.RegisterClientActivity;
import co.com.astransportes.includes.MyToolbar;
import co.com.astransportes.models.Client;
import co.com.astransportes.models.Driver;
import co.com.astransportes.providers.AuthProvider;
import co.com.astransportes.providers.ClientProvider;
import co.com.astransportes.providers.DriverProvider;
import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;

    TextInputEditText   mTilNameRegister, mTilEmailRegister, mTilPasswordRegister,
                        mTilVehicleRegister, mTilPlateRegister;
    Button mBtnRegister;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.createToolbar(RegisterDriverActivity.this, getString(R.string.registrate), true);

        mTilNameRegister = findViewById(R.id.tilNameRegister);
        mTilEmailRegister = findViewById(R.id.tilEmailRegister);
        mTilPasswordRegister = findViewById(R.id.tilPasswordRegister);
        mTilVehicleRegister = findViewById(R.id.tilVehicleBrandRegister);
        mTilPlateRegister = findViewById(R.id.tilPlateRegister);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        mDialog = new SpotsDialog.Builder().setContext(RegisterDriverActivity.this).setMessage("R.string.message_dialog").build();

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });
    }

    public void clickRegister(){
        String name = mTilNameRegister.getText().toString();
        String email = mTilEmailRegister.getText().toString();
        String password = mTilPasswordRegister.getText().toString();
        String vehicle = mTilVehicleRegister.getText().toString();
        String plate = mTilPlateRegister.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !vehicle.isEmpty() && !plate.isEmpty()){
            if (password.length() >= 6){
                mDialog.show();
                register(name, email, password, vehicle, plate);
            } else{
                Toast.makeText(RegisterDriverActivity.this, "La contraseña deber teneral menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(RegisterDriverActivity.this, "Revisa todos los campos e intentalo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    public void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(String name, String email, String password, String vehicle, String plate){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id, name, email, vehicle, plate);
                    create(driver);
                } else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
}