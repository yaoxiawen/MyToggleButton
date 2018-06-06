package com.yxw.mytogglebutton.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TButton extends View {

    private TBState tbState;//开关的状态
    private Bitmap slidebg;//滑动块的背景图片
    private Bitmap switchbg;//滑动开关的背景图片
    private int x = 0;
    private boolean move = false;

    /**
     * 使用代码动态new对象时调用该构造方法
     *
     * @param context
     */
    public TButton(Context context) {
        super(context);
    }

    /**
     * 有属性定义时，布局文件所用的构造方法
     * view只是在布局文件中使用，只需要重写这个构造方法即可
     *
     * @param context
     * @param attrs
     */
    public TButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 定义枚举变量，开关的状态，open或者close
     */
    public enum TBState {
        Open, Close
    }

    /**
     * 设置滑动块的背景图片
     *
     * @param slideBackground
     */
    public void setSlideBackgroundResource(int slideBackground) {
        slidebg = BitmapFactory.decodeResource(getResources(), slideBackground);
    }

    /**
     * 设置滑动开关的背景图片
     *
     * @param switchBackground
     */
    public void setSwitchBackgroundResource(int switchBackground) {
        switchbg = BitmapFactory.decodeResource(getResources(), switchBackground);
    }

    /**
     * 设置开关的状态
     *
     * @param state
     */
    public void setTBState(TBState state) {
        tbState = state;
    }

    /**
     * 设置当前控件显示在屏幕上的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchbg.getWidth(), switchbg.getHeight());
    }

    /**
     * 控制显示在屏幕上的样子
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图片
        canvas.drawBitmap(switchbg, 0, 0, null);
        if (move) {
            //滑动块的位置
            int left = x - slidebg.getWidth() / 2;
            if (left < 0) {
                left = 0;
            }
            if (left > switchbg.getWidth() - slidebg.getWidth()) {
                left = switchbg.getWidth() - slidebg.getWidth();
            }
            //绘制滑动块图片
            canvas.drawBitmap(slidebg, left, 0, null);
        } else {
            if (tbState == TBState.Open) {
                canvas.drawBitmap(slidebg, switchbg.getWidth() - slidebg.getWidth(), 0, null);
            } else {
                canvas.drawBitmap(slidebg, 0, 0, null);
            }
        }
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                move = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                move = false;
                if (x < switchbg.getWidth() / 2) {
                    if (tbState != TBState.Close) {
                        tbState = TBState.Close;
                        if (listener != null) {
                            listener.onStateChange(tbState);
                        }
                    }
                } else {
                    if (tbState != TBState.Open) {
                        tbState = TBState.Open;
                        if (listener != null) {
                            listener.onStateChange(tbState);
                        }
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 暴露接口
     */
    public interface OnStateChangeListener {
        void onStateChange(TBState state);
    }

    private OnStateChangeListener listener;

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.listener = listener;
    }
}
