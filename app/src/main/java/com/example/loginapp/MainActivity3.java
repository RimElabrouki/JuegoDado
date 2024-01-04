package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.loginapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;
/**
 *
 * Esta clase contiene atributos y metodos del MainActivity3
 * @author Rim
 * @version 12
 * @since 02/04/2022
 *
 */
public class MainActivity3<loginViewModel> extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private Button registrar;
    private EditText primerpassword;
    private ImageView volver;
    AwesomeValidation awesomeValidation;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +            //
                    "(?=.*[0-9])"+           // debe contener al menos un digito
                    "(?=.*[a-z])"+           //debe contener al menos Una letra en Miniscula
                    ".{5,}" +                // al menos 5 caracteres
                    "$");                    // fin de cadena
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        primerpassword = findViewById(R.id.primerpassword);
        volver = findViewById(R.id.atras);
        validateEmail();
        validatePassword();
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity3.this, LoginActivity.class));

            }
        });
    }

    /**
     * Metodo validaEmail para valir el campo del email
     * @return true si has entregado un campo valido y false si no
     */
    private boolean validateEmail() {
        // Extract input from EditText
        String emailInput = email.getText().toString().trim();

        // si el campo del email esta vacio
        if (emailInput.isEmpty()) {
            email.setError("El campo no puede estar vacio");
            return false;
        }
        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("gmail o email y @ son campos obligatorios");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    /**
     * Metodo validatePassword para valir el campo de la contraseña
     * @return true si has entregado un campo valido y false si no
     */
    private boolean validatePassword() {
        String passwordInput = primerpassword.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            primerpassword.setError("Error no puede estar vacio");
            return false;
        }
        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            primerpassword.setError("Proporcina una contraseña que contenga  acentura(a-z) y Numeros  y sera > 5");
            return false;
        } else {
            primerpassword.setError(null);
            return true;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }


    /**
     * Metodo RegistrarUsuario para registrarte si no tienes cuenta
     * @param view de tipo View
     */
    public void RegistrarUsuario(View view){
        if (primerpassword.getText().toString().equals(primerpassword.getText().toString())){
            Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email.getText().toString(), primerpassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Usuario creado.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "No puedes Registrar intenta de nuevo", Toast.LENGTH_SHORT).show();
                            }
                            if(!validatePassword()){
                                email.setError("gmail o email y @ son campos obligatorios");
                            }else{

                            }
                        }
                    }); }else{
            Toast.makeText(this,"las contraseñas no conciden", Toast.LENGTH_SHORT).show();

        }}}



