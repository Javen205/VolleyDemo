package com.javen.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.javen.test.UI.ItemOnListener;
import com.javen.volley.R;
import com.javen.volley.VolleyController;
import com.javen.volley.VolleyErrorHelper;
import com.javen.volley.VolleyUtils;
import com.javen.volley.VolleyUtils.MyImageListener;
import com.javen.volley.request.XMLRequest;
import com.javen.volley.utils.ImageCacheManager;

public class MainActivity extends Activity {
	TextView textView;
	ImageView imageView;
	private String url="http://b.hiphotos.baidu.com/image/pic/item/377adab44aed2e7352c286388501a18b87d6faff.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.content);
        imageView=(ImageView) findViewById(R.id.id_imgView);
    }
    
    public void CacheImage(View view){
    	Bitmap defaultImage=BitmapFactory.decodeResource(getResources(), R.drawable.loading);
    	Bitmap errorImage=BitmapFactory.decodeResource(getResources(), R.drawable.load_error);
    	ImageCacheManager.loadImage(this, url, imageView, defaultImage, errorImage);
    }
    
    public void NetImage(View view)
    {
    	Toast.makeText(MainActivity.this, "Image onclick.....", 1).show();
    	VolleyUtils.ImageView(this, "http://b.hiphotos.baidu.com/image/pic/item/377adab44aed2e7352c286388501a18b87d6faff.jpg", new MyImageListener() {
			
			@Override
			public void setImageBitmap(final Bitmap bitmap) {
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						/*UI.showDialog(MainActivity.this, bitmap, new ItemOnListener() {
							
							@Override
							public void itemOnListener(View view) {
								Toast.makeText(MainActivity.this, "itemOnListener", 1).show();
								}
							
							@Override
							public void closeOnListener(View view) {
								Toast.makeText(MainActivity.this, "closeOnListener", 1).show();
							}
						});*/
						
						UI.showAnyDialog(getApplicationContext(), R.layout.bs_image_layout, bitmap, new ItemOnListener() {
							
							@Override
							public void itemOnListener(View view) {
								// TODO Auto-generated method stub
								Toast.makeText(MainActivity.this, "itemOnListener", 1).show();
							}
							
							@Override
							public void closeOnListener(View view) {
								// TODO Auto-generated method stub
								Toast.makeText(MainActivity.this, "closeOnListener", 1).show();
							}
						});
						
					}
				}, 1000*10);
			}
		});
    }
    public void xml(View view){
    	String url="http://flash.weather.com.cn/wmaps/xml/china.xml";
    	XMLRequest xmlRequest=new XMLRequest(url, new Response.Listener<XmlPullParser>() {

			@Override
			public void onResponse(XmlPullParser response) {
				try {  
                    int eventType = response.getEventType();  
                    while (eventType != XmlPullParser.END_DOCUMENT) {  
                        switch (eventType) {  
                        case XmlPullParser.START_TAG:  
                            String nodeName = response.getName();  
                            if ("city".equals(nodeName)) {  
                                String pName = response.getAttributeValue(0);  
                                Log.d("TAG", "pName is " + pName);  
                            }  
                            break;  
                        }  
                        eventType = response.next();  
                    }  
                } catch (XmlPullParserException e) {  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				textView.setText(VolleyErrorHelper.getMessage(error, MainActivity.this));
			}
		});
    	VolleyController.getInstance(this).addToRequestQueue(xmlRequest,"xmlRequest");
    	
    }
    
    public void Json(View view){
    	Toast.makeText(this, "onclick", 1).show();
    	json();
    }
    
    private void json(){
    	
    	final String url="http://192.168.1.115:8080/Demo/webad/volley";
    	//优先级有LOW，NORMAL,HIGH,IMMEDIATE
    	//设置请求的优先级别通过覆写getPrioriity()方法
    	final Priority priority = Priority.HIGH;
    	
    	//用来保存post参数
    	HashMap<String,String> params=new HashMap<String,String>();
    	params.put("userId","123189283");
    	
    	JsonObjectRequest req=new JsonObjectRequest(url,new JSONObject(params)
    			,new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				//正确响应时回调此函数
				textView.setText(response.toString());
			}
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				//正确响应时回调此函数
				System.out.println(VolleyErrorHelper.getMessage(error, MainActivity.this));
				textView.setText(VolleyErrorHelper.getMessage(error, MainActivity.this));
			}
		}){
    		//设置请求级别
    		@Override
    		public Priority getPriority() {
    			return priority;
    		}
    		//设置请求头
    		@Override
    		public Map<String, String> getHeaders() throws AuthFailureError {
    			// TODO Auto-generated method stub
    			return super.getHeaders();
    		}
    	};
    	//第一个代表超时时间：即超过20S认为超时，第三个参数代表最大重试次数，这里设置为1.0f代表如果超时，则不重试
    	req.setRetryPolicy(new DefaultRetryPolicy(20*1000, 1, 1.0f));
    	//可以通过setTag方法为每一个Request添加tag
    	req.setTag("My Tag");
    	//也可以在我们实现的添加进RequestQueue的时候设置
    	VolleyController.getInstance(this).addToRequestQueue(req,"My Tag");

//    	//取消Request
//    	VolleyController.getInstance(this).getRequestQueue().cancelAll("My Tag");
//    	//或者我们前面实现的方法
//    	VolleyController.getInstance(this).cancelPendingRequests("My Tag");
    }
}
