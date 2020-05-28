package com.example.manas.smartshopping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class searchactivity extends AppCompatActivity {

    private ArrayList<HashMap<String,Object>> templistmap=new ArrayList<>();
    private ArrayList<HashMap<String,Object>> taglistmap =new ArrayList<>();
    private ArrayList<HashMap<String,Object>> categorieslistmap=new ArrayList<>();
    private HashMap<String,Object> tempmap1=new HashMap<>();

    private android.support.v7.widget.SearchView searchviewbar;
    private ListView searchlist;
    private ListView searchdesclist;


    private FirebaseDatabase _database = FirebaseDatabase.getInstance();

    private DatabaseReference category =_database.getReference();
    private ChildEventListener _category_listener;

    private int udindex=0;
    private String dk="";
    private int counter1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        Initialize();
        InitializeLogic();
    }

    public void Initialize() {
        searchviewbar = findViewById(R.id.searchbarview);
        searchlist = findViewById(R.id.searchlist);
        searchdesclist =findViewById(R.id.searchdesclist);

        searchdesclist.setVisibility(View.GONE);

        udindex = getIntent().getIntExtra("ui", 0);
        dk = getIntent().getStringExtra("dk");

        searchviewbar.setIconifiedByDefault(false);

        searchviewbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                

                return false;
            }
        });


    }

    public void InitializeLogic() {
        if (dk != "") {
            taglistmap = new ArrayList<>();
            templistmap = new ArrayList<>();
            DatabaseReference tempdata = _database.getReference(dk);
            tempdata.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {

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
                    for (int j10 = 0; j10 < templistmap.size(); j10++) {
                        tempmap1 = new HashMap<>();
                        counter1 = 1;
                        tempmap1.put("name", templistmap.get(j10).get("name").toString());
                        if (templistmap.get(j10).containsKey("tags")) {
                            String temptag = templistmap.get(j10).get("tags").toString();
                            int wc = 0;
                            for (int l10 = 0; l10 < temptag.length(); l10++) {
                                if (temptag.substring(l10 + 1, l10 + 2).equals(",") || temptag.substring(l10 + 1, l10 + 2).equals(".")) {
                                    tempmap1.put(String.valueOf(counter1), temptag.substring(wc, l10));
                                    counter1++;
                                    wc = l10 + 1;
                                }
                            }
                        }
                        taglistmap.add(tempmap1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            taglistmap = new ArrayList<>();
            for (int i10 = 0; i10 < categorieslistmap.size(); i10++) {
                final DatabaseReference tempdb = _database.getReference("Categories/" + String.valueOf(i10 + 1) + "/" + categorieslistmap.get(i10).get("fbn").toString());
                tempdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
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
                        for (int j10 = 0; j10 < templistmap.size(); j10++) {
                            tempmap1 = new HashMap<>();
                            counter1 = 1;
                            tempmap1.put("name", templistmap.get(j10).get("name").toString());
                            if (templistmap.get(j10).containsKey("tags")) {
                                String temptag = templistmap.get(j10).get("tags").toString();
                                int wc = 0;
                                for (int l10 = 0; l10 < temptag.length(); l10++) {
                                    if (temptag.substring(l10 + 1, l10 + 2).equals(",") || temptag.substring(l10 + 1, l10 + 2).equals(".")) {
                                        tempmap1.put(String.valueOf(counter1), temptag.substring(wc, l10));
                                        counter1++;
                                        wc = l10 + 1;
                                    }
                                }
                            }
                            taglistmap.add(tempmap1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError _databaseError) {
                    }
                });
            }
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
