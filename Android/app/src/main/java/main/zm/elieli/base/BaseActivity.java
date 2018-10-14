package main.zm.elieli.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zm on 2018/10/9.
 */

public class BaseActivity extends AppCompatActivity {

    public void toast( String it) {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show();
    }
}
