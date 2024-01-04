package com.example.loginapp.ui.login;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.loginapp.MainActivity2;
import com.example.loginapp.MainActivity3;
import com.example.loginapp.R;
import com.example.loginapp.ui.login.LoginViewModel;
import com.example.loginapp.ui.login.LoginViewModelFactory;
import com.example.loginapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * Esta clase contiene atributos y metodos del LoginActivity
 * @author rim (DAM206)
 * @version 12
 * @since 02/04/2022
 * @
 */
public class LoginActivity extends AppCompatActivity {
    // la librareria esta importada en el build.gradle
    AwesomeValidation awesomeValidation;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private Button jugar;
    private EditText usuario;
    private EditText passwords;
    private TextView registrar;
    private FirebaseAuth mAuth;

    /**
     * Metodo onCreate Utiliza para iniciar la actividad
     * super se usa para llamar al constructor de la clase padre
     * setContentView se usa para configurar el xml del LoginActivity
     * @param savedInstanceState de tipo Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.username);
        passwords = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        /**
         añadimos las validaciones del email y de la contraseña **/
        awesomeValidation.addValidation(this,R.id.username, Patterns.EMAIL_ADDRESS,R.string.invalid_username );
        awesomeValidation.addValidation(this,R.id.password,".{5,}",R.string.invalid_password);

        registrar = findViewById(R.id.textView);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity3.class));
            }
        });
        jugar = findViewById(R.id.botonMainJugar);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    String email = usuario.getText().toString();
                    String contraseña = passwords.getText().toString();
                    mAuth.signInWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mijuego();
                                Toast.makeText(getApplicationContext(), "Bienvenido.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication faild", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
               }
            }
        });

    }// termina el metodo onCreate

    /**
     *metodo onStart indica que el activite esta a punto de ser mostrada para al usuario
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //probar si el usuario se registrarte in not null y actualiza Ui accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    public void mijuego(){
        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("email",usuario.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);


    }

    /**
     *Metodo inciarJuego que muestra mensajes de errores para el usuario
     * @param view de tipo View
     */
    public void iniciarJuego(View view) {

       binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.botonMainJugar;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));

                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                    passwordEditText.setError("Proporciona Una contraseña que contenga caracteres limitados sin acentuar(a-z),Números (0-9)");

                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
               // finish();
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    /**
     *
     * @param model
     */
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @param errorString
     */

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    }
