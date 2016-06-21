package com.jvgl.Norad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by norad on 31/05/15.
 */
public class NScreenshot {
    private final static String TAG = "Norad.Screenshot";

    public static String get_screenshotPath(){
        return Environment.getExternalStorageDirectory() + File.separator + "Pictures" + File.separator + "Norad";
    }
    public static File make_screenshotFile(String _ext){
        String path = get_screenshotPath();
        File f = new File(path);
        f.mkdirs();
        String fullPath  = path + File.separator + "scr_" + System.currentTimeMillis()+"."+_ext;
        File file = new File(fullPath);
        return file;
    }
    public static Bitmap screenshot(Context context, View v) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);

        return returnedBitmap;
    }

    public static Bitmap glScreenshot(NRenderer _nrenderer){
        _nrenderer.set_doScreenshot(true);
        Bitmap screenshot = _nrenderer.get_lastScreenshot();
        return screenshot;
    }

    public static void aOverB(Bitmap _A, Bitmap _B, Bitmap _out){
        Canvas comboImage = new Canvas(_out);
        comboImage.drawBitmap(_A, 0f, 0f, null);
        comboImage.drawBitmap(_B, 0f, 0f, null);
    }


    public static void save_bitmap(Bitmap _source, File _output, Bitmap.CompressFormat _format, int _quality){
        OutputStream fout = null;
        try {
            fout = new FileOutputStream(_output);
            _source.compress(_format, _quality, fout);
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fout.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static File make_compositeScreenshot(Activity _activity, NRenderer _nrenderer, String _ext){
        Bitmap gl = glScreenshot(_nrenderer);
        Bitmap ui = screenshot(_activity, _activity.getWindow().getDecorView().getRootView());
        Bitmap out = Bitmap.createBitmap(gl);
        aOverB(gl, ui, out);
        File imageFile = make_screenshotFile(_ext);
        save_bitmap(out, imageFile, find_format(_ext), 90);
        Log.d(TAG, "Created composite screenshot "+imageFile.getAbsolutePath());
        return imageFile;
    }

    public static File make_glscreenshot(NRenderer _nrenderer, String _ext){
        Bitmap gl = glScreenshot(_nrenderer);
        File imageFile = make_screenshotFile(_ext);
        save_bitmap(gl, imageFile, find_format(_ext), 90);
        Log.d(TAG, "Created glScreenshot "+imageFile.getAbsolutePath());
        return imageFile;
    }

    public static File make_screenshot(Activity _activity, String _ext){
        Bitmap ui = screenshot(_activity, _activity.getWindow().getDecorView().getRootView());
        File imageFile = make_screenshotFile(_ext);
        save_bitmap(ui, imageFile, find_format(_ext), 90);
        Log.d(TAG, "Created screenshot "+imageFile.getAbsolutePath());
        return imageFile;
    }

    public static Bitmap.CompressFormat find_format(String _ext){
        if (_ext.equalsIgnoreCase("jpg") || _ext.equalsIgnoreCase("jpeg")){
            return Bitmap.CompressFormat.JPEG;
        }
        else if (_ext.equalsIgnoreCase("png")){
            return Bitmap.CompressFormat.PNG;
        }
        else {
            Log.e(TAG, "Illegal extension : " + _ext + ". Use 'jpg' or 'png'");
            throw new IllegalArgumentException();
        }
    }
}
