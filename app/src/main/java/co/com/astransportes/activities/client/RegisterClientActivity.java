package co.com.astransportes.activities.client;

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

import co.com.astransportes.R;
import co.com.astransportes.activities.driver.MapDriverActivity;
import co.com.astransportes.activities.driver.RegisterDriverActivity;
import co.com.astransportes.includes.MyToolbar;
import co.com.astransportes.models.Client;
import co.com.astransportes.providers.AuthProvider;
import co.com.astransportes.providers.ClientProvider;
import dmax.dialog.SpotsDialog;

public class RegisterClientActivity extends AppCompatActivity {

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    TextInputEditText mTilNameRegister, mTilEmailRegister, mTilPasswordRegister;
    Button mBtnRegister;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        MyToolbar.createToolbar(RegisterClientActivity.this, getString(R.string.registrate), true);

        mTilNameRegister = findViewById(R.id.tilNameRegister);
        mTilEmailRegister = findViewById(R.id.tilEmailRegister);
        mTilPasswordRegister = findViewById(R.id.tilPasswordRegister);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        mDialog = new SpotsDialog.Builder().setContext(RegisterClientActivity.this).setMessage(R.string.message_dialog).build();

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

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if (password.length() >= 6){
                mDialog.show();
                register(name, email, password);
            } else{
                Toast.makeText(RegisterClientActivity.this, "La contraseña deber teneral menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(RegisterClientActivity.this, "Revisa todos los campos e intentalo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    public void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(RegisterClientActivity.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterClientActivity.this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(String name, String email, String password){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name, email);
                    create(client);
                } else{
                    Toast.makeText(RegisterClientActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
    /*
    public void saveUser(String id, String email, String name){
        String selectedUser = mPreference.getString("user", "");
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        if (selectedUser.equals("driver")){
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(RegisterActivity.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
}