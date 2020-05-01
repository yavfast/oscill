package anywheresoftware.b4a.objects.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@BA.ShortName("Canvas")
@BA.ActivityObject
public class CanvasWrapper {
    private BitmapWrapper bw;
    @BA.Hide
    public Canvas canvas;
    private PorterDuffXfermode eraseMode;
    private Paint paint;
    private RectF rectF;

    public void Initialize(View Target) {
        this.paint = new Paint();
        ViewGroup.LayoutParams lp = Target.getLayoutParams();
        Bitmap bitmap = Bitmap.createBitmap(lp.width, lp.height, Bitmap.Config.ARGB_8888);
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        this.canvas = new Canvas(bitmap);
        if (Target.getBackground() != null) {
            Target.getBackground().setBounds(0, 0, lp.width, lp.height);
            Target.getBackground().draw(this.canvas);
        }
        Target.setBackgroundDrawable(bd);
        this.bw = new BitmapWrapper();
        this.bw.setObject(bitmap);
    }

    private void checkAndSetTransparent(int color) {
        if (color != 0) {
            this.paint.setXfermode((Xfermode) null);
            return;
        }
        if (this.eraseMode == null) {
            this.eraseMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        }
        this.paint.setXfermode(this.eraseMode);
    }

    public void Initialize2(Bitmap Bitmap) {
        this.paint = new Paint();
        if (!Bitmap.isMutable()) {
            throw new RuntimeException("Bitmap is not mutable.");
        }
        this.canvas = new Canvas(Bitmap);
        this.bw = new BitmapWrapper();
        this.bw.setObject(Bitmap);
    }

    public void DrawLine(float x1, float y1, float x2, float y2, int Color, float StrokeWidth) {
        checkAndSetTransparent(Color);
        this.paint.setColor(Color);
        this.paint.setStrokeWidth(StrokeWidth);
        this.canvas.drawLine(x1, y1, x2, y2, this.paint);
    }

    public void DrawColor(int Color) {
        if (Color == 0) {
            this.canvas.drawColor(Color, PorterDuff.Mode.CLEAR);
        } else {
            this.canvas.drawColor(Color);
        }
    }

