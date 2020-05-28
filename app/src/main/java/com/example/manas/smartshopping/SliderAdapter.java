package com.example.manas.smartshopping;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class SliderAdapter extends PagerAdapter{

    ArrayList<HashMap<String, Object>> _data=new ArrayList<>();
    ArrayList<HashMap<String, Object>> _data1=new ArrayList<>();
    HashMap<String,Object> _tm=new HashMap<>();
    int ijk1=0,ijk2=0;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context,ArrayList<HashMap<String,Object>> _arr){
        _data1=(ArrayList)_arr.clone();
        int sz;
        if(_data1.size()<10)
            sz=_data1.size();
        else
            sz=10;
        for(ijk1=0;ijk1<sz;ijk1++)
        {
            _tm=new HashMap<>();
            int max=(int)Double.parseDouble(_data1.get(0).get("rank").toString());
            int pos10=0;
            for(ijk2=0;ijk2<_data1.size();ijk2++)
            {
                if((int)Double.parseDouble(_data1.get(ijk2).get("rank").toString())<=max)
                {
                    max=(int)Double.parseDouble(_data1.get(ijk2).get("rank").toString());
                    pos10=ijk2;
                }

            }
            _tm=_data1.get(pos10);
            _data1.remove(pos10);
            _data.add(_tm);

        }
        this.context=context;
    }

    @Override
    public int getCount() {
        if(_data.size()<10) {
            return _data.size();
        }else
        {
            return 10;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.trendingslidelayout,null);
        ImageView imageView3=view.findViewById(R.id.imageView3);
        TextView textView10=view.findViewById(R.id.textView10);
        TextView textView11=view.findViewById(R.id.textView11);
        TextView textView12=view.findViewById(R.id.textView12);
        imageView3.setImageResource(R.drawable.ic_launcher_background);
        textView10.setText(_data.get(position).get("name").toString());
        Glide.with(context).load(_data.get(position).get("url").toString()).into(imageView3);
        Glide.with(context).load(_data.get(position).get("url").toString()).into(imageView3);

        if(_data.get(position).containsKey("quantity"))
        {
            textView11.setVisibility(View.VISIBLE);
            textView11.setText("Quantity:"+_data.get(position).get("quantity").toString());
        }
        else{
            textView11.setVisibility(View.GONE);
        }

        textView12.setText("Cost:"+_data.get(position).get("price").toString());


        ViewPager vp =(ViewPager) container;
        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp =(ViewPager) container;
        View view=(View) object;
        vp.removeView(view);
    }
}
