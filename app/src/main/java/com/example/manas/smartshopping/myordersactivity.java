package com.example.manas.smartshopping;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class myordersactivity extends AppCompatActivity {

    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _toggle;

    private Intent orderactivity = new Intent();
    private Intent searchactivity = new Intent();
    private Intent homeactivity = new Intent();
    private Intent myaddressessactivity = new Intent();
    private Intent myaccountactivity = new Intent();
    private Intent wishlistactivity = new Intent();

    private TextView profilename;
    private TextView profileemail;
    private TextView ocodtext;
    private TextView oconotext;
    private TextView totalcost;
    private TextView ocnametext;
    private TextView ocaddresstext;
    private TextView ocpnotext;
    private ListView orderdesclist;
    private ListView morderlist;
    private ScrollView orderlistscroll;
    private ScrollView orderbilllayout;
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
    private int alindex=0;
    private int anaf=0;

    private ArrayList<HashMap<String,Object>> userdatalistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> templistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> orderlistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> orderlistmap1=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> templistmap1=new ArrayList<>();
    private HashMap<String,Object> tempmap=new HashMap<>();
    private ArrayList<Object> uroidl=new ArrayList<>();

    private FirebaseDatabase _database= FirebaseDatabase.getInstance();

    private DatabaseReference ud=_database.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    private DatabaseReference uo=_database.getReference("orderdata");
    private ChildEventListener _uo_child_listener;

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
                        if (getIntent().getIntExtra("pp", 0) == 11) {
                            myaddressessactivity.putExtra("ac", "lo");
                            setResult(RESULT_OK, myaddressessactivity);
                        }
                    }
                }
                myordersactivity.this.finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(_toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                orderlistscroll.setVisibility(View.VISIBLE);
                orderbilllayout.setVisibility(View.GONE);
                anaf=0;
            }
            else {
                super.onBackPressed();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myordersactivity);
        Initialize();
        InitializeLogic();
    }

    public void Initialize(){

        _drawer= findViewById(R.id._drawer);
        _toggle=new ActionBarDrawerToggle(myordersactivity.this,_drawer,R.string.open,R.string.close);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myaccountactivity.setClass(myordersactivity.this,myaccountactivity.class);
        myaddressessactivity.setClass(myordersactivity.this,myaddressesactivity.class);
        homeactivity.setClass(myordersactivity.this,homepage.class);

        profilename=findViewById(R.id.profilename);
        profileemail=findViewById(R.id.profileemail);
        orderbilllayout=findViewById(R.id.orderbilllayout);
        ocodtext=findViewById(R.id.ocodtext);
        oconotext=findViewById(R.id.oconotext);
        ocnametext=findViewById(R.id.ocnametext);
        ocaddresstext=findViewById(R.id.ocaddresstext);
        ocpnotext=findViewById(R.id.ocpnotext);
        orderdesclist=findViewById(R.id.ordereddesclist);
        totalcost=findViewById(R.id.totalcost);
        orderlistscroll=findViewById(R.id.orderlistscroll);
        morderlist=findViewById(R.id.morderlist);
        homebutton=findViewById(R.id.homebutton);
        myordersbutton=findViewById(R.id.myordersbutton);
        myaddressbutton=findViewById(R.id.myaddressbutton);
        myaccountbutton=findViewById(R.id.myaccountbutton);
        logoutbutton=findViewById(R.id.logoutbutton);

        udindex=getIntent().getIntExtra("ui",0);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeactivity.putExtra("pp",12);
                startActivityForResult(homeactivity,10);
            }
        });

        myaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaddressessactivity.putExtra("pp",12);
                startActivityForResult(myaddressessactivity,11);

            }
        });

        myaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaccountactivity.putExtra("pp",13);
                startActivityForResult(myaccountactivity,13);
            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("pp",0) == 10) {
                    homeactivity.putExtra("ac", "lo");
                    setResult(RESULT_OK, homeactivity);
                } else {
                    if (getIntent().getIntExtra("pp", 0) == 13) {
                        myaccountactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK, myaccountactivity);
                    } else {
                        if (getIntent().getIntExtra("pp", 0) == 11) {
                            myaddressessactivity.putExtra("ac", "lo");
                            setResult(RESULT_OK, myaddressessactivity);
                        }
                    }
                }
                myordersactivity.this.finish();
            }
        });

    }

    public void InitializeLogic(){

        orderbilllayout.setVisibility(View.GONE);
        orderlistscroll.setVisibility(View.VISIBLE);

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
                uroidl = new ArrayList<>();
                for (int j13 = 0; j13 < orderlistmap.size(); j13++) {
                    if (orderlistmap.get(j13).get("uid").toString().equals(FirebaseAuth.getInstance().getUid())) {
                        uroidl.add(j13);
                    }
                }
                orderlistmap1=new ArrayList<>();
                for (int i13=0;i13<orderlistmap.size();i13++)
                {
                    if(uroidl.contains(i13))
                    {
                        orderlistmap1.add(orderlistmap.get(i13));
                    }
                }
                morderlist.setAdapter(new orderlistadapter(orderlistmap1));
                setListViewHeightBasedOnItems(morderlist);
            }
            @Override
            public void onCancelled(DatabaseError _databaseError) {
            }
        });


    }

    public class orderlistadapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;
        public orderlistadapter(ArrayList<HashMap<String, Object>> _arr) {
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
                _v = _inflater.inflate(R.layout.myorderlistlayout, null);
            }

            final TextView ordernotext = _v.findViewById(R.id.ordernotext);
            final TextView quantitytext = _v.findViewById(R.id.quantitytext);
            final TextView datetext = _v.findViewById(R.id.datetext);
            final TextView costtext = _v.findViewById(R.id.costtext);
            final Button vdbutton = _v.findViewById(R.id.vdbutton);

            costtext.setText("for Amount Rs."+_data.get(_position).get("Cost").toString());
            ordernotext.setText("Order no: "+_data.get(_position).get("orderno").toString());
            datetext.setText("Order placed on " + _data.get(_position).get("date").toString());
            final DatabaseReference odl = _database.getReference("orderdata/"+_data.get(_position).get("orderno").toString()+"/orderlist");
            odl.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot _dataSnapshot) {
                    profilename.setText(String.valueOf(2));
                    templistmap = new ArrayList<>();
                    try {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                            HashMap<String, Object> _map = _data.getValue(_ind);
                            templistmap.add(_map);
                        }
                    }
                    catch (Exception _e) {
                        _e.printStackTrace();
                    }
                    quantitytext.setText(templistmap.size()+" Items");
                    vdbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            anaf=1;
                            final DatabaseReference odl1 = _database.getReference("orderdata/"+_data.get(_position).get("orderno").toString()+"/orderlist");
                            odl1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot _dataSnapshot) {
                                    profilename.setText(String.valueOf(2));
                                    templistmap1 = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            templistmap1.add(_map);
                                        }
                                    }
                                    catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                    orderbilllayout.setVisibility(View.VISIBLE);
                                    orderlistscroll.setVisibility(View.GONE);
                                    oconotext.setText("Order no: "+_data.get(_position).get("orderno").toString());
                                    ocodtext.setText("Order placed on " + _data.get(_position).get("date").toString());
                                    orderdesclist.setAdapter(new odlistviewAdapter(templistmap1));
                                    setListViewHeightBasedOnItems(orderdesclist);
                                    totalcost.setText("Total Cost: "+_data.get(_position).get("Cost").toString());
                                    ocnametext.setText("Name: "+_data.get(_position).get("name").toString());
                                    ocaddresstext.setText("Address: "+_data.get(_position).get("address").toString());
                                    ocpnotext.setText("Phone number: "+_data.get(_position).get("pno").toString());
                                }
                                @Override
                                public void onCancelled(DatabaseError _databaseError) {
                                }
                            });

                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError _databaseError) {
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
                th=th+item.getMeasuredHeight()+10;
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
        public View getView(final int _position1, View _view, ViewGroup _viewGroup) {
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

            productnametext.setText(_data.get(_position1).get("name").toString());
            costtext.setText("Cost:"+_data.get(_position1).get("price").toString());
            quantitynotext.setText("No.of items:"+_data.get(_position1).get("quantityno").toString());
            productimage.setImageResource(R.drawable.ic_launcher_background);
            Glide.with(getApplicationContext()).load(_data.get(_position1).get("url").toString()).into(productimage);
            Glide.with(getApplicationContext()).load(_data.get(_position1).get("url").toString()).into(productimage);
            if(_data.get(_position1).containsKey("quantity")) {
                quantitytext.setVisibility(View.VISIBLE);
                quantitytext.setText("Quantity:" + _data.get(_position1).get("quantity").toString());
            }
            else
            {
                quantitytext.setVisibility(View.INVISIBLE);
            }

            return _v;
        }
    }
}