    public void DrawOval(Rect Rect1, int Color, boolean Filled, float StrokeWidth) {
        checkAndSetTransparent(Color);
        this.paint.setColor(Color);
        this.paint.setStyle(Filled ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(StrokeWidth);
        if (this.rectF == null) {
            this.rectF = new RectF();
        }
        this.rectF.set(Rect1);
        this.canvas.drawOval(this.rectF, this.paint);
    }

    public void DrawOvalRotated(Rect Rect1, int Color, boolean Filled, float StrokeWidth, float Degrees) {
        this.canvas.save();
        try {
            this.canvas.rotate(Degrees, (float) Rect1.centerX(), (float) Rect1.centerY());
            DrawOval(Rect1, Color, Filled, StrokeWidth);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawRect(Rect Rect1, int Color, boolean Filled, float StrokeWidth) {
        checkAndSetTransparent(Color);
        this.paint.setColor(Color);
        this.paint.setStyle(Filled ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(StrokeWidth);
        this.canvas.drawRect(Rect1, this.paint);
    }

    public void DrawRectRotated(Rect Rect1, int Color, boolean Filled, float StrokeWidth, float Degrees) {
        this.canvas.save();
        try {
            this.canvas.rotate(Degrees, (float) Rect1.centerX(), (float) Rect1.centerY());
            DrawRect(Rect1, Color, Filled, StrokeWidth);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawCircle(float x, float y, float Radius, int Color, boolean Filled, float StrokeWidth) {
        checkAndSetTransparent(Color);
        this.paint.setColor(Color);
        this.paint.setStyle(Filled ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(StrokeWidth);
        this.canvas.drawCircle(x, y, Radius, this.paint);
    }

    public void DrawBitmap(Bitmap Bitmap1, Rect SrcRect, Rect DestRect) {
        this.canvas.drawBitmap(Bitmap1, SrcRect, DestRect, (Paint) null);
    }

    public void DrawBitmapRotated(Bitmap Bitmap1, Rect SrcRect, Rect DestRect, float Degrees) {
        this.canvas.save();
        try {
            this.canvas.rotate(Degrees, (float) DestRect.centerX(), (float) DestRect.centerY());
            DrawBitmap(Bitmap1, SrcRect, DestRect);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawBitmapFlipped(Bitmap Bitmap1, Rect SrcRect, Rect DestRect, boolean Vertically, boolean Horizontally) {
        int i = -1;
        this.canvas.save();
        try {
            Canvas canvas2 = this.canvas;
            float f = (float) (Horizontally ? -1 : 1);
            if (!Vertically) {
                i = 1;
            }
            canvas2.scale(f, (float) i, (float) DestRect.centerX(), (float) DestRect.centerY());
            DrawBitmap(Bitmap1, SrcRect, DestRect);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawText(BA ba, String Text, float x, float y, Typeface Typeface1, float TextSize, int Color, Paint.Align Align1) {
        checkAndSetTransparent(Color);
        this.paint.setTextAlign(Align1);
        this.paint.setTextSize(ba.context.getResources().getDisplayMetrics().scaledDensity * TextSize);
        this.paint.setTypeface(Typeface1);
        this.paint.setColor(Color);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(Common.Density);
        this.paint.setStyle(Paint.Style.STROKE);
        this.canvas.drawText(Text, x, y, this.paint);
        this.paint.setAntiAlias(false);
    }

    public void DrawTextRotated(BA ba, String Text, float x, float y, Typeface Typeface1, float TextSize, int Color, Paint.Align Align1, float Degree) {
        this.canvas.save();
        try {
            this.canvas.rotate(Degree, x, y);
            DrawText(ba, Text, x, y, Typeface1, TextSize, Color, Align1);
        } finally {
            this.canvas.restore();
        }
    }

    public float MeasureStringWidth(String Text, Typeface Typeface, float TextSize) {
        this.paint.setTextSize(BA.applicationContext.getResources().getDisplayMetrics().scaledDensity * TextSize);
        this.paint.setTypeface(Typeface);
        this.paint.setStrokeWidth(Common.Density);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setTextAlign(Paint.Align.LEFT);
        return this.paint.measureText(Text);
    }

    public float MeasureStringHeight(String Text, Typeface Typeface, float TextSize) {
        this.paint.setTextSize(BA.applicationContext.getResources().getDisplayMetrics().scaledDensity * TextSize);
        this.paint.setTypeface(Typeface);
        this.paint.setStrokeWidth(Common.Density);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setTextAlign(Paint.Align.LEFT);
        Rect r = new Rect();
        this.paint.getTextBounds(Text, 0, Text.length(), r);
        return (float) r.height();
    }

    public void DrawPoint(float x, float y, int Color) {
        checkAndSetTransparent(Color);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(Common.Density);
        this.paint.setColor(Color);
        this.canvas.drawPoint(x, y, this.paint);
    }

    public void DrawDrawable(Drawable Drawable1, Rect DestRect) {
        Drawable1.setBounds(DestRect);
        Drawable1.draw(this.canvas);
    }

    public void DrawDrawableRotate(Drawable Drawable1, Rect DestRect, float Degrees) {
        this.canvas.save();
        try {
            this.canvas.rotate(Degrees, (float) DestRect.centerX(), (float) DestRect.centerY());
            DrawDrawable(Drawable1, DestRect);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawPath(Path Path1, int Color, boolean Filled, float StrokeWidth) {
        checkAndSetTransparent(Color);
        this.paint.setColor(Color);
        this.paint.setStyle(Filled ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(StrokeWidth);
        this.canvas.drawPath(Path1, this.paint);
    }

    public void ClipPath(Path Path1) {
        this.canvas.clipPath(Path1);
    }

    public void RemoveClip() {
        this.canvas.clipRect(new Rect(0, 0, this.bw.getWidth(), this.bw.getHeight()), Region.Op.UNION);
    }

    public BitmapWrapper getBitmap() {
        return this.bw;
    }

    @BA.ShortName("Bitmap")
    public static class BitmapWrapper extends AbsObjectWrapper<Bitmap> {
        public void Initialize(String Dir, String FileName) throws IOException {
            File.InputStreamWrapper in = null;
            boolean shouldDownSample = false;
            try {
                File file = Common.File;
                in = File.OpenInput(Dir, FileName);
                Initialize2((InputStream) in.getObject());
                in.Close();
            } catch (OutOfMemoryError e) {
                OutOfMemoryError outOfMemoryError = e;
                System.gc();
                in.Close();
                shouldDownSample = true;
            }
            if (shouldDownSample) {
                BA.Log("Downsampling image due to lack of memory.");
                Display display = ((WindowManager) BA.applicationContext.getSystemService("window")).getDefaultDisplay();
                InitializeSample(Dir, FileName, display.getWidth() / 2, display.getHeight() / 2);
            }
        }

        public void Initialize2(InputStream InputStream) {
            Bitmap bmp = BitmapFactory.decodeStream(InputStream);
            if (bmp == null) {
                throw new RuntimeException("Error loading bitmap.");
            }
            bmp.setDensity(160);
            setObject(bmp);
        }

        public void InitializeSample(String Dir, String FileName, int MaxWidth, int MaxHeight) throws IOException {
            File file = Common.File;
            File.InputStreamWrapper in = File.OpenInput(Dir, FileName);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream((InputStream) in.getObject(), (Rect) null, o);
            in.Close();
            float r1 = Math.max((float) (o.outWidth / MaxWidth), (float) (o.outHeight / MaxHeight));
            BitmapFactory.Options o2 = null;
            if (r1 > 1.0f) {
                o2 = new BitmapFactory.Options();
                o2.inSampleSize = (int) r1;
            }
            Bitmap bmp = null;
            boolean oomFlag = false;
            int retries = 5;
            while (retries > 0) {
                try {
                    File file2 = Common.File;
                    in = File.OpenInput(Dir, FileName);
                    bmp = BitmapFactory.decodeStream((InputStream) in.getObject(), (Rect) null, o2);
                    in.Close();
                    break;
                } catch (OutOfMemoryError e) {
                    OutOfMemoryError outOfMemoryError = e;
                    if (in != null) {
                        in.Close();
                    }
                    System.gc();
                    if (o2 == null) {
                        o2 = new BitmapFactory.Options();
                        o2.inSampleSize = 1;
                    }
                    o2.inSampleSize *= 2;
                    BA.Log("Downsampling image due to lack of memory: " + o2.inSampleSize);
                    oomFlag = true;
                    retries--;
                }
            }
            if (bmp != null) {
                bmp.setDensity(160);
                setObject(bmp);
            } else if (oomFlag) {
                throw new RuntimeException("Error loading bitmap (OutOfMemoryError)");
            } else {
                throw new RuntimeException("Error loading bitmap.");
            }
        }

        public void Initialize3(Bitmap Bitmap) {
            setObject(Bitmap.createBitmap(Bitmap));
        }

        public void InitializeMutable(@BA.Pixel int Width, @BA.Pixel int Height) {
            setObject(Bitmap.createBitmap(Width, Height, Bitmap.Config.ARGB_8888));
        }

        public int GetPixel(int x, int y) {
            return ((Bitmap) getObject()).getPixel(x, y);
        }

        public int getWidth() {
            return ((Bitmap) getObject()).getWidth();
        }

        public int getHeight() {
            return ((Bitmap) getObject()).getHeight();
        }

        public void WriteToStream(OutputStream OutputStream, int Quality, Bitmap.CompressFormat Format) {
            ((Bitmap) getObject()).compress(Format, Quality, OutputStream);
        }

        @BA.Hide
        public String toString() {
            String s = baseToString();
            if (IsInitialized()) {
                return String.valueOf(s) + ": " + ((Bitmap) getObject()).getWidth() + " x " + ((Bitmap) getObject()).getHeight();
            }
            return s;
        }
    }

    @BA.ShortName("Rect")
    public static class RectWrapper extends AbsObjectWrapper<Rect> {
        public void Initialize(int Left, int Top, int Right, int Bottom) {
            setObject(new Rect(Left, Top, Right, Bottom));
        }

        public int getLeft() {
            return ((Rect) getObject()).left;
        }

        public void setLeft(int Left) {
            ((Rect) getObject()).left = Left;
        }

        public int getTop() {
            return ((Rect) getObject()).top;
        }

        public void setTop(int Top) {
            ((Rect) getObject()).top = Top;
        }

        public int getRight() {
            return ((Rect) getObject()).right;
        }

        public void setRight(int Right) {
            ((Rect) getObject()).right = Right;
        }

        public int getBottom() {
            return ((Rect) getObject()).bottom;
        }

        public void setBottom(int Bottom) {
            ((Rect) getObject()).bottom = Bottom;
        }

        public int getCenterX() {
            return ((Rect) getObject()).centerX();
        }

        public int getCenterY() {
            return ((Rect) getObject()).centerY();
        }

        @BA.Hide
        public String toString() {
            String s = baseToString();
            if (IsInitialized()) {
                return String.valueOf(s) + "(" + getLeft() + ", " + getTop() + ", " + getRight() + ", " + getBottom() + ")";
            }
            return s;
        }
    }

    @BA.ShortName("Path")
    public static class PathWrapper extends AbsObjectWrapper<Path> {
        public void Initialize(float x, float y) {
            Path path = new Path();
            path.moveTo(x, y);
            setObject(path);
        }

        public void LineTo(float x, float y) {
            getObject().lineTo(x, y);
        }

        public void MoveTo(float x, float y) {
            getObject().moveTo(x, y);
        }
    }
}
