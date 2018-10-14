package main.zm.elieli.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by zm on 2018/10/11.
 */

public class MyButton extends BootstrapButton {
    public String href;
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
