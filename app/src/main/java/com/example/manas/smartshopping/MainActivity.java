package com.example.manas.smartshopping;


import android.app.*;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.webkit.*;
import android.text.*;
import android.util.*;
import android.animation.*;
import android.view.animation.*;
import android.text.TextUtils;
import java.util.*;
import java.text.*;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.ProgressBar;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final long time = 600000;
    private Timer _timer = new Timer();
    private Timer _timer2 = new Timer();
    private TimerTask timer;
    private TimerTask timer2;
    private Intent homepageintent=new Intent();
    private HashMap<String,Object> tempmap = new HashMap<>();
    private FirebaseDatabase _firebase=FirebaseDatabase.getInstance();

    private ArrayList<HashMap<String,Object>> udlistmap = new ArrayList<>();

    private LinearLayout mainscreenlinear;
    private LinearLayout splashscreenlinear1;
    private LinearLayout loginsignuplinear;
    private LinearLayout loginlinear;
    private LinearLayout signuplinear;
    private LinearLayout loginsignupimagelinear;
    private LinearLayout loginsignuploginlinear;
    private LinearLayout loginsignupsignuplinear;
    private LinearLayout signupimagelinear;
    private LinearLayout fullnamelinear;
    private LinearLayout emaillinear;
    private LinearLayout passwordlinear;
    private LinearLayout repasswordlinear;
    private LinearLayout phonelinear;
    private LinearLayout signupbuttonlinear;
    private LinearLayout loginimagelinear;
    private LinearLayout emailidlinear;
    private LinearLayout passwordtypelinear;
    private LinearLayout loginbuttonlinear;
    private ImageView splashimage;
    private ImageView loginsignuppagelogo;
    private ImageView signupimage;
    private ImageView loginimage;
    private TextView fullnametextview;
    private TextView emailtextview;
    private TextView passwordtextview;
    private TextView repasswordtextview;
    private TextView phonetextview;
    private TextView emailidtextview;
    private TextView passwordtypetextview;
    private EditText fullnameedit;
    private EditText emailedit;
    private EditText passwordedit;
    private EditText repasswordedit;
    private EditText phoneedit;
    private EditText emailidedit;
    private EditText passwordtypeedit;
    private EditText otpedit;
    private Button loginbutton;
    private Button signupbutton;
    private Button signupenterbutton;
    private Button loginenterbutton;
    private Button verifybutton;
    private Button resendbutton;

    private ProgressBar splashprogressbar;
    private FirebaseAuth ssauth;
    private FirebaseAuth.AuthStateListener ssauthlistner;

    private String Authid="";

    private DatabaseReference ud=_firebase.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("key1").equals("logout")) {
                    FirebaseAuth.getInstance().signOut();
                    loginsignuplinear.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                MainActivity.this.finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ssauth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeLogic();

    }

    private void initialize() {


        splashscreenlinear1 = findViewById(R.id.splashscreenlinear1);
        loginsignuplinear = findViewById(R.id.loginsignuplinear);
        loginlinear = findViewById(R.id.loginlinear);
        loginbutton = findViewById(R.id.loginbutton);
        signupbutton = findViewById(R.id.signupbutton);
        signuplinear = findViewById(R.id.signuplinear);
        fullnameedit = findViewById(R.id.fullnameedit);
        emailedit = findViewById(R.id.emailedit);
        loginenterbutton = findViewById(R.id.loginenterbutton);
        passwordedit = findViewById(R.id.passwordedit);
        repasswordedit = findViewById(R.id.repasswordedit);
        phoneedit = findViewById(R.id.phoneedit);
        emailidedit = findViewById(R.id.emailidedit);
        passwordtypeedit = findViewById(R.id.passwordtypeedit);
        signupenterbutton = findViewById(R.id.signupenterbutton);
        splashprogressbar=findViewById(R.id.splashprogressbar);
        splashimage= findViewById(R.id.splashimage);

        ssauth=FirebaseAuth.getInstance();



        homepageintent.setClass(MainActivity.this,homepage.class);


        _ud_child_listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                ud.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        udlistmap=new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                udlistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                ud.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        udlistmap=new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                udlistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError _param1) {
                final int _errorCode=_param1.getCode();
                final String _errorMessage=_param1.getMessage();

            }
        };
        ud.addChildEventListener(_ud_child_listener);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashscreenlinear1.setVisibility(View.GONE);
                loginsignuplinear.setVisibility(View.GONE);
                loginlinear.setVisibility(View.VISIBLE);
                signuplinear.setVisibility(View.GONE);
            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashscreenlinear1.setVisibility(View.GONE);
                loginsignuplinear.setVisibility(View.GONE);
                loginlinear.setVisibility(View.GONE);
                signuplinear.setVisibility(View.VISIBLE);
            }
        });
        loginenterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordtypeedit.getText().toString().length()>0 && emailidedit.getText().toString().length()>0) {
                    ssauth.signInWithEmailAndPassword(emailidedit.getText().toString(), passwordtypeedit.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final boolean _success = task.isSuccessful();
                            final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
                            if (_success) {
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                                Authid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                startActivityForResult(homepageintent, 3);
                            } else {
                                Toast.makeText(MainActivity.this, _errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        signupenterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((passwordedit.getText().toString().equals(repasswordedit.getText().toString())) && (fullnameedit.getText().toString()!=null) && (emailedit.getText().toString()!=null)) {

                    ssauth.createUserWithEmailAndPassword(emailedit.getText().toString(),passwordedit.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final boolean _success=task.isSuccessful();
                            final String _errorMessage=task.getException()!=null?task.getException().getMessage():"";
                            if(_success){
                                FirebaseUser user=ssauth.getCurrentUser();
                                tempmap=new HashMap<>();
                                tempmap.put("fullname",fullnameedit.getText().toString());
                                tempmap.put("phoneno",phoneedit.getText().toString());
                                tempmap.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                tempmap.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                                ud.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        udlistmap=new ArrayList<>();
                                        try {
                                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                                HashMap<String, Object> _map = _data.getValue(_ind);
                                                udlistmap.add(_map);
                                            }
                                        }
                                        catch (Exception _e) {
                                            _e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                ud.child(String.valueOf(udlistmap.size() +1)).updateChildren(tempmap);
                                startActivityForResult(homepageintent,3);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,_errorMessage,Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });



    }
    private void initializeLogic(){

        loginlinear.setVisibility(View.GONE);
        signuplinear.setVisibility(View.GONE);
        loginsignuplinear.setVisibility(View.GONE);
       //Glide.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/smartshopping-c310d.appspot.com/o/Screenshot%20(17).png?alt=media&token=c1b1a776-6e60-4006-b72c-375841b8b474").fitCenter().into(splashimage);
       //centerCrop
        //fitCentre
        timer2=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        splashscreenlinear1.setVisibility(View.GONE);

                                    if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
                                        startActivityForResult(homepageintent, 3);

                                    } else {
                                        loginlinear.setVisibility(View.GONE);
                                        signuplinear.setVisibility(View.GONE);
                                        loginsignuplinear.setVisibility(View.VISIBLE);
                                    }

                    }
                });
            }
        };
        _timer2.schedule(timer2, 3000);
        timer=new TimerTask(){
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        splashprogressbar.incrementProgressBy(5);
                    }
                });
            }
        };
        _timer.scheduleAtFixedRate(timer, 0, 600);

    }

}
