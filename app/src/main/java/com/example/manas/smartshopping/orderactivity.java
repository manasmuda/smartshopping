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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;

public class orderactivity extends AppCompatActivity {

    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _toggle;

    private Intent homepageintent=new Intent();

    private LinearLayout addressllinear;
    private LinearLayout pobuttonlinear;
    private LinearLayout addressclinear;
    private LinearLayout orderbilllayout;
    private LinearLayout odonelinear;
    private LinearLayout dblinear;
    private Button donebutton;
    private Button pobutton;
    private Button addabutton;
    private Button addaddressbutton;
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
    private TextView textView9;
    private TextView ocodtext;
    private TextView oconotext;
    private TextView totalcost;
    private TextView ocnametext;
    private TextView ocaddresstext;
    private TextView ocpnotext;
    private ListView orderdesclist;

    private AlertDialog.Builder cd;

    private String pname="";
    private String pemail="";
    private int counter1=0;
    private int udindex=0;
    private int alindex=0;
    private int anaf=0;

    private Calendar odat=Calendar.getInstance();

    private ArrayList<HashMap<String,Object>> userdatalistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> addresslistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> orderlistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> cartlistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> ordercartlistmap=new ArrayList<>();
    private HashMap<String,Object> tempmap=new HashMap<>();

    private FirebaseDatabase _database= FirebaseDatabase.getInstance();

    private DatabaseReference ud=_database.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    private DatabaseReference uo=_database.getReference("orderdata");
    private ChildEventListener _uo_child_listener;

    @Override
    public void onBackPressed() {
        if(_drawer.isDrawerOpen(GravityCompat.START))
        {
            _drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(anaf==1)
            {
                addressclinear.setVisibility(View.GONE);
                addressllinear.setVisibility(View.VISIBLE);
                pobuttonlinear.setVisibility(View.VISIBLE);
                nameedit.setText("");
                hnoedit.setText("");
                apartmentno.setText("");
                lmedit.setText("");
                areaedit.setText("");
                cityedit.setText("");
                pinedit.setText("");
                pnoedit.setText("");
                anaf=0;
            }
            else {
                super.onBackPressed();
            }
        }

    }

