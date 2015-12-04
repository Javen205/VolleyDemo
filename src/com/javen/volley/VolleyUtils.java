package com.javen.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Priority;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
/**
 * 网络请求工具类
 * @author Javen
 * 
 */
public class VolleyUtils {
	private final static String TAG=VolleyUtils.class.getSimpleName();

	public interface volleyListener{
		void onResponse(JSONObject response);
		void onErrorResponse();
	}
	
	/**
	 * 
	 * @param context
	 * @param url
	 * @param params
	 *            用来保存post参数
	 */
	public static void doPost(Context context, String url,HashMap<String, String> params,final volleyListener listener) {
		// 优先级有LOW，NORMAL,HIGH,IMMEDIATE
		// 设置请求的优先级别通过覆写getPrioriity()方法
		final Priority priority = Priority.HIGH;

		JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(
				params), new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// 正确响应时回调此函数
				listener.onResponse(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				// 正确响应时回调此函数
				listener.onErrorResponse();

			}
		}) {
			// 设置请求级别
			@Override
			public Priority getPriority() {
				return priority;
			}

			// 设置请求头
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				return super.getHeaders();
			}
		};
		// 第一个代表超时时间：即超过20S认为超时，第三个参数代表最大重试次数，这里设置为1.0f代表如果超时，则不重试
		req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		// 可以通过setTag方法为每一个Request添加tag
		req.setTag("My Tag");
		// 也可以在我们实现的添加进RequestQueue的时候设置
		VolleyController.getInstance(context).addToRequestQueue(req, "My Tag");

		// 取消Request
		// VolleyController.getInstance(context).getRequestQueue().cancelAll("My Tag");
		// 或者我们前面实现的方法
		// VolleyController.getInstance(context).cancelPendingRequests("My Tag");
	}
	
	public interface MyImageListener{
		void setImageBitmap(Bitmap bitmap);
	}
	
	public static void ImageView(Context context,String url,final MyImageListener listener){
		ImageLoader imageLoader=VolleyController.getInstance(context).getImageLoader();
		imageLoader.get(url,new ImageListener(){
		   @Override
		   public void onResponse(ImageContainer response,boolean arg)  {
		        if(response.getBitmap()!=null){
		        //设置imageView
		        //	imageView.setImageBitmap(response.getBitmap());
		        	listener.setImageBitmap(response.getBitmap());
		        }
		    }
		    @Override
		    public void onErrorResponse(VolleyError error){
		        Log.e("xx","Image Error"+error.getMessage());
		        }
		    });
	}
	
}
