//package lb.edu.ul.khayme_w_nar.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//
//public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {
//
//    public CircularImageView(Context context) {
//        super(context);
//    }
//
//    public CircularImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Drawable drawable = getDrawable();
//        if (drawable == null) return;
//
//        if (getWidth() == 0 || getHeight() == 0) return;
//
//        Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
//        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//        int width = getWidth();
//        int height = getHeight();
//
//        Bitmap roundBitmap = getRoundedBitmap(bitmap, width);
//        canvas.drawBitmap(roundBitmap, 0, 0, null);
//    }
//
//    private Bitmap getRoundedBitmap(Bitmap bitmap, int radius) {
//        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, radius, radius);
//
//        paint.setAntiAlias(true);
//        canvas.drawCircle(radius/2f, radius/2f, radius/2f, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, null, rect, paint);
//
//        return output;
//    }
//}

package lb.edu.ul.khayme_w_nar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) return;

        if (getWidth() == 0 || getHeight() == 0) return;

        // Convert drawable to bitmap safely
        Bitmap bitmap = drawableToBitmap(drawable);

        if (bitmap == null) return;

        int width = getWidth();
        int height = getHeight();

        Bitmap roundBitmap = getRoundedBitmap(bitmap, width);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    // New method to convert any drawable to bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {
        // If it's already a BitmapDrawable, extract the bitmap directly
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // For other drawable types, create a bitmap
        try {
            // Create a bitmap with the same width and height as the drawable
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );

            // Draw the drawable onto the bitmap
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);

            return bitmap;
        } catch (Exception e) {
            // Log the error or handle it appropriately
            return null;
        }
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap, int radius) {
        // Resize bitmap to fit the circular view
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, true);

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        canvas.drawCircle(radius/2f, radius/2f, radius/2f, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledBitmap, null, rect, paint);

        return output;
    }
}