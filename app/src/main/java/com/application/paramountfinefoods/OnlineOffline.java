package com.application.paramountfinefoods;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.application.paramountfinefoods.utils.DominoBoldTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineOffline extends Fragment {

    private Switch sw1;
    Long mode;
    DominoBoldTextView tv_setStatus;
    Context mcontext;

    FirebaseDatabase database ;
    DatabaseReference mDatabaseRef;

    public OnlineOffline() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference();

//        myref.child("myDb").child("awais@gmailcom")
//        mDatabaseRef.child("TABLE_NAME").child("orderStatus").setValue(2);

        View view=inflater.inflate(R.layout.fragment_online_offline, container, false);
        sw1 = (Switch) view.findViewById(R.id.switch1);
        tv_setStatus = (DominoBoldTextView) view.findViewById(R.id.getBtn);
        mcontext = this.getActivity();

        tv_setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1, str2;
                if (sw1.isChecked()){
                    str1 = sw1.getTextOn().toString();
                    mode = 1L;
                }

                else{
                    str1 = sw1.getTextOff().toString();
                    mode = 0L;
                }

                updateData(mode);

//                if (sw2.isChecked())
//                    str2 = sw2.getTextOn().toString();
//                else
//                    str2 = sw2.getTextOff().toString();

                Toast.makeText(mcontext, "Switch1 -  " + str1,Toast.LENGTH_SHORT).show();
            }
        });

//        btnGet = (Button)findViewById(R.id.getBtn);
        // Inflate the layout for this fragment
        return view;
    }

    private void updateData(final Long newmode) {
//        database = FirebaseDatabase.getInstance();
//        myref = database.getReference();
//        User user = new
        mDatabaseRef.child("Location").child("two").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("mode").setValue(newmode);
//                dialog.dismiss();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }

}
