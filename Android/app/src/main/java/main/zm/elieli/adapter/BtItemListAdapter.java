package main.zm.elieli.adapter;

/**
 * Created by zm on 2018/9/3.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import main.zm.elieli.activity.OpenWebActivity;
import main.zm.elieli.activity.ShowFanActivity;
import main.zm.elieli.service.NetService;
import main.zm.elieli.view.MyButton;


public class BtItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ShowFanActivity mContext;
    public JSONArray listdate = new JSONArray();
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

    public BtItemListAdapter(Context mContext) {
        this.mContext = (ShowFanActivity) mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.button, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).setData(position);
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyButton myButton;
//        LinearLayout item_layout;

        public RecyclerHolder(View itemView) {
            super(itemView);
//            item_layout = itemView.findViewById(R.id.item_layout);
//            item_layout.setOnClickListener(this);
            myButton = itemView.findViewById(R.id.bt_button);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NetService.getVedioUrl(myButton.href,mContext);
//                    String href ="http://www.ezdmw.com"+myButton.href;
//                    Intent intent = new Intent(mContext, OpenWebActivity.class);
//
//                    Log.w("________", href);
//                    intent.putExtra("webUrl",href);
//                    mContext.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getLayoutPosition());
            }
        }

        public void setData(int position) {
            try {
                myButton.setText(listdate.getJSONObject(position).getString("hrefname"));
                myButton.href = listdate.getJSONObject(position).getString("href");
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
