package com.javen.volley.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.javen.volley.VolleyController;

/**
 * 图片缓存管理类 获取ImageLoader对象
 * @author Javen
 *
 */
public class ImageCacheManager {
    private static String TAG = ImageCacheManager.class.getSimpleName();

    /**
     * 获取ImageListener
     * 
     * @param view
     * @param defaultImage
     * @param errorImage
     * @return
     */
    public static ImageListener getImageListener(final ImageView view, final Bitmap defaultImage, final Bitmap errorImage) {

        return new ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // 回调失败
                if (errorImage != null) {
                    view.setImageBitmap(errorImage);
                }
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                // 回调成功
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImage != null) {
                    view.setImageBitmap(defaultImage);
                }
            }
        };

    }

    /**
     * 提供给外部调用方法
     * 
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(Context context,String url, ImageView view, Bitmap defaultImage, Bitmap errorImage) {
       VolleyController.getInstance(context).getImageLoader().get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), 0, 0);
    }

    /**
     * 提供给外部调用方法
     * 
     * @param url
     * @param view
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImage(Context context,String url, ImageView view, Bitmap defaultImage, Bitmap errorImage, int maxWidth, int maxHeight) {
    	 VolleyController.getInstance(context).getImageLoader().get(url, ImageCacheManager.getImageListener(view, defaultImage, errorImage), maxWidth, maxHeight);
    }
}