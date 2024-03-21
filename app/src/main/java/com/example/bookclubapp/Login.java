package com.example.bookclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookclubapp.helpers.StringHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    Button btnEnter, btnLogin, btnRegister;
    TextInputEditText idInfo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idInfo = findViewById(R.id.idInfo);
        password = findViewById(R.id.password);

        btnEnter = findViewById(R.id.btnEnter);
        btnRegister = findViewById(R.id.btnRegister);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticateUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp(v);
            }
        });
    }

    public void goToSignUp(View view){
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
        finish();
    }

    public void authenticateUser(){
        if (!validateEmailOrLogin() || !validatePassword()){
            return;
        }

        // Создаём очередь запросов на сервер
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        String url = "http://192.168.43.3:9080/api/v1/user/login";

        Map<String, String> params = new HashMap<String, String>();
        params.put("emailOrLogin", idInfo.getText().toString());
        params.put("password", password.getText().toString());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String name = (String) response.get("name");
                    String email = (String) response.get("email");

                    Intent goToProfile = new Intent(Login.this, UserProfile.class);
                    // пробрасываем значения в следующее активити
                    goToProfile.putExtra("name", name);
                    goToProfile.putExtra("email", email);
                    startActivity(goToProfile);
                    finish();

                } catch (JSONException e){
                    Log.d("LOGINERR", Objects.requireNonNull(e.getMessage()));
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Log.d("LOGINERR", error.getMessage());
                Toast.makeText(Login.this, "Не удалось войти", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);

    }


    public boolean validateEmailOrLogin(){
        String uIdInfo =  String.valueOf(idInfo.getText());
        if (uIdInfo.isEmpty()){
            idInfo.setError("Поле не может быть пустым!");
            return false;
        } else {
            idInfo.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String uPassword =  String.valueOf(password.getText());
        if (uPassword.isEmpty()){
            password.setError("Поле не может быть пустым!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


}