package com.ellixar.app.customer.sembe.customerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by munyingiian on 17/08/2018
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.button_login)
    Button loginBtn;
    @BindView(R.id.button_register)
    Button registerBtn;
    @BindView(R.id.text_password)
    TextView tv_password;
    @BindView(R.id.login_phone)
    TextView tv_phone;
    @BindView(R.id.login_progressbar)
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirebaseFirestore;

    private ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNIN = 0;
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        // Progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @OnClick(R.id.button_register)
    public void sendToRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.button_login)
    public void loginTheUser(){

        login();

//        Intent intent = new Intent(this,MainActivity.class);
//        finish();
//        startActivity(intent);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginBtn.setEnabled(true);

        //ProgressDialog
        /**final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
         R.style.AppTheme_Dark_Dialog);
         progressDialog.setIndeterminate(true);
         progressDialog.setMessage("Authenticating...");
         progressDialog.show();*/

        String phone = tv_phone.getText().toString();
        String password = tv_password.getText().toString();

        // TODO: Implement your own authentication logic here.

        checkLogin(phone, password);


        /**new android.os.Handler().postDelayed(
         new Runnable() {
         public void run() {
         // On complete call either onLoginSuccess or onLoginFailed
         onLoginSuccess();
         // onLoginFailed();
         progressDialog.dismiss();
         }
         }, 3000);*/
    }

    @Override
    public void onBackPressed() {
        // Disable going back to any other Activity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        String phone = tv_phone.getText().toString();
        String password = tv_password.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            tv_phone.setError("Enter a valid phone address");
            valid = false;
        } else {
            tv_phone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            tv_password.setError("Between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            tv_password.setError(null);
        }

        return valid;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginBtn.setEnabled(true);
    }

    public void onLoginSuccess() {
        //Create AimPOs/Images Directory
        //createNewDirectory("AimPOS Images");

        //**control access**
        // Fetching product id details from sqlite


        loginBtn.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void checkLogin(final String phone, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.setMessage("Authenticating...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                hideDialog();
                            }
                        }, 3000);

                try {
                    // Now store the user in SQLite
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String token = jObj.getString("token");
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        String user_id = user.getString("id");
                        String user_phone = user.getString("phone");
                        String user_avatar = user.getString("avatar");
                        String user_user = user.getString("user");
                        String user_driver = user.getString("driver");
                        String user_marketer = user.getString("marketer");
                        String user_admin = user.getString("admin");
                        String user_validity_status = user.getString("validated");
                        String user_deleted_at = user.getString("deleted_at");
                        String user_created_at = user.getString("created_at");
                        String user_updated_at = user.getString("updated_at");

                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at, is_shop_owner);

                        Toast.makeText(getApplicationContext(), "User successfully Login!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        onLoginSuccess();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        JSONObject errorMsg = jObj.getJSONObject("errors");
                        String message = errorMsg.getString("phone");
                        Toast.makeText(getApplicationContext(),
                                "Json POST error: " + message, Toast.LENGTH_LONG).show();
                        onLoginFailed();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    onLoginFailed();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);

                Toast.makeText(getApplicationContext(),
                        "Error code : " + statusCode, Toast.LENGTH_LONG).show();

                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    try {

                        JSONObject jsonObj = new JSONObject(body);

//                        String message = jsonObj.getString("message");

                        Log.e(TAG, "JSON : " + jsonObj);

                        String errorsObj = jsonObj.getString("error");



//                        String name = errorsObj.getString("name");
//                        String phone = errorsObj.getString("phone");
//                        String password = errorsObj.getString("password");

//                        Log.e(TAG, "Login Error msg: " + message);

                        Toast.makeText(getApplicationContext(),
                                "Error : " + errorsObj, Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (UnsupportedEncodingException e) {
                    // exception
                    Toast.makeText(getApplicationContext(),
                            "Json error2: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                hideDialog();
                            }
                        }, 3000);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
