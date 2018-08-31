package com.ellixar.app.customer.sembe.customerapp;

/**
 * Created by munyingiian on 17/08/2018
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.reg_phone)
    TextView regPhone;
    @BindView(R.id.reg_name)
    TextView regName;
    @BindView(R.id.reg_password)
    TextView regPassword;
    @BindView(R.id.reg_repeat_password)
    TextView regRepeatPassword;
    @BindView(R.id.button_create_new_account)
    Button createNewAccountBtn;
    @BindView(R.id.button_back_to_login)
    Button backTologin;
    @BindView(R.id.profile_imageBtn)
    CircleImageView profileImgbtn;
    @BindView(R.id.register_progressBar)
    ProgressBar progressBar;

    public static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;


    private ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;


    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //layout init
        ButterKnife.bind(this);

        imageUri = null;
        storageReference = FirebaseStorage.getInstance().getReference().child("profileimages");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        // Progress dialog
        progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme_Dark_Dialog);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.button_back_to_login)
    public void sendBackTologin() {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.profile_imageBtn)
    public void picImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                imageUri = data.getData();
                Glide.with(this).load(imageUri).into(profileImgbtn);
            }
        }
    }

    @OnClick(R.id.button_create_new_account)
    public void registerNewUser() {
        //Sign up
        signup();


//        Intent intent = new Intent(this,LoginActivity.class);
//        finish();
//        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(RegisterActivity.this);
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


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
        } else {


            createNewAccountBtn.setEnabled(true);

            /**final ProgressDialog progressDialog = new ProgressDialog(registerActivity.this,
             R.style.AppTheme_Dark_Dialog);
             progressDialog.setIndeterminate(true);
             progressDialog.setMessage("Creating Account...");
             progressDialog.show();*/


            regName = (EditText) findViewById(R.id.reg_name);
            regPhone = (EditText) findViewById(R.id.reg_phone);
            regPassword = (EditText) findViewById(R.id.reg_password);
            regRepeatPassword = (EditText) findViewById(R.id.reg_repeat_password);

            //register_Button_inRegister = (Button) findViewById(R.id.btn_register_inRegister);
            //login_Button_inRegister = (Button) findViewById(R.id.btn_login_inRegister);


            String user_name = regName.getText().toString().trim();
            String user_phone_number = regPhone.getText().toString().trim();
            String user_password = regPassword.getText().toString().trim();
            String user_repeat_password = regRepeatPassword.getText().toString().trim();
            String user_type = String.valueOf(1);

            //sets is shop owner to one which indicates the person is a shop owner
            String isShopOwner = "1";

            // TODO: Implement your own signup logic here.

            registerUser(user_name, user_phone_number, user_password);

            /**new Handler().postDelayed(
             new Runnable() {
             public void run() {
             // On complete call either onSignupSuccess or onSignupFailed
             // depending on success
             onSignupSuccess();
             // onSignupFailed();
             progressDialog.dismiss();
             }
             }, 3000);*/
        }
    }

    public boolean validate() {
        boolean valid = true;

        String user_name = regName.getText().toString().trim();
        String user_phone_number = regPhone.getText().toString().trim();
        String user_password = regPassword.getText().toString().trim();
        String user_repeat_password = regRepeatPassword.getText().toString().trim();

        if (user_name.isEmpty() || user_name.length() < 3) {
            regName.setError("at least 3 characters");
            valid = false;
        } else {
            regName.setError(null);
        }
        if (user_phone_number.isEmpty() || user_phone_number.length() < 10) {
            regPhone.setError("at least 10 numerics");
            valid = false;
        } else {
            regPhone.setError(null);
        }

        if (user_password.isEmpty() || user_password.length() < 6) {
            regPassword.setError("at least 5 characters");
            valid = false;
        } else {
            regPassword.setError(null);
        }

        if (!user_password.equals(user_repeat_password)) {
            regRepeatPassword.setError("Password Do not match");
            regPassword.setError("Password Do not match");
            valid = false;
        } else {

        }

        if (user_repeat_password.isEmpty() || user_repeat_password.length() < 6 || user_repeat_password.length() > 10 || !(user_repeat_password.equals(user_password))) {
            regRepeatPassword.setError("Password should be between 6 and 10 characters.");
            valid = false;
        } else {
            regRepeatPassword.setError(null);
        }

        return valid;
    }

    private void registerUser(final String user_name, final String user_phone_number, String user_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setMessage("Creating Account...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                hideDialog();
                            }
                        }, 3000);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String token = jObj.getString("token");
                    if (!error) {
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

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        onSignupSuccess();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        JSONObject errorMsg = jObj.getJSONObject("errors");
                        String message = errorMsg.getString("phone");
                        Toast.makeText(getApplicationContext(),
                                "Json POST error: " + message, Toast.LENGTH_LONG).show();
                        onSignupFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();// JSON error
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    onSignupFailed();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    try {

                        JSONObject jsonObj = new JSONObject(body);

                        String message = jsonObj.getString("message");

                        Log.e(TAG, "JSON : " + jsonObj);

                        JSONObject errorsObj = jsonObj.getJSONObject("errors");



//                        String name = errorsObj.getString("name");
//                        String phone = errorsObj.getString("phone");
//                        String password = errorsObj.getString("password");

                        Log.e(TAG, "Registration Error msg: " + message);

                        Toast.makeText(getApplicationContext(),
                                "Error : " + message, Toast.LENGTH_LONG).show();

                        if (errorsObj.has("name")) {
                            JSONArray nameArray = errorsObj.getJSONArray("name");
                            final String name = nameArray.getString(0);
//                            Toast.makeText(getApplicationContext(),
//                                    "Error : " + name, Toast.LENGTH_LONG).show();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this, R.style.AlertDialogTheme);
                                    dlgAlert.setMessage(name);
                                    dlgAlert.setPositiveButton("OK", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();

                                }
                            });
                        }
                        if (errorsObj.has("phone")) {
                            JSONArray phoneArray = errorsObj.getJSONArray("phone");
                            final String phone = phoneArray.getString(0);
//                            Toast.makeText(getApplicationContext(),
//                                    "Error : " + phone, Toast.LENGTH_LONG).show();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                                    dlgAlert.setMessage(phone);
                                    dlgAlert.setPositiveButton("OK", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();
                                }
                            });
                        }
                        if (errorsObj.has("password")) {
                            JSONArray passwordArray = errorsObj.getJSONArray("password");
                            final String password = passwordArray.getString(0);
//                            Toast.makeText(getApplicationContext(),
//                                    "Error : " + password, Toast.LENGTH_LONG).show();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegisterActivity.this);
                                    dlgAlert.setMessage(password);
                                    dlgAlert.setPositiveButton("OK", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (UnsupportedEncodingException e) {
                    // exception
                }


//                Toast.makeText(getApplicationContext(),
//                        "Volley registration error: " + error.getMessage(), Toast.LENGTH_LONG).show();
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
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", user_name);
                params.put("phone", user_phone_number);
                params.put("password", user_phone_number);
                params.put("user", String.valueOf(true));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        createNewAccountBtn.setEnabled(true);
    }


    public void onSignupSuccess() {
        createNewAccountBtn.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
