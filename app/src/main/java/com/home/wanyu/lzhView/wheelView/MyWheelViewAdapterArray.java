package com.home.wanyu.lzhView.wheelView;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.wanyu.R;
import com.home.wanyu.lzhView.wheelView.AbstractWheelTextAdapter;
import com.home.wanyu.lzhView.wheelView.WheelView;
import com.home.wanyu.lzhView.wheelView.WheelViewAdapter;

import java.util.List;

/**
 * Created by wanyu on 2017/5/8.
 */
//array数据源
public class MyWheelViewAdapterArray extends AbstractWheelTextAdapter implements WheelView.WheelCurrent{
    private String[]str;
    private int current;
    public MyWheelViewAdapterArray(Context context,String[]str) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.str=str;
    }
    @Override
    protected CharSequence getItemText(int index) {
        if (index >= 0 && index < str.length) {
            String item = str[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {
//            if (convertView == null) {
//                convertView = getView(itemResourceId, parent);
//                convertView.setMinimumHeight((int)context.getResources().getDimension(R.dimen.wheelViewHeight));
//            }
            View vi= LayoutInflater.from(context).inflate(R.layout.text,null);
            TextView textView = (TextView) vi.findViewById(R.id.texta);
            if (textView != null) {
                CharSequence text = getItemText(index);
                if (text == null) {
                    text = "";
                }
                textView.setText(text);
                if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                    configureTextView(textView,index);
                }
            }
            return vi;
        }
        return null;
    }


    protected void configureTextView(TextView view,int pos) {
        if (current==pos){
            view.setTextSize(14);
            view.setTextColor(context.getResources().getColor(R.color.titlecolor3));
        }
        else {
            view.setTextSize(14);
            view.setTextColor(context.getResources().getColor(R.color.colorc));
        }
        view.setGravity(Gravity.CENTER);

//        view.setHeight((int)context.getResources().getDimension(R.dimen.wheelViewHeight));
        view.setEllipsize(TextUtils.TruncateAt.END);
        view.setLines(1);
//        view.setCompoundDrawablePadding(20);
//        view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }
    private TextView getTextView(View view, int textResource) {
        TextView text = null;
        try {
            if (textResource == NO_RESOURCE && view instanceof TextView) {
                text = (TextView) view;
            } else if (textResource != NO_RESOURCE) {
                text = (TextView) view.findViewById(textResource);
            }
        } catch (ClassCastException e) {
            Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "AbstractWheelAdapter requires the resource ID to be a TextView", e);
        }

        return text;
    }


    @Override
    public int getItemsCount() {
        return str==null?0:str.length;
    }

    @Override
    public void sendCurrent(int id) {
        this.current=id;
        notifyDataChangedEvent();
    }


    private View getView(int resource, ViewGroup parent) {
        switch (resource) {
            case NO_RESOURCE:
                return null;
            case TEXT_VIEW_ITEM_RESOURCE:
                return new TextView(context);
            default:
                return inflater.inflate(resource, parent, false);
        }
    }
}
