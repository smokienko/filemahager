package filemanager.android.ykolesnik.com.filemanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 * Created by s.mokiyenko on 11/24/14.
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable{

    private boolean mCheched;
    private Drawable mDefaultColor;

    public CheckableRelativeLayout(Context context) {
        super(context);
        mDefaultColor = this.getBackground();
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDefaultColor = this.getBackground();
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDefaultColor = this.getBackground();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDefaultColor = this.getBackground();

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setChecked(boolean b) {
        if (mCheched != b) {
            mCheched = b;
            this.setBackground(mCheched ? new ColorDrawable(Color.BLUE) : mDefaultColor);
        }
    }

    @Override
    public boolean isChecked() {
        return mCheched;
    }

    @Override
    public void toggle() {
    }
}
