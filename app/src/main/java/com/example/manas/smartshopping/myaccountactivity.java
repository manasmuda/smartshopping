package com.example.manas.smartshopping;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class myaccountactivity extends AppCompatActivity {

    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _toggle;

    private Intent orderactivity = new Intent();
    private Intent searchactivity = new Intent();
    private Intent myordersactivity = new Intent();
    private Intent myaddressessactivity = new Intent();
    private Intent homeactivity = new Intent();
    private Intent wishlistactivity = new Intent();

    private LinearLayout repalinear;
    private LinearLayout acchlinear;
    private TextView nptext;
    private TextView oldptext;
    private TextView rnptext;
    private TextView nametext;
    private TextView emailidtext;
    private TextView phonenotext;
    private EditText npedit;
    private EditText oldpedit;
    private EditText rnpedit;
    private EditText nameedit;
    private EditText emailidedit;
    private EditText phonenoedit;
    private Button repabutton;
    private Button scbutton;
    private Button rpbutton;
    private TextView profilename;
    private TextView profileemail;
    private ImageView profileimage;
    private Button homebutton;
    private Button myordersbutton;
    private Button myaddressbutton;
    private Button wishlistbutton;
    private Button myaccountbutton;
    private Button logoutbutton;
    private Button orderbutton;

    private ArrayList<HashMap<String,Object>> userdatalistmap =new ArrayList<>();
    private ArrayList<HashMap<String,Object>> templistmap =new ArrayList<>();
    private ArrayList<HashMap<String,Object>> templistmap1 =new ArrayList<>();
    private HashMap<String,Object> tempmap = new HashMap<>();

    private int udindex=0;
    private int counter1=0;
    private int i14=0;
    private int i15=0;
    private int i16=0;
    private int anaf=0;

    private FirebaseAuth ssauth;
    private FirebaseAuth.AuthStateListener ssauthlistner;
    private AuthCredential sscredential;
    private FirebaseUser ssauthu;

    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    private DatabaseReference ud = _database.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("ac").equals("lo"))
                {
                    if (getIntent().getIntExtra("pp",0)==10) {
                        homeactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK,homeactivity);
                    }
                    else
                    {
                        if (getIntent().getIntExtra("pp",0)==11)
                        {
                            myaddressessactivity.putExtra("ac","lo");
                            setResult(RESULT_OK,myordersactivity);
                        }
                        else
                        {
                            if(getIntent().getIntExtra("pp",0)==12)
                            {
                                myordersactivity.putExtra("ac","lo");
                                setResult(RESULT_OK,myordersactivity);
                            }
                        }
                    }
                }
                myaccountactivity.this.finish();
            }
        }
        if(requestCode==11)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("ac").equals("lo"))
                {
                    if (getIntent().getIntExtra("pp",0)==10) {
                        homeactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK,homeactivity);
                    }
                    else
                    {
                        if (getIntent().getIntExtra("pp",0)==11)
                        {
                            myaddressessactivity.putExtra("ac","lo");
                            setResult(RESULT_OK,myordersactivity);
                        }
                        else
                        {
                            if(getIntent().getIntExtra("pp",0)==12)
                            {
                                myordersactivity.putExtra("ac","lo");
                                setResult(RESULT_OK,myordersactivity);
                            }
                        }
                    }
                }
                myaccountactivity.this.finish();
            }
        }
        if(requestCode==12)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("ac").equals("lo"))
                {
                    if (getIntent().getIntExtra("pp",0)==10) {
                        homeactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK,homeactivity);
                    }
                    else
                    {
                        if (getIntent().getIntExtra("pp",0)==11)
                        {
                            myaddressessactivity.putExtra("ac","lo");
                            setResult(RESULT_OK,myordersactivity);
                        }
                        else
                        {
                            if(getIntent().getIntExtra("pp",0)==12)
                            {
                                myordersactivity.putExtra("ac","lo");
                                setResult(RESULT_OK,myordersactivity);
                            }
                        }
                    }
                }
                myaccountactivity.this.finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(_drawer.isDrawerOpen(GravityCompat.START))
        {
            _drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(anaf==1)
            {
                anaf=0;
                acchlinear.setVisibility(View.VISIBLE);
                repalinear.setVisibility(View.GONE);
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (_toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccountactivity);
        Initialize();
        InitializeLogic();
    }

    public void Initialize(){

        _drawer= findViewById(R.id._drawer);
        _toggle=new ActionBarDrawerToggle(myaccountactivity.this,_drawer,R.string.open,R.string.close);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeactivity.setClass(myaccountactivity.this,homepage.class);
        myaddressessactivity.setClass(myaccountactivity.this,myaddressesactivity.class);
        myordersactivity.setClass(myaccountactivity.this,myordersactivity.class);

        repalinear=findViewById(R.id.repalinear);
        acchlinear=findViewById(R.id.acchlinear);
        emailidtext=findViewById(R.id.emailidtext);
        emailidedit=findViewById(R.id.emailidedit);
        nametext=findViewById(R.id.nametext);
        nameedit=findViewById(R.id.nameedit);
        phonenotext=findViewById(R.id.phonenotext);
        phonenoedit=findViewById(R.id.phonenoedit);
        oldptext=findViewById(R.id.oldptext);
        oldpedit=findViewById(R.id.oldpedit);
        nptext=findViewById(R.id.nptext);
        npedit=findViewById(R.id.npedit);
        rnptext=findViewById(R.id.rnptext);
        rnpedit=findViewById(R.id.rnpedit);
        repabutton=findViewById(R.id.repabutton);
        rpbutton=findViewById(R.id.rpbutton);
        scbutton=findViewById(R.id.scbutton);
        profilename=findViewById(R.id.profilename);
        profileemail=findViewById(R.id.profileemail);
        profileimage=findViewById(R.id.profileimage);
        homebutton=findViewById(R.id.homebutton);
        myordersbutton=findViewById(R.id.myordersbutton);
        myaddressbutton=findViewById(R.id.myaddressbutton);
        myaccountbutton=findViewById(R.id.myaccountbutton);
        logoutbutton=findViewById(R.id.logoutbutton);

        ssauthu=FirebaseAuth.getInstance().getCurrentUser();

        scbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempmap=new HashMap<>();
                tempmap.put("fullname",nameedit.getText().toString());
                tempmap.put("phoneno",phonenoedit.getText().toString());
                tempmap.put("email",userdatalistmap.get(udindex).get("email").toString());
                tempmap.put("uid",userdatalistmap.get(udindex).get("uid").toString());
                final DatabaseReference tdb=_database.getReference("userdata/"+String.valueOf(udindex+1)+"/cart");
                tdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                        templistmap= new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                templistmap.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        ud.child(String.valueOf(udindex+1)).updateChildren(tempmap);
                        profilename.setText(nameedit.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        myordersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myordersactivity.putExtra("pp",13);
                startActivityForResult(myordersactivity,12);
            }
        });

        myaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaddressessactivity.putExtra("pp",13);
                startActivityForResult(myaddressessactivity,11);

            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeactivity.putExtra("pp",13);
                startActivityForResult(homeactivity,10);
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("pp",0)==10) {
                    homeactivity.putExtra("ac", "lo");
                    setResult(RESULT_OK,homeactivity);
                }
                else
                {
                    if (getIntent().getIntExtra("pp",0)==11)
                    {
                        myaddressessactivity.putExtra("ac","lo");
                        setResult(RESULT_OK,myordersactivity);
                    }
                    else
                    {
                        if(getIntent().getIntExtra("pp",0)==12)
                        {
                            myordersactivity.putExtra("ac","lo");
                            setResult(RESULT_OK,myordersactivity);
                        }
                    }
                }
                myaccountactivity.this.finish();
            }
        });

        rpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repalinear.setVisibility(View.VISIBLE);
                acchlinear.setVisibility(View.GONE);
                anaf=1;
            }
        });

        repabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldpedit.getText().toString().length() > 0) {
                    sscredential = EmailAuthProvider.getCredential(ssauthu.getEmail(), oldpedit.getText().toString());
                    ssauthu.reauthenticate(sscredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (npedit.getText().toString().equals(rnpedit.getText().toString())) {
                                    if (npedit.getText().toString().length() >= 6) {
                                        ssauthu.updatePassword(npedit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(myaccountactivity.this, "password updated", Toast.LENGTH_LONG).show();
                                                    repalinear.setVisibility(View.GONE);
                                                    acchlinear.setVisibility(View.VISIBLE);
                                                    anaf = 0;
                                                } else {
                                                    Toast.makeText(myaccountactivity.this, "password not updated", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(myaccountactivity.this, "new password size must be more than 6", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(myaccountactivity.this, "passwords do not match", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(myaccountactivity.this, "Old password is not correct", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(myaccountactivity.this,"old password must not be empty",Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void InitializeLogic(){
        acchlinear.setVisibility(View.VISIBLE);
        repalinear.setVisibility(View.GONE);

        emailidedit.setFocusable(false);
        emailidedit.setFocusableInTouchMode(false);
        emailidedit.setClickable(false);

        udindex=getIntent().getIntExtra("ui",0);

        ud.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                userdatalistmap= new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                    };
                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        userdatalistmap.add(_map);
                    }
                } catch (Exception _e) {
                    _e.printStackTrace();
                }
                counter1=0;
                for(int i = 0; i< userdatalistmap.size(); i++)
                {
                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userdatalistmap.get(counter1).get("uid").toString())) {
                        udindex=counter1;
                    }
                    counter1++;
                }

                profilename.setText(userdatalistmap.get(udindex).get("fullname").toString());
                profileemail.setText(userdatalistmap.get(udindex).get("email").toString());

                emailidedit.setText(userdatalistmap.get(udindex).get("email").toString());
                nameedit.setText(userdatalistmap.get(udindex).get("fullname").toString());
                phonenoedit.setText(userdatalistmap.get(udindex).get("phoneno").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
