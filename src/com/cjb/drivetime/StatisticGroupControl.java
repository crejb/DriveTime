package com.cjb.drivetime;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticGroupControl extends LinearLayout {
	
	public StatisticGroupControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Options, 0, 0);
        String valueText = a.getString(R.styleable.Options_itemValue);
        String descriptionText = a.getString(R.styleable.Options_itemDescription);
        a.recycle();
        
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.statistic_group, this, true);
    }
	
	public void setItemValue(String valueText){
		((TextView)findViewById(R.id.text_statistic_value)).setText(valueText);
	}
	
	public void setItemDescription(String descriptionText){
		((TextView)findViewById(R.id.text_statistic_description)).setText(descriptionText);
	}
}
