package main.zm.elieli.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.blankj.utilcode.util.SnackbarUtils;

import main.zm.elieli.MainActivity;
import main.zm.elieli.R;
import main.zm.elieli.service.AlipayZeroSdk;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownLoadFragment extends Fragment {

    public View ConTenView;

    public MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity= (MainActivity) context;
    }

    public DownLoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConTenView = inflater.inflate(R.layout.fragment_down_load, container, false);
        BootstrapButton bt_baba = ConTenView.findViewById(R.id.bt_baba);
        bt_baba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 捐赠开发者
                // HTTPS://QR.ALIPAY.COM/FKX04844O7GX5QDURDKJ09      --------2快\
                //HTTPS://QR.ALIPAY.COM/FKX089840DT0DYAC0370B6       ---------自定义金额
                if (AlipayZeroSdk.hasInstalledAlipayClient(mainActivity)) {
                    AlipayZeroSdk.startAlipayClient(mainActivity, "fkx04675ylfnbbug1par622?t=1539334167074");
                } else {
                    mainActivity.toast("谢谢，您没有安装支付宝客户端");
                }

            }
        });
        return ConTenView;
    }

}
