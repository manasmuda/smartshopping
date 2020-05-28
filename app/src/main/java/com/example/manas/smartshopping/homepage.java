package com.example.manas.smartshopping;
import android.app.*;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.zip.Inflater;

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
import android.os.Bundle;
import android.app.ProgressDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
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

public class homepage extends AppCompatActivity {

    private FirebaseDatabase _database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference imagesRef = storageRef.child("images");

    private Intent orderactivity = new Intent();
    private Intent searchactivity = new Intent();
    private Intent myordersactivity = new Intent();
    private Intent myaddressessactivity = new Intent();
    private Intent myaccountactivity = new Intent();
    private Intent wishlistactivity = new Intent();

    private ViewPager trendingslider;
    private ViewPager recommendedslider;
    private SliderAdapter slideAdapter;
    private SlideAdapter1 slideAdapter1;


    private View view1;

    private ArrayList<HashMap<String, Object>> categorieslistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> recommendedlistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> trendinglistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> userdatalistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> cproductlistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap1 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap2 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap3 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap4 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap5 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap6 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> templistmap7 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> taglistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> cartlistmap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> ordermap = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> olistmap = new ArrayList<>();
    private HashMap<String, Object> tempmap = new HashMap<>();
    private HashMap<String, Object> tempmap1 = new HashMap<>();
    private HashMap<String,Object> tempmap2 =new HashMap<>();
    private ArrayList<String> tempstringlist = new ArrayList<>();
    private ArrayList<String> tempstringlist1 = new ArrayList<>();
    private ArrayList<String> tempstringlist2 = new ArrayList<>();
    private ArrayList<Object> uroidl = new ArrayList<>();

    private DrawerLayout _drawer;
    private ActionBarDrawerToggle _toggle;


    private Intent mainintent = new Intent();

    private String authid = "";
    private String pname = "";
    private String pemail = "";
    private int counter1 = 0;
    private int udindex = 0;
    private String dk = "";
    private String dk1 ="";
    private int cartflag = 0;
    private int flag = 0;
    private int i = 0, i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0;
    private int j = 0, j1 = 0, j2 = 0, j3 = 0, j4 = 0, j5 = 0, j6 = 0, j7 = 0;
    private int l = 0, l1 = 0, l2 = 0, l3 = 0, l4 = 0, l5 = 0, l6 = 0, l7 = 0;
    private int updateflag=0;
    private int searchflag=0;

    private LinearLayout linearscreen;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout menulinear;
    private LinearLayout searchlinear;
    private LinearLayout cartlinear;
    private ImageView cartimage;
    private TextView cartcounttext;
    private TextView profilename;
    private TextView profileemail;
    private TextView recommendedtext;
    private TextView trendingtext;
    private ScrollView mainscroll;
    private LinearLayout mainscrolllinear;
    private ListView categorylistview;
    private ListView odlist;
    private ImageView profileimage;
    private Button homebutton;
    private Button myordersbutton;
    private Button myaddressbutton;
    private Button wishlistbutton;
    private Button myaccountbutton;
    private Button logoutbutton;
    private Button orderbutton;
    private TextView categorytext;
    private TextView cattypetext;
    private android.support.v7.widget.SearchView searchbarview;
    private LinearLayout trendinglinear;
    private Button trobutton;
    private Button reobutton;

    private DatabaseReference Categories = _database.getReference("Categories");
    private ChildEventListener _Categories_child_listener;

    private DatabaseReference ud = _database.getReference("userdata");
    private ChildEventListener _ud_child_listener;

    private DatabaseReference uo = _database.getReference("orderdata");
    private ChildEventListener _uo_child_listener;


