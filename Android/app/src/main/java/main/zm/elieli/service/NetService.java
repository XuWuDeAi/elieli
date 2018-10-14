package main.zm.elieli.service;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.activity.OpenWebActivity;
import main.zm.elieli.activity.ShowFanActivity;
import main.zm.elieli.adapter.SearchAdapter;
import main.zm.elieli.dialog.FanListDialog;
import main.zm.elieli.dialog.SeaarchDialog;
import main.zm.elieli.fragment.BlankFragment;
import main.zm.elieli.fragment.HomeFragment;
import okhttp3.Call;

/**
 * Created by zm on 2018/10/9.
 */

public class NetService {
    public static MainActivity mainActivity;

    public static void getHome(final MainActivity mainActivity, final HomeFragment homeFragment) {
        String url = "http://47.106.162.236:80/EliEli/home";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mainActivity.toast("网络请求错误:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject rep = new JSONObject(response);
                            JSONArray data = rep.getJSONArray("data");
                            homeFragment.listAdapter.setListdate(data);
                            homeFragment.listAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mainActivity.toast("网络请求错误:" + e.getMessage());
                        }

                    }
                });
    }

    public static void getLeaderboard(final MainActivity mainActivity, final FanListDialog fanListDialog) {
        String url = "http://47.106.162.236:80/EliEli/leaderboard";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mainActivity.toast("网络请求错误:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(response);
                            JSONArray data = resp.getJSONArray("data");
                            fanListDialog.fanHorListAdapter.setListdate(data);
                            fanListDialog.fanHorListAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    public static void getCharsing(final MainActivity mainActivity, String week, final BlankFragment blankFragment) {
        String url = "http://47.106.162.236/EliEli/chasing?week=" + week;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mainActivity.toast("网络请求错误:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(response);
                            JSONArray data = resp.getJSONArray("data");
                            blankFragment.listAdapter.setListdate(data);
                            blankFragment.listAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    public static void getShowFan(String id, final ShowFanActivity showFanActivity) {
        String url = "http://47.106.162.236/EliEli/getShowFan?nk=" + id;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showFanActivity.toast("网络请求错误:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(response);
                            showFanActivity.tv_fanname.setText(resp.getString("name"));
                            if (showFanActivity.isFinishing())
                                return;
                            Glide.with(showFanActivity)
                                    .load(resp.getString("pic"))
                                    .placeholder(R.drawable.placeholder_disk_300)//图片加载出来前，显示的图片
                                    .error(R.drawable.placeholder_disk_300)//图片加载失败后，显示的图片
                                    .into(showFanActivity.iv_fanpic);

                            showFanActivity.tv_attributes.setText(resp.getString("attributes"));
                            showFanActivity.tv_status.setText(resp.getString("status"));
                            showFanActivity.btItemListAdapter.listdate = resp.getJSONArray("aitems");
                            showFanActivity.btItemListAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }


    public static void getVedioUrl(String href, ShowFanActivity showFanActivity) {
        final ProgressDialog waitingDialog = new ProgressDialog(showFanActivity);
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
        String url = "http://47.106.162.236/EliEli/getVedioUrl?href=" + href;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mainActivity.toast("网络请求错误:" + e.getMessage());
                        waitingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(response);
                            Log.w("________", resp.toString());
                            mainActivity.toast(response);
                            String href = resp.getString("data");
                            Intent intent = new Intent(mainActivity, OpenWebActivity.class);

                            Log.w("________", href);
                            intent.putExtra("webUrl", href);
                            mainActivity.startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        waitingDialog.dismiss();

                    }
                });
    }


    public static void search(String keyword, final SeaarchDialog seaarchDialog) {

        String url = "http://47.106.162.236/EliEli/search?value=" + keyword;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mainActivity.toast("网络请求错误:" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject resp = null;
                        try {
                            resp = new JSONObject(response);
                            JSONArray data = resp.getJSONArray("data");

                            mainActivity.toast("搜索结果:" + data.length() + "个");
                            if (data.length() != 0) {

                                seaarchDialog.searchAdapter.listdate = data;
                                seaarchDialog.searchAdapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}
