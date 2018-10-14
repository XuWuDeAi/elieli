package main.zm.elieli.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import main.zm.elieli.R;
import main.zm.elieli.base.BaseActivity;
import main.zm.elieli.service.Permissions;

public class VedioActivity extends BaseActivity {
    final String TAG = "测试 PlayerActivity";
    final int REQ_OPEN_FILE = 101;

    VideoView videoView; // 播放器控件
    SeekBar seekBar; // 可拖放进度条
    View controlBar; // 播放控制面板

    Handler msgHandler; // Android消息机制 (高级篇讲解)
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);

        // 权限支持
        Permissions.check(this);

        // 初始化界面
        videoView = (VideoView) findViewById(R.id.id_videoview); // 播放器
        seekBar = (SeekBar) findViewById(R.id.id_seekbar);  // 可拖放进度条
        seekBar.setMax(100);

        // 消息支持 (可以写成内部类, 用匿名类的话onCreate也点长)
        msgHandler = new MyHandler();

        // 播放控制（暂停、继承)
        final ImageButton imageButton = (ImageButton) findViewById(R.id.id_play_pause);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 此处可以优化 (事先把两个图标先加载好, 不要每次现加载）
                if (videoView.isPlaying()) {
                    Log.d(TAG, "正在播放，现在暂停...");
                    videoView.pause();
                    imageButton.setImageDrawable(getDrawable(R.drawable.ic_play));
                } else {
                    Log.d(TAG, "不在播放，现在继续...");
                    videoView.start();
                    imageButton.setImageDrawable(getDrawable(R.drawable.ic_pause));
                }
            }
        });

        // 播放控制 (进度拖放）
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 控制视频跳转到目标位置
                if (videoView.isPlaying()) {
                    int progress = seekBar.getProgress();
                    int position = progress * videoView.getDuration() / 100;
                    videoView.seekTo(position);
                }
            }
        });

        // 控制面板的显示和隐藏
        controlBar = findViewById(R.id.id_control_bar);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "点中画面..");
                if (controlBar.getVisibility() == View.GONE) {
                    controlBar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "显示播放控制面板..");
                } else {
                    controlBar.setVisibility(View.GONE);
                    Log.d(TAG, "隐藏播放控制面板..");
                }
                return false;
            }
        });

        // 接受外部调用
        Intent intent = getIntent();
        Uri mediaUri = intent.getData();
        if (mediaUri != null) {
            videoView.setVideoURI(mediaUri);
            videoView.start();
        }

        String href = getIntent().getStringExtra("href");
        toast(href);
        videoView.setVideoURI(Uri.parse(href));
        videoView.start();

    }

    @Override
    protected void onStart() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        super.onStart();

        if (timer == null) {
            // 启动定时器(间隔500ms)
            timerTask = new MyTimerTask();
            timer = new Timer();
            timer.schedule(timerTask, 500, 500);
        }
    }

    @Override
    protected void onStop() {
        if (timer != null) {
            //本界面隐藏时，要停止定时器（因为本界面已经隐藏了，如果继续刷新界面将毫无意义、白白耗费CPU)
            timer.cancel();
            timer = null;
        }
        super.onStop();
    }

    // 点击 '打开' 按钮
    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*"); // 只显示视频
        startActivityForResult(intent, REQ_OPEN_FILE);
    }

    // 从媒体管理器返回
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_OPEN_FILE) {
            if (resultCode == RESULT_OK) {
                Uri mediaUri = data.getData();
                videoView.setVideoURI(mediaUri);
                videoView.start();
            }
        }
    }

    // 当视频停止时
    // 更新显示进度: duration总时长 position 当前播放位置
    public void showProgess(int duration, int position) {
        // 转成百分比
        int percent = position * 100 / duration;
        seekBar.setProgress(percent);
    }

    /////////////// 消息支持 ////////////////
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                // 从消息里取出进度数据，然后更新UI
                int duration = msg.arg1;
                int position = msg.arg2;
                showProgess(duration, position);
            }
        }
    }

    ///////////// 定时器任务 ////////////
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            // 如果当前VideoView并不在播放中,就不做什么
            if (!videoView.isPlaying()) return;

            // 取得当前播放进度
            int duration = videoView.getDuration(); // 单位 毫秒(ms)
            int position = videoView.getCurrentPosition(); // 单位 毫秒(ms)
            // 注意：在工作线程里不能直接更新UI，必须发消息给UI线程，然后在Handler里处理

            // 发消息给UI线程
            Message msg = new Message();
            msg.what = 1; // 消息类型
            msg.arg1 = duration; // 第1个参数
            msg.arg2 = position; // 第2个参数
            msgHandler.sendMessage(msg);
        }
    }
}
