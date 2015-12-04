package com.javen.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.javen.volley.cache.ImageCacheUtil;

public class VolleyController {

	// 创建一个TAG，方便调试或Log
	private static final String TAG = "VolleyController";

	// 创建一个全局的请求队列
	private RequestQueue reqQueue;
	private ImageLoader imageLoader;

	// 创建一个static VolleyController对象，便于全局访问
	private static VolleyController mInstance;
	
	private Context mContext;

	private VolleyController(Context context) {
		mContext=context;
	}

	/**
	 * 以下为需要我们自己封装的添加请求取消请求等方法
	 */

	// 用于返回一个VolleyController单例
	public static VolleyController getInstance(Context context) {
		if (mInstance == null) {
			synchronized(VolleyController.class)
			{
				if (mInstance == null) {
					mInstance = new VolleyController(context);
				}
			}
		}
		return mInstance;
	}

	// 用于返回全局RequestQueue对象，如果为空则创建它
	public RequestQueue getRequestQueue() {
		if (reqQueue == null){
			synchronized(VolleyController.class)
			{
				if (reqQueue == null){
					reqQueue = Volley.newRequestQueue(mContext);
				}
			}
		}
		return reqQueue;
	}
	
	
	public ImageLoader getImageLoader(){
        getRequestQueue();
        //如果imageLoader为空则创建它，第二个参数代表处理图像缓存的类
        if(imageLoader==null){
        	//LruCache
            //imageLoader=new ImageLoader(reqQueue, new LruBitmapCache());
        	//LruCache  DiskLruCache
        	imageLoader=new ImageLoader(reqQueue, new ImageCacheUtil(mContext));
        }
        return imageLoader;
    }


	/**
	 * 将Request对象添加进RequestQueue，由于Request有*StringRequest,JsonObjectResquest...
	 * 等多种类型，所以需要用到*泛型。同时可将*tag作为可选参数以便标示出每一个不同请求
	 */

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// 如果tag为空的话，就是用默认TAG
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	// 通过各Request对象的Tag属性取消请求
	public void cancelPendingRequests(Object tag) {
		if (reqQueue != null) {
			reqQueue.cancelAll(tag);
		}
	}
}