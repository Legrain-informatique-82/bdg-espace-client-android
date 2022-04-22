package fr.legrain.bdg.espaceclient.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import fr.legrain.bdg.espaceclient.R;

/*
https://stackoverflow.com/questions/30716673/changing-the-underline-color-of-textview

https://developer.android.com/training/custom-views/create-view

res/values/attrs.xml

<declare-styleable name="UnderlinedTextView" >
    <attr name="underlineWidth" format="dimension" />
    <attr name="underlineColor" format="color" />
</declare-styleable>


<fr.legrain.bdg.espaceclient.ui.UnderlinedTextView
    android:id="@+id/tvTest"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    android:layout_marginBottom="10dp"
    android:layout_marginLeft="20dp"
    android:layout_marginRight="20dp"
    android:gravity="center"
    android:text="This is a demo text"
    android:textSize="16sp"
    app:underlineColor="#ffc112ef"
    app:underlineWidth="3dp"/>
 */
public class UnderlinedTextView extends AppCompatTextView {

    private Rect lineBoundsRect;
    private Paint underlinePaint;

   // private int mStrokeWidth = 3;

    public UnderlinedTextView(Context context) {
        this(context, null, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {

        float density = context.getResources().getDisplayMetrics().density;

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlinedTextView, defStyle, 0);
        int mColor = typedArray.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        float mStrokeWidth = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
        typedArray.recycle();

        lineBoundsRect = new Rect();
        underlinePaint = new Paint();
        underlinePaint.setStyle(Paint.Style.STROKE);
        underlinePaint.setColor(mColor); //color of the underline
        underlinePaint.setStrokeWidth(mStrokeWidth);
    }

    @ColorInt
    public int getUnderLineColor() {
        return underlinePaint.getColor();
    }

    public void setUnderLineColor(@ColorInt int mColor) {
        underlinePaint.setColor(mColor);
        invalidate();
    }

    public float getUnderlineWidth() {
       return underlinePaint.getStrokeWidth();
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        underlinePaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int count = getLineCount();

        final Layout layout = getLayout();
        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, lineBoundsRect);
            firstCharInLine = layout.getLineStart(i);
            lastCharInLine = layout.getLineEnd(i);

            x_start = layout.getPrimaryHorizontal(firstCharInLine);
            x_diff = layout.getPrimaryHorizontal(firstCharInLine + 1) - x_start;
            x_stop = layout.getPrimaryHorizontal(lastCharInLine - 1) + x_diff;

            canvas.drawLine(x_start, baseline + underlinePaint.getStrokeWidth(), x_stop, baseline + underlinePaint.getStrokeWidth(), underlinePaint);
        }

        super.onDraw(canvas);
    }
}