    private void trerecdef() {
        if (ordermap.size() == 0) {
            recommendedtext.setVisibility(View.GONE);
            trendingtext.setVisibility(View.GONE);
            trendingslider.setVisibility(View.GONE);
            trobutton.setVisibility(View.GONE);
            reobutton.setVisibility(View.GONE);
            recommendedslider.setVisibility(View.GONE);
        } else {
            trendingtext.setVisibility(View.VISIBLE);
            trendingslider.setVisibility(View.VISIBLE);
            trobutton.setVisibility(View.VISIBLE);
            templistmap2 = new ArrayList<>();

            trendinglistmap = new ArrayList<>();
            for (i = 0; i < ordermap.size(); i++) {
                final DatabaseReference tempdb1 = _database.getReference("orderdata/" + String.valueOf(i + 1) + "/orderlist");
                tempdb1.addListenerForSingleValueEvent(new ValueEventListener(){

                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        templistmap1 = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                templistmap1.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        for (j = 0; j < templistmap1.size(); j++) {
                            int pos1 = -1;
                            for (l = 0; l < templistmap2.size(); l++) {
                                if (templistmap1.get(j).get("name").toString().equals(templistmap2.get(l).get("name").toString())) {
                                    pos1 = l;
                                }
                            }
                            if (pos1 == -1) {
                                tempmap = new HashMap<>();
                                tempmap.put("name", templistmap1.get(j).get("name").toString());
                                tempmap.put("count", 1);
                                templistmap2.add(tempmap);

                            } else {
                                tempmap = new HashMap<>();
                                tempmap.put("name", templistmap2.get(pos1).get("name").toString());
                                tempmap.put("count", (int) Double.parseDouble(templistmap2.get(pos1).get("count").toString())+1);
                                templistmap2.remove(pos1);
                                templistmap2.add(tempmap);
                            }
                        }
                        tempstringlist = new ArrayList<>();
                        int max = 0;
                        int pos = -1;
                        ArrayList<HashMap<String, Object>> tlm10 = new ArrayList<>();
                        tlm10 = (ArrayList) templistmap2.clone();
                        int mapsize = tlm10.size();
                        for (int i1 = 0; i1 < mapsize; i1++) {
                            max = 0;
                            pos = 0;
                            for (int j1 = 0; j1 < tlm10.size(); j1++) {
                                if (max < (int) Double.parseDouble(tlm10.get(j1).get("count").toString())) {
                                    pos = j1;
                                    max = (int) Double.parseDouble(tlm10.get(j1).get("count").toString());
                                }
                            }
                            int pos3 = -1;
                            for (j3 = 0; j3 < tempstringlist.size(); j3++) {
                                if (tlm10.get(pos).get("name").toString().equals(tempstringlist.get(j3))) {
                                    pos3 = l;
                                }
                            }
                            if (pos3 == -1) {
                                tempstringlist.add(tlm10.get(pos).get("name").toString());
                                tlm10.remove(pos);
                            }
                        }
                        trendinglistmap = new ArrayList<>();

                        for (int i2 = 0; i2 < categorieslistmap.size(); i2++) {
                            final DatabaseReference tempdb = _database.getReference("Categories/" + String.valueOf(i2 + 1) + "/" + categorieslistmap.get(i2).get("fbn").toString());
                            tempdb.addListenerForSingleValueEvent(new ValueEventListener() {

                                public void onDataChange(DataSnapshot _dataSnapshot) {
                                    templistmap = new ArrayList<>();
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
                                    for (int j2 = 0; j2 < templistmap.size(); j2++) {
                                        for (l2 = 0; l2 < tempstringlist.size(); l2++) {
                                            if (tempstringlist.get(l2).equals(templistmap.get(j2).get("name").toString())) {
                                                tempmap1 = new HashMap<>();
                                                tempmap1.put("name", templistmap.get(j2).get("name").toString());
                                                tempmap1.put("price", templistmap.get(j2).get("price").toString());
                                                tempmap1.put("url",templistmap.get(j2).get("url").toString());
                                                tempmap1.put("rank", String.valueOf(l2));
                                                if (templistmap.get(j2).containsKey("quantity")) {
                                                    tempmap1.put("quantity", templistmap.get(j2).get("quantity"));
                                                }
                                                int pos2 = -1;
                                                for (l3 = 0; l3 < trendinglistmap.size(); l3++) {
                                                    if (tempmap1.get("name").toString().equals(trendinglistmap.get(l3).get("name").toString())) {
                                                        pos2 = l;
                                                    }
                                                }
                                                if (pos2 == -1) {
                                                    trendinglistmap.add(tempmap1);
                                                }
                                            }
                                        }
                                    }
                                    slideAdapter = new SliderAdapter(homepage.this, trendinglistmap);
                                    trendingslider.setAdapter(slideAdapter);
                                    trendingslider.setAdapter(slideAdapter);
                                }

                                @Override
                                public void onCancelled(DatabaseError _databaseError) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }


            uroidl = new ArrayList<>();
            for (j = 0; j < ordermap.size(); j++) {
                if (ordermap.get(j).get("uid").toString().equals(FirebaseAuth.getInstance().getUid())) {
                    uroidl.add(j);
                }
            }
            if (uroidl.size() == 0) {
                recommendedtext.setVisibility(View.GONE);
                recommendedslider.setVisibility(View.GONE);
                reobutton.setVisibility(View.GONE);
            } else {
                recommendedtext.setVisibility(View.VISIBLE);
                recommendedslider.setVisibility(View.VISIBLE);
                reobutton.setVisibility(View.VISIBLE);
                recommendedlistmap = new ArrayList<>();
                templistmap3 = new ArrayList<>();
                for (i = 0; i < ordermap.size(); i++) {
                    if (uroidl.contains(i)) {
                        final DatabaseReference tempdb1 = _database.getReference("orderdata/" + String.valueOf(i + 1) + "/orderlist");
                        tempdb1.addListenerForSingleValueEvent(new ValueEventListener() {

                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                templistmap4 = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                    };
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        templistmap4.add(_map);
                                    }
                                } catch (Exception _e) {
                                    _e.printStackTrace();
                                }
                                for (j = 0; j < templistmap4.size(); j++) {
                                    int pos1 = -1;
                                    for (l = 0; l < templistmap3.size(); l++) {
                                        if (templistmap4.get(j).get("name").toString().equals(templistmap3.get(l).get("name").toString())) {
                                            pos1 = l;
                                        }
                                    }
                                    if (pos1 == -1) {
                                        tempmap = new HashMap<>();
                                        tempmap.put("name", templistmap4.get(j).get("name").toString());
                                        tempmap.put("count", 1);
                                        templistmap3.add(tempmap);

                                    } else {
                                        tempmap = new HashMap<>();
                                        tempmap.put("name", templistmap3.get(pos1).get("name").toString());
                                        tempmap.put("count", (int) Double.parseDouble(templistmap3.get(pos1).get("count").toString()) + 1);
                                        templistmap3.remove(pos1);
                                        templistmap3.add(tempmap);
                                    }
                                }
                                tempstringlist1 = new ArrayList<>();
                                int max = 0;
                                int pos = -1;
                                ArrayList<HashMap<String, Object>> tlm10 = new ArrayList<>();
                                tlm10 = (ArrayList) templistmap3.clone();
                                int mapsize = tlm10.size();
                                for (int i1 = 0; i1 < mapsize; i1++) {
                                    max = 0;
                                    pos = 0;
                                    for (int j1 = 0; j1 < tlm10.size(); j1++) {
                                        if (max < (int) Double.parseDouble(tlm10.get(j1).get("count").toString())) {
                                            pos = j1;
                                            max = (int) Double.parseDouble(tlm10.get(j1).get("count").toString());
                                        }
                                    }
                                    int pos3 = -1;
                                    for (j3 = 0; j3 < tempstringlist1.size(); j3++) {
                                        if (tlm10.get(pos).get("name").toString().equals(tempstringlist1.get(j3))) {
                                            pos3 = l;
                                        }
                                    }
                                    if (pos3 == -1) {
                                        tempstringlist1.add(tlm10.get(pos).get("name").toString());
                                        tlm10.remove(pos);
                                    }

                                }
                                recommendedlistmap = new ArrayList<>();


                                for (int i2 = 0; i2 < categorieslistmap.size(); i2++) {
                                    final DatabaseReference tempdb = _database.getReference("Categories/" + String.valueOf(i2 + 1) + "/" + categorieslistmap.get(i2).get("fbn").toString());
                                    tempdb.addListenerForSingleValueEvent(new ValueEventListener() {

                                        public void onDataChange(DataSnapshot _dataSnapshot) {
                                            templistmap5 = new ArrayList<>();
                                            try {
                                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                                };
                                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                                    templistmap5.add(_map);
                                                }
                                            } catch (Exception _e) {
                                                _e.printStackTrace();
                                            }
                                            for (int j2 = 0; j2 < templistmap5.size(); j2++) {
                                                for (l2 = 0; l2 < tempstringlist1.size(); l2++) {
                                                    if (tempstringlist1.get(l2).equals(templistmap5.get(j2).get("name").toString())) {
                                                        tempmap1 = new HashMap<>();
                                                        tempmap1.put("name", templistmap5.get(j2).get("name").toString());
                                                        tempmap1.put("price", templistmap5.get(j2).get("price").toString());
                                                        tempmap1.put("url",templistmap5.get(j2).get("url").toString());
                                                        tempmap1.put("rank", String.valueOf(l2));
                                                        if (templistmap5.get(j2).containsKey("quantity")) {
                                                            tempmap1.put("quantity", templistmap5.get(j2).get("quantity"));
                                                        }
                                                        int pos2 = -1;
                                                        for (l3 = 0; l3 < recommendedlistmap.size(); l3++) {
                                                            if (tempmap1.get("name").toString().equals(recommendedlistmap.get(l3).get("name").toString())) {
                                                                pos2 = l;
                                                            }
                                                        }
                                                        if (pos2 == -1) {
                                                            recommendedlistmap.add(tempmap1);
                                                        }
                                                    }
                                                }
                                            }
                                            slideAdapter1 = new SlideAdapter1(homepage.this, recommendedlistmap);
                                            recommendedslider.setAdapter(slideAdapter1);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError _databaseError) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError _databaseError) {
                            }
                        });
                    }
                }
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
    public void onBackPressed() {

        if ((odlist.getVisibility() == View.VISIBLE) && (!(_drawer.isDrawerOpen(GravityCompat.START)))) {
            odlist.setVisibility(View.GONE);
            cattypetext.setVisibility(View.GONE);
            trendingslider.setVisibility(View.VISIBLE);
            trobutton.setVisibility(View.VISIBLE);
            reobutton.setVisibility(View.VISIBLE);
            categorylistview.setVisibility(View.VISIBLE);
            recommendedslider.setVisibility(View.VISIBLE);
            categorytext.setVisibility(View.VISIBLE);
            uo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                    ordermap = new ArrayList<>();
                    try {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                        };
                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                            HashMap<String, Object> _map = _data.getValue(_ind);
                            ordermap.add(_map);
                        }
                    } catch (Exception _e) {
                        _e.printStackTrace();
                    }
                    trerecdef();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (searchflag==1) {
                cartlinear.setVisibility(View.VISIBLE);
                searchflag=0;
            }
        } else {
            if (!(_drawer.isDrawerOpen(GravityCompat.START))) {
                super.onBackPressed();
            }
        }
        if ((_drawer.isDrawerOpen(GravityCompat.START))) {
            _drawer.closeDrawer(GravityCompat.START);
        }
        if ((cartflag == 1) && !(_drawer.isDrawerOpen(GravityCompat.START))) {
            menulinear.setVisibility(View.GONE);
            cartflag = 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                categorylistview.setVisibility(View.VISIBLE);
                odlist.setVisibility(View.GONE);
                cattypetext.setVisibility(View.GONE);
                categorytext.setVisibility(View.VISIBLE);
                cartlinear.setVisibility(View.VISIBLE);
                cartcounttext.setText(String.valueOf(0));
                categorylistview.setAdapter(new CategorylistviewAdapter(categorieslistmap));
                setListViewHeightBasedOnItems(categorylistview);
                menulinear.setVisibility(View.GONE);
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                        ordermap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                ordermap.add(_map);
                            }
                        } catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        trerecdef();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }
        if(requestCode==11)
        {
            if(resultCode==RESULT_OK)
            {
                if (getIntent().getIntExtra("pp", 0) == 12) {
                    myordersactivity.putExtra("ac", "lo");
                    setResult(RESULT_OK, myordersactivity);
                } else {
                    if (getIntent().getIntExtra("pp", 0) == 13) {
                        myaccountactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK, myaccountactivity);
                    } else {
                        if (getIntent().getIntExtra("pp", 0) == 11) {
                            myaddressessactivity.putExtra("ac", "lo");
                            setResult(RESULT_OK, myaddressessactivity);
                        }
                        else{
                            if (getIntent().getIntExtra("pp",0)==0)
                            {
                                mainintent.putExtra("key1","logout");
                                setResult(RESULT_OK,mainintent);
                            }
                        }
                    }
                }
                homepage.this.finish();
            }
        }
        if (requestCode==12)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("ac").equals("lo"))
                {
                    mainintent.putExtra("key1","logout");
                    setResult(RESULT_OK,mainintent);
                    homepage.this.finish();
                }
            }
        }
        if(requestCode==13)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getStringExtra("ac").equals("lo"))
                {
                    mainintent.putExtra("key1","logout");
                    setResult(RESULT_OK,mainintent);
                    homepage.this.finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

}

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initialize();
        initializeLogic();
    }
    private void initialize() {

        _drawer= findViewById(R.id._drawer);
        _toggle=new ActionBarDrawerToggle(homepage.this,_drawer,R.string.open,R.string.close);
        _drawer.addDrawerListener(_toggle);
        _toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderactivity.setClass(homepage.this,orderactivity.class);
        searchactivity.setClass(homepage.this,searchactivity.class);
        myaccountactivity.setClass(homepage.this,myaccountactivity.class);
        myaddressessactivity.setClass(homepage.this,myaddressesactivity.class);
        myordersactivity.setClass(homepage.this,myordersactivity.class);

        linear1 = findViewById(R.id.linear1);
        searchlinear = findViewById(R.id.searchlinear);
        linear2 = findViewById(R.id.linear2);
        menulinear = findViewById(R.id.menulinear);
        cartlinear = findViewById(R.id.cartlinear);
        cartimage = findViewById(R.id.cartimage);
        cartcounttext = findViewById(R.id.cartcounttext);
        mainscroll = findViewById(R.id.mainscroll);
        mainscrolllinear = findViewById(R.id.mainscrolllinear);
        profilename= findViewById(R.id.profilename);
        profileemail= findViewById(R.id.profileemail);
        cartcounttext=findViewById(R.id.cartcounttext);
        recommendedtext=findViewById(R.id.recommendedtext);
        trendingtext=findViewById(R.id.trendingtext);
        homebutton=findViewById(R.id.homebutton);
        myordersbutton=findViewById(R.id.myordersbutton);
        myaddressbutton=findViewById(R.id.myaddressbutton);
        myaccountbutton=findViewById(R.id.myaccountbutton);
        logoutbutton=findViewById(R.id.logoutbutton);
        categorylistview=findViewById(R.id.categorylistview);
        odlist=findViewById(R.id.odlist);
        categorytext=findViewById(R.id.categorytext);
        cattypetext=findViewById(R.id.cattypetext);
        orderbutton=findViewById(R.id.orderbutton);
        trendingslider=findViewById(R.id.trendingslider);
        recommendedslider=findViewById(R.id.recommendedslider);
        searchbarview=findViewById(R.id.searchbarview);
        trendinglinear=findViewById(R.id.trendinglinear);
        trobutton=findViewById(R.id.trobutton);
        reobutton=findViewById(R.id.reobutton);

        categorylistview = findViewById(R.id.categorylistview);

        odlist.setVisibility(View.GONE);
        cattypetext.setVisibility(View.GONE);
        recommendedtext.setVisibility(View.GONE);
        trendingtext.setVisibility(View.GONE);
        menulinear.setVisibility(View.GONE);
        searchbarview.setFocusable(false);
        searchbarview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchactivity.putExtra("ui",udindex);
                searchactivity.putExtra("dk",dk);
                startActivityForResult(searchactivity,1);
            }
        });
        searchbarview.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbarview.setIconified(true);
                searchbarview.clearFocus();
                searchactivity.putExtra("ui",udindex);
                searchactivity.putExtra("dk",dk);
                startActivityForResult(searchactivity,1);
            }
        });

        myordersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myordersactivity.putExtra("pp",10);
                startActivityForResult(myordersactivity,12);
            }
        });

        myaddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaddressessactivity.putExtra("pp",10);
                startActivityForResult(myaddressessactivity,11);

            }
        });

        myaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myaccountactivity.putExtra("pp",10);
                startActivityForResult(myaccountactivity,13);
            }
        });


        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getIntExtra("pp", 0) == 12) {
                    myordersactivity.putExtra("ac", "lo");
                    setResult(RESULT_OK, myordersactivity);
                } else {
                    if (getIntent().getIntExtra("pp", 0) == 13) {
                        myaccountactivity.putExtra("ac", "lo");
                        setResult(RESULT_OK, myaccountactivity);
                    } else {
                        if (getIntent().getIntExtra("pp", 0) == 11) {
                            myaddressessactivity.putExtra("ac", "lo");
                            setResult(RESULT_OK, myaddressessactivity);
                        }
                        else{
                            if (getIntent().getIntExtra("pp",0)==0)
                            {
                                mainintent.putExtra("key1","logout");
                                setResult(RESULT_OK,mainintent);
                            }
                        }
                    }
                }
                homepage.this.finish();
            }
        });

        categorylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position=i;

                if(categorieslistmap.get(position).containsKey(categorieslistmap.get(position).get("fbn").toString())) {
                    trendingslider.setVisibility(View.GONE);
                    recommendedslider.setVisibility(View.GONE);
                    trobutton.setVisibility(View.GONE);
                    reobutton.setVisibility(View.GONE);
                    categorylistview.setVisibility(View.GONE);
                    categorytext.setVisibility(View.GONE);
                    odlist.setVisibility(View.VISIBLE);
                    cattypetext.setVisibility(View.VISIBLE);
                    recommendedtext.setVisibility(View.GONE);
                    trendingtext.setVisibility(View.GONE);
                    cattypetext.setText(categorieslistmap.get(position).get("Cname").toString());
                    dk = "Categories/" + String.valueOf((long) (position + 1)) + "/" + categorieslistmap.get(position).get("fbn").toString();
                    final DatabaseReference cpl = _database.getReference(dk);
                    ChildEventListener _cpl_childlistener;
                    _cpl_childlistener= new ChildEventListener() {
                        @Override
                        public void  onChildAdded(DataSnapshot _param1, String _param2) {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            final String _childKey = _param1.getKey();
                            final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                            cpl.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                                    cproductlistmap = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                        };
                                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            cproductlistmap.add(_map);
                                        }
                                    } catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onChildChanged(DataSnapshot _param1, String _param2) {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            final String _childKey = _param1.getKey();
                            final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                            cpl.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                                    cproductlistmap = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                        };
                                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            cproductlistmap.add(_map);
                                        }
                                    } catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onChildMoved(DataSnapshot _param1, String _param2) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot _param1) {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            final String _childKey = _param1.getKey();
                            final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                            cpl.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                                    cproductlistmap = new ArrayList<>();
                                    try {
                                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                        };
                                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                            HashMap<String, Object> _map = _data.getValue(_ind);
                                            cproductlistmap.add(_map);
                                        }
                                    } catch (Exception _e) {
                                        _e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError _param1) {
                            final int _errorCode = _param1.getCode();
                            final String _errorMessage = _param1.getMessage();

                        }
                    };
                    cpl.addChildEventListener(_cpl_childlistener);
                    cpl.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                            cproductlistmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                                };
                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                    cproductlistmap.add(_map);
                                }
                            } catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            Adapter adapter1=new odlistviewAdapter(cproductlistmap);
                            odlist.setAdapter(new odlistviewAdapter(cproductlistmap));
                            setListViewHeightBasedOnItems(odlist);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        trobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trendingslider.setVisibility(View.GONE);
                recommendedslider.setVisibility(View.GONE);
                trobutton.setVisibility(View.GONE);
                reobutton.setVisibility(View.GONE);
                categorylistview.setVisibility(View.GONE);
                categorytext.setVisibility(View.GONE);
                odlist.setVisibility(View.VISIBLE);
                cattypetext.setVisibility(View.VISIBLE);
                recommendedtext.setVisibility(View.GONE);
                trendingtext.setVisibility(View.GONE);
                cattypetext.setText("Trending");
                templistmap6=new ArrayList<>();
                templistmap6=(ArrayList)trendinglistmap.clone();
                templistmap7=new ArrayList<>();
                int sz;
                if(templistmap6.size()<10)
                    sz=templistmap6.size();
                else
                    sz=10;
                for(int ijk3=0;ijk3<sz;ijk3++)
                {
                    tempmap2=new HashMap<>();
                    int max=(int)Double.parseDouble(templistmap6.get(0).get("rank").toString());
                    int pos12=0;
                    for(int ijk4=0;ijk4<templistmap6.size();ijk4++)
                    {
                        if((int)Double.parseDouble(templistmap6.get(ijk4).get("rank").toString())<=max)
                        {
                            max=(int)Double.parseDouble(templistmap6.get(ijk4).get("rank").toString());
                            pos12=ijk4;
                        }

                    }
                    tempmap2=templistmap6.get(pos12);
                    templistmap6.remove(pos12);
                    templistmap7.add(tempmap2);

                }
                odlist.setAdapter(new odlistviewAdapter(templistmap7));
                setListViewHeightBasedOnItems(odlist);
            }
        });


        reobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trendingslider.setVisibility(View.GONE);
                recommendedslider.setVisibility(View.GONE);
                trobutton.setVisibility(View.GONE);
                reobutton.setVisibility(View.GONE);
                categorylistview.setVisibility(View.GONE);
                categorytext.setVisibility(View.GONE);
                odlist.setVisibility(View.VISIBLE);
                cattypetext.setVisibility(View.VISIBLE);
                recommendedtext.setVisibility(View.GONE);
                trendingtext.setVisibility(View.GONE);
                cattypetext.setText("Recommended");
                templistmap6=new ArrayList<>();
                templistmap6=(ArrayList)recommendedlistmap.clone();
                templistmap7=new ArrayList<>();
                int sz;
                if(templistmap6.size()<10)
                    sz=templistmap6.size();
                else
                    sz=10;
                for(int ijk3=0;ijk3<sz;ijk3++)
                {
                    tempmap2=new HashMap<>();
                    int max=(int)Double.parseDouble(templistmap6.get(0).get("rank").toString());
                    int pos12=0;
                    for(int ijk4=0;ijk4<templistmap6.size();ijk4++)
                    {
                        if((int)Double.parseDouble(templistmap6.get(ijk4).get("rank").toString())<=max)
                        {
                            max=(int)Double.parseDouble(templistmap6.get(ijk4).get("rank").toString());
                            pos12=ijk4;
                        }

                    }
                    tempmap2=templistmap6.get(pos12);
                    templistmap6.remove(pos12);
                    templistmap7.add(tempmap2);

                }
                odlist.setAdapter(new odlistviewAdapter(templistmap7));
                setListViewHeightBasedOnItems(odlist);
            }
        });

        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartlistmap.size()>0)
                startActivityForResult(orderactivity,2);
                else
                    Toast.makeText(homepage.this,"Cannot place order when cart is empty",Toast.LENGTH_LONG).show();
            }
        });

        cartlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                odlist.setVisibility(View.VISIBLE);
                odlist.setAdapter(new odlistviewAdapter(cartlistmap));
                setListViewHeightBasedOnItems(odlist);
                recommendedslider.setVisibility(View.GONE);
                trendingslider.setVisibility(View.GONE);
                trobutton.setVisibility(View.GONE);
                reobutton.setVisibility(View.GONE);
                recommendedtext.setVisibility(View.GONE);
                trendingtext.setVisibility(View.GONE);
                categorytext.setVisibility(View.GONE);
                categorylistview.setVisibility(View.GONE);
                menulinear.setVisibility(View.VISIBLE);
                cattypetext.setVisibility(View.GONE);
                cartflag=1;
            }
        });



        _Categories_child_listener = new ChildEventListener() {
            @Override
            public void  onChildAdded(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                Categories.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        categorieslistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                categorieslistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        categorylistview.setAdapter(new CategorylistviewAdapter(categorieslistmap));
                        setListViewHeightBasedOnItems(categorylistview);
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot _param1, String _param2) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                Categories.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        categorieslistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                categorieslistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        categorylistview.setAdapter(new CategorylistviewAdapter(categorieslistmap));
                        setListViewHeightBasedOnItems(categorylistview);
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }

            @Override
            public void onChildMoved(DataSnapshot _param1, String _param2) {

            }

            @Override
            public void onChildRemoved(DataSnapshot _param1) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = _param1.getKey();
                final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                Categories.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        categorieslistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                categorieslistmap.add(_map);
                            }
                        }
                        catch (Exception _e) {
                            _e.printStackTrace();
                        }
                        categorylistview.setAdapter(new CategorylistviewAdapter(categorieslistmap));
                        setListViewHeightBasedOnItems(categorylistview);
                    }
                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError _param1) {
                final int _errorCode = _param1.getCode();
                final String _errorMessage = _param1.getMessage();

            }
        };
        Categories.addChildEventListener(_Categories_child_listener);

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
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        ordermap= new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                ordermap.add(_map);
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
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        ordermap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                ordermap.add(_map);
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
                uo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        ordermap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                ordermap.add(_map);
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
        uo.addChildEventListener(_uo_child_listener);

    }
    private void initializeLogic(){
        uo.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                ordermap=new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        ordermap.add(_map);
                    }
                }
                catch (Exception _e) {
                    _e.printStackTrace();
                }
                trerecdef();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Categories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot _dataSnapshot) {
                categorieslistmap = new ArrayList<>();
                try {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        categorieslistmap.add(_map);
                    }
                }
                catch (Exception _e) {
                    _e.printStackTrace();
                }
                categorylistview.setAdapter(new CategorylistviewAdapter(categorieslistmap));
                setListViewHeightBasedOnItems(categorylistview);
            }
            @Override
            public void onCancelled(DatabaseError _databaseError) {
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
                //cartcounttext.setText(String.valueOf((long)(udindex)));
                pname=userdatalistmap.get(udindex).get("fullname").toString();
                profilename.setText(pname);
                pemail=userdatalistmap.get(udindex).get("email").toString();
                profileemail.setText(pemail);
                    final DatabaseReference ucartdata = _database.getReference("userdata/" + String.valueOf(udindex + 1) + "/cart");
                    final ChildEventListener _ucartdata_Child_Event_Listener;
                    ucartdata.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot _dataSnapshot) {
                            cartlistmap = new ArrayList<>();
                            try {
                                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                    HashMap<String, Object> _map = _data.getValue(_ind);
                                    cartlistmap.add(_map);
                                }
                            }
                            catch (Exception _e) {
                                _e.printStackTrace();
                            }
                            cartcounttext.setText(String.valueOf(cartlistmap.size()));

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



    public class CategorylistviewAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;
        public CategorylistviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
                _v = _inflater.inflate(R.layout.categorylist, null);
            }

            final TextView textview1 = _v.findViewById(R.id.textview1);
            final ImageView imageView = _v.findViewById(R.id.imageView);

            textview1.setText(categorieslistmap.get(_position).get("Cname").toString());
            imageView.setImageResource(R.drawable.ic_menu_send);

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
            final LinearLayout addmodifylinear=_v.findViewById(R.id.addmodifylinear);

            modifyquantitylinear.setVisibility(View.GONE);
            addbutton.setVisibility(View.VISIBLE);

            final ProgressBar progressBar1 =new ProgressBar(homepage.this,null,android.R.attr.progressBarStyle);
            addmodifylinear.addView(progressBar1);
            progressBar1.setVisibility(View.GONE);

            final DatabaseReference ucartdata = _database.getReference("userdata/" + String.valueOf(udindex + 1) + "/cart");
                final ChildEventListener _ucartdata_Child_Event_Listener;
                ucartdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot _dataSnapshot) {
                        cartlistmap = new ArrayList<>();
                        try {
                            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                HashMap<String, Object> _map = _data.getValue(_ind);
                                cartlistmap.add(_map);
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
                _ucartdata_Child_Event_Listener=new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot _param1, @Nullable String _param2) {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        final String _childKey = _param1.getKey();
                        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
                        ucartdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                cartlistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        cartlistmap.add(_map);
                                        cartcounttext.setText(String.valueOf(cartlistmap.size()));
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
                        ucartdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                cartlistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        cartlistmap.add(_map);
                                        cartcounttext.setText(String.valueOf(cartlistmap.size()));
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
                        ucartdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot _dataSnapshot) {
                                cartlistmap = new ArrayList<>();
                                try {
                                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                                    for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                                        HashMap<String, Object> _map = _data.getValue(_ind);
                                        cartlistmap.add(_map);
                                        cartcounttext.setText(String.valueOf(cartlistmap.size()));
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
                        cartcounttext.setText(String.valueOf(cartlistmap.size()));
                    }
                };
                ucartdata.addChildEventListener(_ucartdata_Child_Event_Listener);


                for (int j = 0; j < cartlistmap.size(); j++) {
                    if (_data.get(_position).get("name").toString().equals(cartlistmap.get(j).get("name").toString())) {
                        addbutton.setVisibility(View.GONE);
                        modifyquantitylinear.setVisibility(View.VISIBLE);
                        quantitynotext.setText(cartlistmap.get(j).get("quantityno").toString());
                    }
                }


            productnametext.setText(_data.get(_position).get("name").toString());
            costtext.setText("Cost:"+_data.get(_position).get("price").toString());
            productimage.setImageResource(R.drawable.ic_launcher_background);
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

            addbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbutton.setVisibility(View.GONE);
                    modifyquantitylinear.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.VISIBLE);
                    tempmap=new HashMap<>();
                    tempmap.put("name",_data.get(_position).get("name").toString());
                    tempmap.put("price",String.valueOf(((int)(Double.parseDouble(_data.get(_position).get("price").toString())))));
                    tempmap.put("url",_data.get(_position).get("url").toString());
                    if(_data.get(_position).containsKey("quantity")) {

                        tempmap.put("quantity",_data.get(_position).get("quantity").toString());
                    }
                    tempmap.put("quantityno",String.valueOf((int)Double.parseDouble(quantitynotext.getText().toString())));
                    ucartdata.child(String.valueOf((cartlistmap.size() +1))).updateChildren(tempmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBar1.setVisibility(View.GONE);
                            modifyquantitylinear.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar1.setVisibility(View.GONE);
                            addbutton.setVisibility(View.VISIBLE);
                        }
                    });


                }
            });

            plusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateflag=0;
                    modifyquantitylinear.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.VISIBLE);
                    for(int j = 0; j< cartlistmap.size(); j++)
                    {
                        if(cartlistmap.get(j).get("name").equals(_data.get(_position).get("name")))
                        {
                            tempmap=new HashMap<>();
                            tempmap.put("name",cartlistmap.get(j).get("name"));
                            tempmap.put("price",String.valueOf(((int)(Double.parseDouble(_data.get(_position).get("price").toString())))*((int)(Double.parseDouble(cartlistmap.get(j).get("quantityno").toString()))+1)));
                            tempmap.put("url",_data.get(j).get("url").toString());
                            tempmap.put("quantityno",String.valueOf(((int)Double.parseDouble(cartlistmap.get(j).get("quantityno").toString()))+1));
                            if(_data.get(_position).containsKey("quantity")) {
                                quantitytext.setVisibility(View.VISIBLE);
                                tempmap.put("quantity",_data.get(_position).get("quantity").toString());
                            }
                            else
                            {
                                quantitytext.setVisibility(View.GONE);
                            }
                            ucartdata.child(String.valueOf(j+1)).updateChildren(tempmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar1.setVisibility(View.GONE);
                                    modifyquantitylinear.setVisibility(View.VISIBLE);
                                    quantitynotext.setText(String.valueOf((long) (((int) (Double.parseDouble(quantitynotext.getText().toString()))) + 1)));

                                }
                            });
                        }
                    }
                }
            });
            minusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((int)(Double.parseDouble(quantitynotext.getText().toString())))>1) {
                        modifyquantitylinear.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.VISIBLE);
                        updateflag=0;
                        for(int j = 0; j< cartlistmap.size(); j++)
                        {
                            if(cartlistmap.get(j).get("name").equals(_data.get(_position).get("name")))
                            {
                                tempmap=new HashMap<>();
                                tempmap.put("name",cartlistmap.get(j).get("name"));
                                tempmap.put("price",String.valueOf(((int)Double.parseDouble(_data.get(_position).get("price").toString()))*((int)(Double.parseDouble(cartlistmap.get(j).get("quantityno").toString()))-1)));
                                tempmap.put("url",_data.get(j).get("url").toString());
                                tempmap.put("quantityno",String.valueOf(((int)Double.parseDouble(cartlistmap.get(j).get("quantityno").toString()))-1));
                                if(_data.get(_position).containsKey("quantity")) {
                                    quantitytext.setVisibility(View.VISIBLE);
                                    tempmap.put("quantity",_data.get(_position).get("quantity").toString());
                                }
                                else
                                {
                                    quantitytext.setVisibility(View.GONE);
                                }
                                ucartdata.child(String.valueOf(j+1)).updateChildren(tempmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar1.setVisibility(View.GONE);
                                        modifyquantitylinear.setVisibility(View.VISIBLE);
                                        quantitynotext.setText(String.valueOf((long) (((int) (Double.parseDouble(quantitynotext.getText().toString()))) - 1)));
                                    }
                                });
                            }

                        }
                    }
                    else
                    {
                        modifyquantitylinear.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.VISIBLE);
                        int r1=0;
                        for(int j = 0; j< cartlistmap.size(); j++)
                        {
                            if(cartlistmap.get(j).get("name").equals(_data.get(_position).get("name")))
                            {
                               r1=j;
                            }
                        }
                        if((r1==0)&&(cartlistmap.size()==1))
                        {
                            ucartdata.child("1").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar1.setVisibility(View.GONE);
                                    addbutton.setVisibility(View.VISIBLE);
                                    cartcounttext.setText("0");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar1.setVisibility(View.GONE);
                                    modifyquantitylinear.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        else {
                            updateflag=0;
                            if(r1!=cartlistmap.size()-1) {
                                for (int j = r1 + 1; j < (cartlistmap).size(); j++) {
                                    tempmap = new HashMap<>();
                                    tempmap.put("name", cartlistmap.get(j).get("name").toString());
                                    tempmap.put("price", cartlistmap.get(j).get("price").toString());
                                    tempmap.put("url", cartlistmap.get(j).get("url").toString());
                                    tempmap.put("quantityno", cartlistmap.get(j).get("quantityno").toString());
                                    if (cartlistmap.get(j).containsKey("quantity")) {
                                        tempmap.put("quantity", cartlistmap.get(j).get("quantity").toString());
                                    }
                                    ucartdata.child(String.valueOf(j)).updateChildren(tempmap).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            updateflag = 1;
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if (cartlistmap.get(cartlistmap.size() - 2).equals(cartlistmap.get(cartlistmap.size() - 1))) {
                                                progressBar1.setVisibility(View.GONE);
                                                ucartdata.child(String.valueOf(cartlistmap.size())).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        addbutton.setVisibility(View.VISIBLE);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        modifyquantitylinear.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                            else
                            {
                                ucartdata.child(String.valueOf(cartlistmap.size())).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar1.setVisibility(View.GONE);
                                        addbutton.setVisibility(View.VISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar1.setVisibility(View.GONE);
                                        modifyquantitylinear.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
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
                th=th+item.getMeasuredHeight();
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