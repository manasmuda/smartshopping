package com.example.manas.smartshopping;

import android.app.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.*;
import java.text.*;
import android.os.Message;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.RadioButton;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.app.Dialog;
import android.app.AlertDialog;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.DialogInterface;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.storage.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;

public class myaddressesactivity extends AppCompatActivity {
    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _toggle;

    private Intent homepageintent=new Intent();
    private Intent orderactivity = new Intent();
    private Intent searchactivity = new Intent();
    private Intent myordersactivity = new Intent();
    private Intent homeactivity = new Intent();
    private Intent myaccountactivity = new Intent();
    private Intent wishlistactivity = new Intent();

    private LinearLayout nalinear;
    private LinearLayout addressclinear;
    private ScrollView dalinear;
    private Button addabutton;
    private Button addaddressbutton;
    private Button editabutton;
    private EditText nameedit;
    private EditText hnoedit;
    private EditText apartmentno;
    private EditText lmedit;
    private EditText areaedit;
    private EditText cityedit;
    private EditText pnoedit;
    private EditText pinedit;
    private ListView addresslistview;
    private TextView profilename;
    private TextView profileemail;
    private Button homebutton;
    private Button myordersbutton;
    private Button myaddressbutton;
    private Button wishlistbutton;
    private Button myaccountbutton;
    private Button logoutbutton;
    private Button orderbutton;


    private String pname="";
    private String pemail="";
    private int counter1=0;
    private int udindex=0;
    private int anaf=0;
    private int eaf=0;
    private int updateflag=0;
    private int dedef=0;

    private ArrayList<HashMap<String,Object>> userdatalistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> addresslistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> templistmap=new ArrayList<>();
    private HashMap<String,Object> tempmap=new HashMap<>();

    private FirebaseDatabase _database = FirebaseDatabase.getInstance();

