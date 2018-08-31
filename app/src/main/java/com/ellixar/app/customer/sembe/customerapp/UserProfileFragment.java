package com.ellixar.app.customer.sembe.customerapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    @BindView(R.id.frg_profile_image)
    CircleImageView profileFrgPic;
    @BindView(R.id.frg_profile_name)
    TextView profileFrgName;
    @BindView(R.id.frg_profile_progressBar)
    ProgressBar progressBar;

    private View mRootView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public static final String USERS_COLLECTION = "Users";

    private SQLiteHandler db;
    private SessionManager session;

    public static final String MyOrderListPREFERENCES = "MyOrderListPrefs";
    SharedPreferences sharedpreferences;

    public UserProfileFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this,mRootView);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        loadFragmentData(currentUser);


        // SqLite database handler
        db = new SQLiteHandler(getContext());

        // session manager
        session = new SessionManager(getContext());

        return mRootView;
    }

    private void loadFragmentData(FirebaseUser cUser){
        mFirestore.collection(USERS_COLLECTION).document(cUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("name");
                        String image_url = documentSnapshot.getString("imageurl");

                        RequestOptions placeholderRequestOptions = new RequestOptions();
                        placeholderRequestOptions.placeholder(R.drawable.profile_placeholder_large);

                        profileFrgName.setText(name);
                        Glide.with(mRootView.getContext())
                                .setDefaultRequestOptions(placeholderRequestOptions)
                                .load(image_url)
                                .into(profileFrgPic);


                    }
                });

    }

    @OnClick(R.id.profilebtn_logout)
    public void userLogout(){
        progressBar.setVisibility(View.VISIBLE);
//        sendToMainActivity();
        logoutUser();
    }

    private void sendToMainActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    @SuppressLint("StaticFieldLeak")
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        try {
            new AsyncTask<Void, Void, Void>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected Void doInBackground(Void... params) {
                    //get shared preferences
                    sharedpreferences = Objects.requireNonNull(getContext()).getSharedPreferences(MyOrderListPREFERENCES, Context.MODE_PRIVATE);

                    //Clear shared preferences
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            Toast.makeText(getContext(),
                    "Clear cart failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        // Launching the login activity
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
