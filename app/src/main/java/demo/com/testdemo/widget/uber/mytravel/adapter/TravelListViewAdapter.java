package demo.com.testdemo.widget.uber.mytravel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import demo.com.testdemo.R;
import demo.com.testdemo.widget.uber.mytravel.model.TravelModel;
import demo.com.testdemo.widget.uber.mytravel.view.custom.TravelItemParent;

/**
 * Created by ludexiang on 16/8/19.
 */
public class TravelListViewAdapter <T extends TravelModel> extends BaseAdapter {
    private Context mContext;
    private List<T> mData;
    private LayoutInflater mInflater;

    public TravelListViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData != null ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.ub_my_travel_item_layout, null);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.travelOrderStatus = (TextView) convertView.findViewById(R.id.travel_order_status);
        holder.itemParent = (TravelItemParent) convertView.findViewById(R.id.travel_item_parent);
        holder.itemParent.setModel(mData.get(position));
        if (position == 0) {
            holder.travelOrderStatus.setVisibility(View.VISIBLE);
        } else {
            holder.travelOrderStatus.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        // 未完成订单 或 已完成订单
        private TextView travelOrderStatus;
        private TravelItemParent itemParent;
    }
}