    private DatabaseReference ud=_database.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    @Override
    public void onBackPressed() {
        if(_drawer.isDrawerOpen(GravityCompat.START))
        {
            _drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(anaf==1)
            {
                nalinear.setVisibility(View.GONE);
                dalinear.setVisibility(View.VISIBLE);
                addaddressbutton.setVisibility(View.VISIBLE);
                anaf=0;
                nameedit.setText("");
                hnoedit.setText("");
                apartmentno.setText("");
                lmedit.setText("");
                areaedit.setText("");
                cityedit.setText("");
                pinedit.setText("");
                pnoedit.setText("");
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(_toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            if(data.getStringExtra("ac").equals("lo")) {
                if (getIntent().getIntExtra("pp", 0) == 10) {
                    homeactivity.putExtra("ac", "lo");
                    setResult(RESULT_OK, homeactivity);
                } else {
                    if (getIntent().getIntExtra("pp", 0) == 13) {
                        myaccountactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK, myaccountactivity);
                    } else {
                        if (getIntent().getIntExtra("pp", 0) == 12) {
                            myordersactivity.putExtra("ac", "lo");
                            setResult(RESULT_OK, myordersactivity);
                        }
                    }
                }
                myaddressesactivity.this.finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddressesactivity);
        Initialize();
        InitializeLogic();
    }

    public void Initialize(){
        _drawer= findViewById(R.id._drawer);
        _toggle=new ActionBarDrawerToggle(myaddressesactivity.this,_drawer,R.string.open,R.string.close);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myaccountactivity.setClass(myaddressesactivity.this,myaccountactivity.class);
        homeactivity.setClass(myaddressesactivity.this,homepage.class);
        myordersactivity.setClass(myaddressesactivity.this,myordersactivity.class);

        anaf=0;

        profilename=findViewById(R.id.profilename);
        profileemail=findViewById(R.id.profileemail);
        addresslistview=findViewById(R.id.addresslistview);
        addressclinear=findViewById(R.id.addressclinear);
        addabutton=findViewById(R.id.addabutton);
        addaddressbutton=findViewById(R.id.addaddressbutton);
        nameedit=findViewById(R.id.nameedit);
        hnoedit=findViewById(R.id.hnoedit);
        apartmentno=findViewById(R.id.apartmentno);
        lmedit=findViewById(R.id.lmedit);
        areaedit=findViewById(R.id.areaedit);
        cityedit=findViewById(R.id.cityedit);
        pinedit=findViewById(R.id.pinedit);
        pnoedit=findViewById(R.id.pnoedit);
        nalinear=findViewById(R.id.nalinear);
        dalinear=findViewById(R.id.dalinear);
        editabutton=findViewById(R.id.editabutton);
        homebutton=findViewById(R.id.homebutton);
        myordersbutton=findViewById(R.id.myordersbutton);
        myaddressbutton=findViewById(R.id.myaddressbutton);
        myaccountbutton=findViewById(R.id.myaccountbutton);
        logoutbutton=findViewById(R.id.logoutbutton);

        editabutton.setVisibility(View.GONE);

        _ud_child_listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);

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

        addaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anaf=1;
                dalinear.setVisibility(View.GONE);
                nalinear.setVisibility(View.VISIBLE);
                addaddressbutton.setVisibility(View.GONE);
                addabutton.setVisibility(View.VISIBLE);
                editabutton.setVisibility(View.GONE);
                nameedit.setText("");
                hnoedit.setText("");
                apartmentno.setText("");
                lmedit.setText("");
                areaedit.setText("");
                cityedit.setText("");
                pinedit.setText("");
                pnoedit.setText("");
            }
        });

        myordersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myordersactivity.putExtra("pp",11);
                startActivityForResult(myordersactivity,12);
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeactivity.putExtra("pp",11);
                startActivityForResult(homeactivity,10);

            }
        });

        myaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaccountactivity.putExtra("pp",11);
                startActivityForResult(myaccountactivity,13);
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
                    if (getIntent().getIntExtra("pp",0)==13)
                    {
                        myaccountactivity.putExtra("ac","lo");
                        setResult(RESULT_OK,myaccountactivity);
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
                myaddressesactivity.this.finish();
            }
        });
    }
    public void InitializeLogic(){
        nalinear.setVisibility(View.GONE);
        ud.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot _dataSnapshot) {
                profilename.setText(String.valueOf(2));
                userdatalistmap = new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        userdatalistmap.add(_map);
                    }
                }
                catch (Exception _e) {
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

                pname=userdatalistmap.get(udindex).get("fullname").toString();
                profilename.setText(pname);
                pemail=userdatalistmap.get(udindex).get("email").toString();
                profileemail.setText(pemail);

                final DatabaseReference al=_database.getReference("userdata/"+String.valueOf(udindex+1)+"/addresslist");
                ChildEventListener _al_child_listener;

                _al_child_listener=new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        final String _childKey = _param1.getKey();
                        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                        al.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                addresslistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        addresslistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
                                al.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot _dataSnapshot) {
                                        addresslistmap = new ArrayList<>();
                                        try {
                                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                                HashMap<String, Object> _map = _data.getValue(_ind);
                                                addresslistmap.add(_map);
                                            }
                                        }
                                        catch (Exception _e) {
                                            _e.printStackTrace();
                                        }
                                        if(addresslistmap.size() ==0)
                                        {
                                            dalinear.setVisibility(View.GONE);
                                            nalinear.setVisibility(View.VISIBLE);
                                            addaddressbutton.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            dalinear.setVisibility(View.VISIBLE);
                                            nalinear.setVisibility(View.GONE);
                                            addaddressbutton.setVisibility(View.VISIBLE);
                                            addresslistview.setAdapter(new myaddressesactivity.addresslistadapter(addresslistmap));
                                            setListViewHeightBasedOnItems(addresslistview);
                                        }

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError _databaseError) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(DatabaseError _databaseError) {
                            }
                        });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        final String _childKey = _param1.getKey();
                        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                        al.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                addresslistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        addresslistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
                                al.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot _dataSnapshot) {
                                        addresslistmap = new ArrayList<>();
                                        try {
                                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                                HashMap<String, Object> _map = _data.getValue(_ind);
                                                addresslistmap.add(_map);
                                            }
                                        }
                                        catch (Exception _e) {
                                            _e.printStackTrace();
                                        }
                                        if(addresslistmap.size() ==0)
                                        {
                                            dalinear.setVisibility(View.GONE);
                                            nalinear.setVisibility(View.VISIBLE);
                                            addaddressbutton.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            dalinear.setVisibility(View.VISIBLE);
                                            nalinear.setVisibility(View.GONE);
                                            addaddressbutton.setVisibility(View.VISIBLE);
                                            addresslistview.setAdapter(new myaddressesactivity.addresslistadapter(addresslistmap));
                                            setListViewHeightBasedOnItems(addresslistview);
                                        }

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError _databaseError) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(DatabaseError _databaseError) {
                            }
                        });
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot _param1) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        final String _childKey = _param1.getKey();
                        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                        al.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                addresslistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        addresslistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
                                al.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot _dataSnapshot) {
                                        addresslistmap = new ArrayList<>();
                                        try {
                                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                                HashMap<String, Object> _map = _data.getValue(_ind);
                                                addresslistmap.add(_map);
                                            }
                                        }
                                        catch (Exception _e) {
                                            _e.printStackTrace();
                                        }
                                        if(addresslistmap.size() ==0)
                                        {
                                            dalinear.setVisibility(View.GONE);
                                            nalinear.setVisibility(View.VISIBLE);
                                            addaddressbutton.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            dalinear.setVisibility(View.VISIBLE);
                                            nalinear.setVisibility(View.GONE);
                                            addaddressbutton.setVisibility(View.VISIBLE);
                                            addresslistview.setAdapter(new myaddressesactivity.addresslistadapter(addresslistmap));
                                            setListViewHeightBasedOnItems(addresslistview);
                                        }

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError _databaseError) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(DatabaseError _databaseError) {
                            }
                        });
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
                al.addChildEventListener(_al_child_listener);

                al.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        addresslistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                addresslistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        if(addresslistmap.size() ==0)
                        {
                            dalinear.setVisibility(View.GONE);
                            nalinear.setVisibility(View.VISIBLE);
                            addaddressbutton.setVisibility(View.GONE);
                        }
                        else
                        {
                            dalinear.setVisibility(View.VISIBLE);
                            nalinear.setVisibility(View.GONE);
                            addaddressbutton.setVisibility(View.VISIBLE);
                            addresslistview.setAdapter(new myaddressesactivity.addresslistadapter(addresslistmap));
                            setListViewHeightBasedOnItems(addresslistview);
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });



                addabutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tempmap=new HashMap<>();
                        tempmap.put("name",nameedit.getText().toString());
                        tempmap.put("hno",hnoedit.getText().toString());
                        tempmap.put("apn",apartmentno.getText().toString());
                        tempmap.put("lm",lmedit.getText().toString());
                        tempmap.put("area",areaedit.getText().toString());
                        tempmap.put("city",cityedit.getText().toString());
                        tempmap.put("pin",pinedit.getText().toString());
                        tempmap.put("pno",pnoedit.getText().toString());
                        if(addresslistmap.size() ==0)
                        {
                            tempmap.put("d",1);
                            tempmap.put("s",1);
                        }
                        else
                        {
                            tempmap.put("d",0);
                            tempmap.put("s",0);
                        }
                        al.child(String.valueOf(addresslistmap.size()+1)).updateChildren(tempmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dalinear.setVisibility(View.VISIBLE);
                                nalinear.setVisibility(View.GONE);
                                addaddressbutton.setVisibility(View.VISIBLE);
                                nameedit.setText("");
                                hnoedit.setText("");
                                apartmentno.setText("");
                                lmedit.setText("");
                                areaedit.setText("");
                                cityedit.setText("");
                                pinedit.setText("");
                                pnoedit.setText("");
                            }
                        });

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError _databaseError) {
            }
        });
    }
    public class addresslistadapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;

        public addresslistadapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _view, ViewGroup _viewGroup) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.addresslistlayout, null);
            }

            final TextView nametext = _v.findViewById(R.id.nametext);
            final TextView addresstext = _v.findViewById(R.id.addresstext);
            final TextView phonenotext = _v.findViewById(R.id.phonenotext);
            final Button editbuton = _v.findViewById(R.id.editbutton);
            final Button deletebutton = _v.findViewById(R.id.deletebutton);
            final RadioButton adrb = _v.findViewById(R.id.adrb);

            final DatabaseReference al=_database.getReference("userdata/"+String.valueOf(udindex+1)+"/addresslist");

            nametext.setText("Name:" + _data.get(_position).get("name").toString());
            addresstext.setText("Address:- H.no:" + _data.get(_position).get("hno").toString() + "," + _data.get(_position).get("apn").toString() + "," + _data.get(_position).get("area").toString() + ",near " + _data.get(_position).get("lm").toString() + "," + _data.get(_position).get("city").toString() + ",Pin.no:" + _data.get(_position).get("pin").toString());
            phonenotext.setText("Phone no:" + _data.get(_position).get("pno").toString());
            if (_data.get(_position).get("d").toString().equals("1")) {
                adrb.setChecked(true);
            } else {
                adrb.setChecked(false);
            }
            editbuton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anaf=1;
                    nalinear.setVisibility(View.VISIBLE);
                    dalinear.setVisibility(View.GONE);
                    nameedit.setText(_data.get(_position).get("name").toString());
                    hnoedit.setText(_data.get(_position).get("hno").toString());
                    apartmentno.setText(_data.get(_position).get("apn").toString());
                    lmedit.setText(_data.get(_position).get("lm").toString());
                    areaedit.setText(_data.get(_position).get("area").toString());
                    cityedit.setText(_data.get(_position).get("city").toString());
                    pinedit.setText(_data.get(_position).get("pin").toString());
                    pnoedit.setText(_data.get(_position).get("pno").toString());
                    editabutton.setVisibility(View.VISIBLE);
                    addabutton.setVisibility(View.GONE);
                    editabutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tempmap=new HashMap<>();
                            tempmap.put("name",nameedit.getText().toString());
                            tempmap.put("hno",hnoedit.getText().toString());
                            tempmap.put("apn",apartmentno.getText().toString());
                            tempmap.put("lm",lmedit.getText().toString());
                            tempmap.put("area",areaedit.getText().toString());
                            tempmap.put("city",cityedit.getText().toString());
                            tempmap.put("pin",pinedit.getText().toString());
                            tempmap.put("pno",pnoedit.getText().toString());
                            tempmap.put("s",_data.get(_position).get("s").toString());
                            tempmap.put("d",_data.get(_position).get("d").toString());
                            al.child(String.valueOf(_position+1)).updateChildren(tempmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    editabutton.setVisibility(View.GONE);
                                    addabutton.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }
            });
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_data.size()==1)
                    {
                        al.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dalinear.setVisibility(View.GONE);
                                nalinear.setVisibility(View.VISIBLE);
                            }
                        });
                        setListViewHeightBasedOnItems(addresslistview);
                    }
                    else
                    {
                        if(_data.get(_position).get("d").toString().equals("1"))
                            dedef=1;
                        updateflag=0;
                        for (int i12=_position+1;i12<_data.size();i12++)
                        {
                            tempmap=new HashMap<>();
                            tempmap.put("name",_data.get(i12).get("name").toString());
                            tempmap.put("hno",_data.get(i12).get("hno").toString());
                            tempmap.put("apn",_data.get(i12).get("apn").toString());
                            tempmap.put("lm",_data.get(i12).get("lm").toString());
                            tempmap.put("area",_data.get(i12).get("area").toString());
                            tempmap.put("city",_data.get(i12).get("city").toString());
                            tempmap.put("pin",_data.get(i12).get("pin").toString());
                            tempmap.put("pno",_data.get(i12).get("pno").toString());
                            tempmap.put("s",_data.get(i12).get("s").toString());
                            tempmap.put("d",_data.get(i12).get("d").toString());
                            al.child(String.valueOf(i12)).updateChildren(tempmap).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    updateflag=1;
                                }
                            });
                        }
                        if(updateflag==0)
                        {
                            al.child(String.valueOf(_data.size())).removeValue();
                        }
                        if(dedef==1)
                        {
                            al.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    templistmap = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                        for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            templistmap.add(_map);
                                        }
                                    }
                                    catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                    tempmap=new HashMap<>();
                                    tempmap.put("name",templistmap.get(0).get("name").toString());
                                    tempmap.put("hno",templistmap.get(0).get("hno").toString());
                                    tempmap.put("apn",templistmap.get(0).get("apn").toString());
                                    tempmap.put("lm",templistmap.get(0).get("lm").toString());
                                    tempmap.put("area",templistmap.get(0).get("area").toString());
                                    tempmap.put("city",templistmap.get(0).get("city").toString());
                                    tempmap.put("pin",templistmap.get(0).get("pin").toString());
                                    tempmap.put("pno",templistmap.get(0).get("pno").toString());
                                    tempmap.put("s",1);
                                    tempmap.put("d",1);
                                    al.child(String.valueOf(1)).updateChildren(tempmap);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }
            });
            adrb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    for (int j = 0; j < _data.size(); j++) {
                        tempmap = new HashMap<>();
                        if (j == _position) {
                            tempmap.put("name", _data.get(j).get("name").toString());
                            tempmap.put("hno", _data.get(j).get("hno").toString());
                            tempmap.put("apn", _data.get(j).get("apn").toString());
                            tempmap.put("lm", _data.get(j).get("lm").toString());
                            tempmap.put("area", _data.get(j).get("area").toString());
                            tempmap.put("city", _data.get(j).get("city").toString());
                            tempmap.put("pin", _data.get(j).get("pin").toString());
                            tempmap.put("pno", _data.get(j).get("pno").toString());
                            tempmap.put("s",1);
                            tempmap.put("d",1);
                        } else {
                            tempmap.put("name", _data.get(j).get("name").toString());
                            tempmap.put("hno", _data.get(j).get("hno").toString());
                            tempmap.put("apn", _data.get(j).get("apn").toString());
                            tempmap.put("lm", _data.get(j).get("lm").toString());
                            tempmap.put("area", _data.get(j).get("area").toString());
                            tempmap.put("city", _data.get(j).get("city").toString());
                            tempmap.put("pin", _data.get(j).get("pin").toString());
                            tempmap.put("pno", _data.get(j).get("pno").toString());
                            tempmap.put("s",0);
                            tempmap.put("d",0);
                        }

                        al.child(String.valueOf(j + 1)).updateChildren(tempmap);

                    }

                }
            });


            return _v;
        }
    }
    public static boolean setListViewHeightBasedOnItems(ListView listView)
    {
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter!=null)
        {
            int numberOfItems =listAdapter.getCount();
            int th=0;
            for(int ip=0;ip<numberOfItems;ip++)
            {
                View item =listAdapter.getView(ip,null,listView);
                float px = 500*(listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px,View.MeasureSpec.AT_MOST),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
                th=th+item.getMeasuredHeight()+50;
            }
            int totalDividerHeight=listView.getDividerHeight()*(numberOfItems-1);
            int totalPadding=listView.getPaddingTop()+listView.getPaddingBottom();
            ViewGroup.LayoutParams params= listView.getLayoutParams();
            params.height=th+totalDividerHeight+totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;
        }
        else
            return false;
    }
}
