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

import java.util.List;



/**
 * Created by wanyu on 2017/3/2.
 */

public class MyWheelAdapter50 extends AbstractWheelTextAdapter implements WheelView.WheelCurrent{
    private String title;
    private List<String>list;
    private int current;
    public MyWheelAdapter50(Context context, List<String>list,String title) {
        super(context);
        this.title=title;
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.list=list;
    }
    @Override
    protected CharSequence getItemText(int index) {
        if (index >= 0 && index < list.size()) {
            String item = list.get(index);
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
            View vi= LayoutInflater.from(context).inflate(R.layout.wheel50,null);
            TextView textView = (TextView) vi.findViewById(R.id.texta);
            if (textView != null) {
                CharSequence text = getItemText(index);
                if (text == null) {
                    text = "";
                }
//                if (index==current){
//                    text=text+title;
//                }
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
        return list==null?0:list.size();
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
