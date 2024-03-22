package com.example.bookclubapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookclubapp.helpers.StringHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputEditText name, login, email, password, passwordCheck;
    Button btnJoin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Захват данных из полей ввода
        name = findViewById(R.id.name);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordCheck);

        // Захват кнопки
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignUp:","Button Join successfully clicked");
                processInputFields();
            }
        });
    }

    // Реакция на кнопку "войти" - переход в активити Login
    public void goToLogin(View view){
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void processInputFields(){
        if (!validateName() || !validateLogin() || !validateEmail() || !validatePasswordAndCheck()){
            return;
        }

        // Создаём очередь запросов на сервер
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        String url = "http://192.168.43.3:9080/api/v1/user/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // очищаем поля после пользователя
                name.setText(null);
                login.setText(null);
                email.setText(null);
                password.setText(null);
                passwordCheck.setText(null);
                Toast.makeText(SignUp.this, "Вы успешно зарегистированы!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(SignUp.this, "Вы не зарегистированы!", Toast.LENGTH_LONG).show();
                Log.d("CHECKERROR", Objects.requireNonNull(volleyError.getMessage()));
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // !!!!!!!
                // Параметры должны называться как на сервере!!!!
                // !!!!!!!!

                params.put("name", Objects.requireNonNull(name.getText()).toString());
                params.put("login", Objects.requireNonNull(login.getText()).toString());
                params.put("email", Objects.requireNonNull(email.getText()).toString());
                params.put("password", Objects.requireNonNull(password.getText()).toString());
                params.put("is_admin", "0");

                String par = name.getText().toString() + " " + login.getText().toString() + " " + email.getText().toString() + " " + password.getText().toString();

                Log.d("PARAMS", par);

                return params;
            }
        };

        queue.add(stringRequest);

    }

    public boolean validateName(){
        String uName =  String.valueOf(name.getText());
        if (uName.isEmpty()){
            name.setError("Поле не может быть пустым!");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    public boolean validateLogin(){
        String uLogin =  String.valueOf(login.getText());
        if (uLogin.isEmpty()){
            login.setError("Поле не может быть пустым!");
            return false;
        } else {
            login.setError(null);
            return true;
        }
    }

    public boolean validateEmail(){
        String uEmail =  String.valueOf(email.getText());
        if (uEmail.isEmpty()){
            email.setError("Поле не может быть пустым!");
            return false;
        } else if(!StringHelper.EmailValidationOnPattern(uEmail)){
            email.setError("Почта введена некорректно!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public boolean validatePasswordAndCheck(){
        String uPassword =  String.valueOf(password.getText());
        String uPasswordCheck =  String.valueOf(passwordCheck.getText());
        if (uPassword.isEmpty()){
            password.setError("Поле не может быть пустым!");
            return false;
        } else if(uPasswordCheck.isEmpty()){
            passwordCheck.setError("Поле не может быть пустым!");
            return false;
        } else if (!uPassword.equals(uPasswordCheck)){
            password.setError("Пароли не совпадают!");
            return false;
        } else {
            password.setError(null);
            passwordCheck.setError(null);
            return true;
        }
    }






}