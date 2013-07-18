package com.yjw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
  
/** 
 * Bitmap与DrawAble与byte[]与InputStream之间的转换工具类 
 * @author Administrator 
 * 
 */  
public class ImgTool {  
    private static ImgTool tools = new ImgTool();  
  
    public static ImgTool getInstance() {  
        if (tools == null) {  
            tools = new ImgTool();  
            return tools;  
        }  
        return tools;  
    }  
  
    // 将byte[]转换成InputStream  
    public InputStream Byte2InputStream(byte[] b) {  
        ByteArrayInputStream bais = new ByteArrayInputStream(b);  
        return bais;  
    }  
  
    // 将InputStream转换成byte[]  
    public byte[] InputStream2Bytes(InputStream is) {  
        String str = "";  
        byte[] readByte = new byte[1024];  
        int readCount = -1;  
        try {  
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {  
                str += new String(readByte).trim();  
            }  
            return str.getBytes();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    // 将Bitmap转换成InputStream  
    public InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }  
  
    // 将Bitmap转换成InputStream  
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }  
  
    // 将InputStream转换成Bitmap  
    public Bitmap InputStream2Bitmap(InputStream is) {  
        return BitmapFactory.decodeStream(is);  
    }  
  
    // Drawable转换成InputStream  
    public InputStream Drawable2InputStream(Drawable d) {  
        Bitmap bitmap = this.drawable2Bitmap(d);  
        return this.Bitmap2InputStream(bitmap);  
    }  
  
    // InputStream转换成Drawable  
    public Drawable InputStream2Drawable(InputStream is) {  
        Bitmap bitmap = this.InputStream2Bitmap(is);  
        return this.bitmap2Drawable(bitmap);  
    }  
  
    // Drawable转换成byte[]  
    public byte[] Drawable2Bytes(Drawable d) {  
    	//Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
    	//ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	//bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    	//byte[] bitmapdata = stream.toByteArray();
    	//return bitmapdata;
        Bitmap bitmap = drawable2Bitmap(d);  
        return this.Bitmap2Bytes(bitmap);  
    }  
  
    // byte[]转换成Drawable  
    public Drawable Bytes2Drawable(byte[] b) {  
        Bitmap bitmap = Bytes2Bitmap(b);  
        return bitmap2Drawable(bitmap);  
    }  
  
    // Bitmap转换成byte[]  
    public byte[] Bitmap2Bytes(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
        bm.setDensity(Bitmap.DENSITY_NONE);
        return baos.toByteArray();  
    }  
  
    // byte[]转换成Bitmap  
    public Bitmap Bytes2Bitmap(byte[] b) {  
        if (b.length != 0) {  
        	Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
        	bm.setDensity(Bitmap.DENSITY_NONE);
            return bm;  
        }  
        return null;  
    }  
  
    // Drawable转换成Bitmap  
    public Bitmap drawable2Bitmap(Drawable drawable) {  
      /*  Bitmap bitmap = Bitmap  
                .createBitmap(  
                        drawable.getIntrinsicWidth(),  
                        drawable.getIntrinsicHeight(),  
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                : Bitmap.Config.RGB_565);
        bitmap.setDensity(bitmap.D)
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                drawable.getIntrinsicHeight());  
        drawable.draw(canvas);
        return bitmap; */
    	// 取 drawable 的长宽  
        int w = drawable.getIntrinsicWidth();  
        int h = drawable.getIntrinsicHeight();  
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;  
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);  
        bitmap.setDensity(bitmap.DENSITY_NONE);
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, w, h);    
        drawable.draw(canvas);  
        return bitmap;          
    }  
  
    // Bitmap转换成Drawable  
    public Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap); 
    }  
}