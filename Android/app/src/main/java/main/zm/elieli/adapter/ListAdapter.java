package main.zm.elieli.adapter;

/**
 * Created by zm on 2018/9/3.
 */


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;


public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MainActivity mContext;
    public JSONArray listdate=new JSONArray();
    private OnItemClickListener mOnItemClickListener;

    public void setListdate(JSONArray listdate) {
        this.listdate = listdate;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ListAdapter(Context mContext) {
        this.mContext = (MainActivity) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_v, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).setData(position);
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout item_layout;
        TextView tv_status;
        TextView tv_fanname;
        ImageView down_img;


        public RecyclerHolder(View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            tv_fanname = itemView.findViewById(R.id.tv_fanname);
            item_layout.setOnClickListener(this);
            down_img = itemView.findViewById(R.id.down_img);
            tv_status = itemView.findViewById(R.id.tv_status);


        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }

        public void setData(int position) {
            try {
                tv_fanname.setText(" " + listdate.getJSONObject(position).getString("name"));
                tv_status.setText(" " + listdate.getJSONObject(position).getString("status"));
                Glide.with(mContext)
                        .load(listdate.getJSONObject(position).getString("pic"))
                        .placeholder(R.drawable.placeholder_disk_300)//图片加载出来前，显示的图片
                        .error(R.drawable.placeholder_disk_300)//图片加载失败后，显示的图片
                        .into(down_img);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return listdate == null ? 0 : listdate.length();
    }
}
