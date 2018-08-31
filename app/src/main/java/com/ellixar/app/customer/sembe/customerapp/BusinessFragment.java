package com.ellixar.app.customer.sembe.customerapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ellixar.app.customer.sembe.customerapp.adapter.AccountActivityBusinessesRecylerViewAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ellixar.app.customer.sembe.customerapp.model.Users;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends Fragment {

    @BindView(R.id.frg_users_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.frg_users_progressBar)
    ProgressBar progressBar;

    private List<Users> usersList;
    AccountActivityBusinessesRecylerViewAdapter userRecylerViewAdapter;
    private FirebaseFirestore mFirestore;


    public BusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.bind(this,view);

        mFirestore = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        userRecylerViewAdapter = new AccountActivityBusinessesRecylerViewAdapter(usersList,container.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(userRecylerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        usersList.clear();
        mFirestore.collection("Users")
                .addSnapshotListener( getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){

                           if (documentChange.getType() == DocumentChange.Type.ADDED){

                               String user_id = documentChange.getDocument().getId();
                               Users users = documentChange.getDocument().toObject(Users.class).withId(user_id);
                               usersList.add(users);

                               userRecylerViewAdapter.notifyDataSetChanged();
                           }
                        }
                    }
                });
    }

    @OnClick(R.id.profilebtn_add_business)
    public void userAddedBusiness(){
        progressBar.setVisibility(View.VISIBLE);
        sendToAddBusinessActivity();
    }

    private void sendToAddBusinessActivity() {
        Intent intent = new Intent(getContext(), AddBusinessActivity.class);
        startActivity(intent);
    }
}