    @Override
    protected void onStart() {
        Initialize();
        InitializeLogic();
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(_toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderactivity);
        Initialize();
        InitializeLogic();
    }
    private void Initialize()
    {
        _drawer= findViewById(R.id._drawer);
        _toggle=new ActionBarDrawerToggle(orderactivity.this,_drawer,R.string.open,R.string.close);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        anaf=0;

        profilename=findViewById(R.id.profilename);
        profileemail=findViewById(R.id.profileemail);
        addresslistview=findViewById(R.id.addresslistview);
        addressllinear=findViewById(R.id.addressllinear);
        addressclinear=findViewById(R.id.addressclinear);
        pobuttonlinear=findViewById(R.id.pobuttonlinear);
        pobutton=findViewById(R.id.pobutton);
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
        textView9=findViewById(R.id.textView9);
        orderbilllayout=findViewById(R.id.orderbilllayout);
        odonelinear=findViewById(R.id.odonelinear);
        dblinear=findViewById(R.id.dblinear);
        donebutton=findViewById(R.id.donebutton);
        ocodtext=findViewById(R.id.ocodtext);
        oconotext=findViewById(R.id.oconotext);
        ocnametext=findViewById(R.id.ocnametext);
        ocaddresstext=findViewById(R.id.ocaddresstext);
        ocpnotext=findViewById(R.id.ocpnotext);
        orderdesclist=findViewById(R.id.ordereddesclist);
        totalcost=findViewById(R.id.totalcost);

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

        _uo_child_listener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderlistmap=new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                orderlistmap.add(_map);
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
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderlistmap=new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                orderlistmap.add(_map);
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
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderlistmap=new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                orderlistmap.add(_map);
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
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError _param1) {
                final int _errorCode=_param1.getCode();
                final String _errorMessage=_param1.getMessage();

            }
        };
       uo.addChildEventListener(_uo_child_listener);

    }

    private void InitializeLogic()
    {
        addressclinear.setVisibility(View.GONE);
        orderbilllayout.setVisibility(View.GONE);
        addressllinear.setVisibility(View.VISIBLE);
        pobuttonlinear.setVisibility(View.VISIBLE);
        uo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderlistmap=new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot _data : dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        orderlistmap.add(_map);
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

                    final DatabaseReference uol=_database.getReference("orderdata/"+String.valueOf(orderlistmap.size() +1)+"/orderlist");
                    ChildEventListener _uol_child_listener;
                    final int uolindex=orderlistmap.size();

                uol.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        ordercartlistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                ordercartlistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });

                _uol_child_listener=new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        final String _childKey = _param1.getKey();
                        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                        uol.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                ordercartlistmap= new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        ordercartlistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
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
                        uol.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                ordercartlistmap= new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        ordercartlistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
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
                        uol.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                ordercartlistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        ordercartlistmap.add(_map);
                                    }
                                }
                                catch (Exception _e) {
                                    _e.printStackTrace();
                                }
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
                uol.addChildEventListener(_uol_child_listener);


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

                                            if(addresslistmap.size() ==0)
                                            {
                                                addressllinear.setVisibility(View.GONE);
                                                addressclinear.setVisibility(View.VISIBLE);
                                                pobuttonlinear.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                addressllinear.setVisibility(View.VISIBLE);
                                                addressclinear.setVisibility(View.GONE);
                                                pobuttonlinear.setVisibility(View.VISIBLE);
                                                addresslistview.setAdapter(new addresslistadapter(addresslistmap));
                                            }
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

                                            if(addresslistmap.size() ==0)
                                            {
                                                addressllinear.setVisibility(View.GONE);
                                                addressclinear.setVisibility(View.VISIBLE);
                                                pobuttonlinear.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                addressllinear.setVisibility(View.VISIBLE);
                                                addressclinear.setVisibility(View.GONE);
                                                pobuttonlinear.setVisibility(View.VISIBLE);
                                                addresslistview.setAdapter(new addresslistadapter(addresslistmap));
                                            }
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
                                            if(addresslistmap.size() ==0)
                                            {
                                                addressllinear.setVisibility(View.GONE);
                                                addressclinear.setVisibility(View.VISIBLE);
                                                pobuttonlinear.setVisibility(View.GONE);
                                            }
                                            else
                                            {
                                                addressllinear.setVisibility(View.VISIBLE);
                                                addressclinear.setVisibility(View.GONE);
                                                pobuttonlinear.setVisibility(View.VISIBLE);
                                                addresslistview.setAdapter(new addresslistadapter(addresslistmap));
                                            }

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
                            for(int xew=0;xew<addresslistmap.size();xew++)
                            {
                                tempmap=new HashMap<>();
                                tempmap.put("name", addresslistmap.get(xew).get("name").toString());
                                tempmap.put("hno", addresslistmap.get(xew).get("hno").toString());
                                tempmap.put("apn", addresslistmap.get(xew).get("apn").toString());
                                tempmap.put("lm", addresslistmap.get(xew).get("lm").toString());
                                tempmap.put("area", addresslistmap.get(xew).get("area").toString());
                                tempmap.put("city", addresslistmap.get(xew).get("city").toString());
                                tempmap.put("pin", addresslistmap.get(xew).get("pin").toString());
                                tempmap.put("pno", addresslistmap.get(xew).get("pno").toString());
                                tempmap.put("d",addresslistmap.get(xew).get("d").toString());
                                tempmap.put("s",0);
                                if(addresslistmap.get(xew).get("d").toString().equals("1"))
                                {
                                    tempmap=new HashMap<>();
                                    tempmap.put("name", addresslistmap.get(xew).get("name").toString());
                                    tempmap.put("hno", addresslistmap.get(xew).get("hno").toString());
                                    tempmap.put("apn", addresslistmap.get(xew).get("apn").toString());
                                    tempmap.put("lm", addresslistmap.get(xew).get("lm").toString());
                                    tempmap.put("area", addresslistmap.get(xew).get("area").toString());
                                    tempmap.put("city", addresslistmap.get(xew).get("city").toString());
                                    tempmap.put("pin", addresslistmap.get(xew).get("pin").toString());
                                    tempmap.put("pno", addresslistmap.get(xew).get("pno").toString());
                                    tempmap.put("d",addresslistmap.get(xew).get("d").toString());
                                    tempmap.put("s",1);
                                }
                                al.child(String.valueOf(xew+1)).updateChildren(tempmap);
                            }
                            if(addresslistmap.size() ==0)
                            {
                                addressllinear.setVisibility(View.GONE);
                                addressclinear.setVisibility(View.VISIBLE);
                                pobuttonlinear.setVisibility(View.GONE);
                            }
                            else
                            {
                                addressllinear.setVisibility(View.VISIBLE);
                                addressclinear.setVisibility(View.GONE);
                                pobuttonlinear.setVisibility(View.VISIBLE);
                                addresslistview.setAdapter(new addresslistadapter(addresslistmap));
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
                                    nameedit.setText("");
                                    hnoedit.setText("");
                                    apartmentno.setText("");
                                    lmedit.setText("");
                                    areaedit.setText("");
                                    cityedit.setText("");
                                    pinedit.setText("");
                                    pnoedit.setText("");
                                    addressllinear.setVisibility(View.VISIBLE);
                                    addressclinear.setVisibility(View.GONE);
                                    pobuttonlinear.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });

                    addaddressbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anaf=1;
                            addressllinear.setVisibility(View.GONE);
                            addressclinear.setVisibility(View.VISIBLE);
                            pobuttonlinear.setVisibility(View.GONE);
                        }
                    });
                    pobutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            tempmap=new HashMap<>();
                            alindex=0;
                            for(int j = 0; j< addresslistmap.size(); j++)
                            {
                                if(addresslistmap.get(j).get("d").toString().equals("1"))
                                {
                                    alindex=j;
                                }
                            }
                            tempmap.put("name",addresslistmap.get(alindex).get("name").toString());
                            tempmap.put("address","H.no:"+addresslistmap.get(alindex).get("hno").toString()+","+addresslistmap.get(alindex).get("apn").toString()+","+addresslistmap.get(alindex).get("area").toString()+",near "+addresslistmap.get(alindex).get("lm").toString()+","+addresslistmap.get(alindex).get("city").toString()+",Pin.no:"+addresslistmap.get(alindex).get("pin").toString());
                            tempmap.put("pno",addresslistmap.get(alindex).get("pno"));
                            tempmap.put("email",userdatalistmap.get(udindex).get("email"));
                            tempmap.put("date",new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
                            tempmap.put("uid",userdatalistmap.get(udindex).get("uid"));
                            final DatabaseReference cd=_database.getReference("userdata/"+String.valueOf(udindex+1)+"/cart");
                            cd.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            cartlistmap=new ArrayList<>();
                                            try {
                                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                                for (DataSnapshot _data : dataSnapshot.getChildren()) {
                                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                                    cartlistmap.add(_map);
                                                }
                                            }
                                            catch (Exception _e) {
                                                _e.printStackTrace();
                                            }
                                    int price=0;
                                    for (int j = 0; j< cartlistmap.size(); j++)
                                    {
                                        price=price+(int)Double.parseDouble(cartlistmap.get(j).get("price").toString());
                                    }
                                    tempmap.put("Cost",price);
                                    tempmap.put("orderno",orderlistmap.size()+1);
                                    uo.child(String.valueOf(orderlistmap.size()+1)).updateChildren(tempmap);
                                    orderlistmap.add(tempmap);
                                    ordercartlistmap=new ArrayList<>();
                                    for (int j = 0; j< cartlistmap.size(); j++)
                                    {
                                        tempmap=new HashMap<>();
                                        tempmap.put("name",cartlistmap.get(j).get("name"));
                                        tempmap.put("quantityno",cartlistmap.get(j).get("quantityno"));
                                        tempmap.put("price",cartlistmap.get(j).get("price"));
                                        tempmap.put("url",cartlistmap.get(j).get("url"));
                                        if(cartlistmap.get(j).containsKey("quantity"))
                                        {
                                            tempmap.put("quantity",cartlistmap.get(j).get("quantity"));
                                        }
                                        ordercartlistmap.add(tempmap);
                                        uol.child(String.valueOf(j+1)).updateChildren(tempmap);
                                    }

                                    cd.removeValue();
                                    addressclinear.setVisibility(View.GONE);
                                    addressllinear.setVisibility(View.GONE);
                                    orderbilllayout.setVisibility(View.VISIBLE);
                                    pobuttonlinear.setVisibility(View.GONE);
                                    setResult(RESULT_OK,homepageintent);
                                    Toast.makeText(orderactivity.this,"Order Placed Successfully",Toast.LENGTH_LONG).show();

                                    oconotext.setText("Order Number: "+String.valueOf(orderlistmap.get(uolindex).get("orderno")));
                                    ocodtext.setText("Ordered Date: "+String.valueOf(orderlistmap.get(uolindex).get("date")));
                                    ocaddresstext.setText("Address: "+String.valueOf(orderlistmap.get(uolindex).get("address")));
                                    ocnametext.setText("Name: "+String.valueOf(orderlistmap.get(uolindex).get("name")));
                                    ocpnotext.setText("Phone number: "+String.valueOf(orderlistmap.get(uolindex).get("pno")));
                                    totalcost.setText("TOTAL: "+String.valueOf(orderlistmap.get(uolindex).get("Cost")));

                                    orderdesclist.setAdapter(new odlistviewAdapter(ordercartlistmap));

                                    donebutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            homepageintent.putExtra("key1","ops");


                                            orderactivity.this.finish();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

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
            LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.addresslistlayout, null);
            }

            final TextView nametext=_v.findViewById(R.id.nametext);
            final TextView addresstext=_v.findViewById(R.id.addresstext);
            final TextView phonenotext=_v.findViewById(R.id.phonenotext);
            final Button editbuton=_v.findViewById(R.id.editbutton);
            final Button deletebutton=_v.findViewById(R.id.deletebutton);
            final RadioButton adrb=_v.findViewById(R.id.adrb);

            nametext.setText("Name:"+_data.get(_position).get("name").toString());
            addresstext.setText("Address:- H.no:"+_data.get(_position).get("hno").toString()+","+_data.get(_position).get("apn").toString()+","+_data.get(_position).get("area").toString()+",near "+_data.get(_position).get("lm").toString()+","+_data.get(_position).get("city").toString()+",Pin.no:"+_data.get(_position).get("pin").toString());
            phonenotext.setText("Phone no:"+_data.get(_position).get("pno").toString());
            editbuton.setVisibility(View.GONE);
            deletebutton.setVisibility(View.GONE);
            if(_data.get(_position).get("s").toString().equals("1"))
            {
                adrb.setChecked(true);
            }
            else
            {
                adrb.setChecked(false);
            }

            adrb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    for(int j = 0; j< _data.size(); j++) {
                        tempmap = new HashMap<>();
                        if(j==_position) {
                            tempmap.put("name", _data.get(j).get("name").toString());
                            tempmap.put("hno", _data.get(j).get("hno").toString());
                            tempmap.put("apn", _data.get(j).get("apn").toString());
                            tempmap.put("lm", _data.get(j).get("lm").toString());
                            tempmap.put("area", _data.get(j).get("area").toString());
                            tempmap.put("city", _data.get(j).get("city").toString());
                            tempmap.put("pin", _data.get(j).get("pin").toString());
                            tempmap.put("pno", _data.get(j).get("pno").toString());
                            tempmap.put("d",_data.get(j).get("d").toString());
                            tempmap.put("s",1);
                        }
                        else
                        {
                            tempmap.put("name", _data.get(j).get("name").toString());
                            tempmap.put("hno", _data.get(j).get("hno").toString());
                            tempmap.put("apn", _data.get(j).get("apn").toString());
                            tempmap.put("lm", _data.get(j).get("lm").toString());
                            tempmap.put("area", _data.get(j).get("area").toString());
                            tempmap.put("city", _data.get(j).get("city").toString());
                            tempmap.put("pin", _data.get(j).get("pin").toString());
                            tempmap.put("pno", _data.get(j).get("pno").toString());
                            tempmap.put("d",_data.get(j).get("d").toString());
                            tempmap.put("s",0);
                        }

                        DatabaseReference tdb=_database.getReference("userdata/"+String.valueOf(udindex+1)+"/addresslist");
                        tdb.child(String.valueOf(j+1)).updateChildren(tempmap);
                        uo.addChildEventListener(_uo_child_listener);

                    }

                }
            });


            return _v;
        }
    }
    public class odlistviewAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;



        public odlistviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
            LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.orderdesplayout, null);
            }

            final TextView productnametext = _v.findViewById(R.id.productnametext);
            final TextView costtext= _v.findViewById(R.id.costtext);
            final ImageView productimage= _v.findViewById(R.id.productimage);
            final Button addbutton=_v.findViewById(R.id.addbutton);
            final TextView quantitytext=_v.findViewById(R.id.quantitytext);
            final TextView quantitynotext=_v.findViewById(R.id.quantitynotext);
            final Button plusbutton=_v.findViewById(R.id.plusbutton);
            final Button minusbutton=_v.findViewById(R.id.minusbutton);
            final LinearLayout modifyquantitylinear=_v.findViewById(R.id.modifyquantitylinear);

            modifyquantitylinear.setVisibility(View.VISIBLE);
            addbutton.setVisibility(View.GONE);
            plusbutton.setVisibility(View.GONE);
            minusbutton.setVisibility(View.GONE);

            productnametext.setText(_data.get(_position).get("name").toString());
            costtext.setText("Cost:"+_data.get(_position).get("price").toString());
            quantitynotext.setText("No.of items:"+_data.get(_position).get("quantityno").toString());
            Glide.with(getApplicationContext()).load(_data.get(_position).get("url").toString()).into(productimage);
            Glide.with(getApplicationContext()).load(_data.get(_position).get("url").toString()).into(productimage);
            Glide.with(getApplicationContext()).load(_data.get(_position).get("url").toString()).into(productimage);
            if(_data.get(_position).containsKey("quantity")) {
                quantitytext.setVisibility(View.VISIBLE);
                quantitytext.setText("Quantity:" + _data.get(_position).get("quantity").toString());
            }
            else
            {
                quantitytext.setVisibility(View.INVISIBLE);
            }
            return _v;
        }
    }

}
