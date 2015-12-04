package com.javen.test;

import com.javen.utils.Res;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class UI {
	 //定义浮动窗口布局  
    static RelativeLayout mFloatLayout;  
    static WindowManager.LayoutParams wmParams;  
    //创建浮动窗口设置布局参数的对象  
     static WindowManager mWindowManager;  
    
    
	public interface ItemOnListener{
		void itemOnListener(View view);
		void closeOnListener(View view);
	}
	
	
	public static void showAnyDialog(Context context,int resource,Bitmap bm,final ItemOnListener listener){
		
		
		wmParams = new WindowManager.LayoutParams();  
        //获取的是WindowManagerImpl.CompatModeWrapper  
        mWindowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);  
        //设置window type  
        wmParams.type = LayoutParams.TYPE_PHONE; 
       /* int sdkApi=Integer.parseInt(android.os.Build.VERSION.SDK);
		 if (sdkApi>=19) {
			 wmParams.type = LayoutParams.TYPE_TOAST;   
		 }else {
			 wmParams.type = LayoutParams.TYPE_PHONE;  
		}*/
        
        
        //设置图片格式，效果为背景透明  
        wmParams.format = PixelFormat.RGBA_8888;   
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;        
        //调整悬浮窗显示的停靠位置为左侧置顶  
        wmParams.gravity = Gravity.CENTER;         
  
        //设置悬浮窗口长宽数据    
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;  
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  
        
        LayoutInflater inflater = LayoutInflater.from(context);  
        //获取浮动窗口视图所在布局  
        mFloatLayout = (RelativeLayout) inflater.inflate(resource, null);  
        //添加mFloatLayout  
        mWindowManager.addView(mFloatLayout, wmParams);  
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,  
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec  
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));  
        Button closeButton = (Button) mFloatLayout.findViewById(Res.id(context, "bs_id_image_close"));
		 ImageView imageView = (ImageView) mFloatLayout.findViewById(Res.id(context, "bs_id_image"));
		 imageView.setImageBitmap(bm);
		 
		
		 imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.itemOnListener(v);
				 mWindowManager.removeView(mFloatLayout);  
			}
		});
		 closeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.closeOnListener(v);
				 mWindowManager.removeView(mFloatLayout); 
			}
		});
	}
	
	public static void showDialog(Context context,Bitmap bm,ItemOnListener listener){
		showDialog(context.getApplicationContext(), Res.layout(context, "bs_image_layout"), Res.style(context, "bs_transparent_dialog"),bm, listener);
	}
	
	public static void showDialog(Context context,int resource,int style,Bitmap bm,final ItemOnListener listener){
		 View view=LayoutInflater.from(context).inflate(resource, null);
		 final AlertDialog dialog = new AlertDialog.Builder(context,style).create();
		 int sdkApi=Integer.parseInt(android.os.Build.VERSION.SDK);
		 if (sdkApi>=19) {
			 dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
		 }else {
			 dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
		}
		 dialog.show();
		
		 WindowManager.LayoutParams params = dialog.getWindow().getAttributes();// 得到属性
		 params.gravity = Gravity.CENTER;// 显示在中间
		
		 DisplayMetrics metrics=  context.getResources().getDisplayMetrics();
		 
		 int dispayWidth =metrics.widthPixels;
		 int dispayHeight =metrics.heightPixels;
		 
		 params.width=(int)(dispayWidth * 0.8);
		 params.height=(int)(dispayHeight* 0.5);
		 dialog.setCanceledOnTouchOutside(false);
		 dialog.setCancelable(false);
		
		 dialog.getWindow().setAttributes(params);// 設置屬性
		 dialog.getWindow().setContentView(view);// 把自定義view加上去
		 Button closeButton = (Button) view.findViewById(Res.id(context, "bs_id_image_close"));
		 ImageView imageView = (ImageView) view.findViewById(Res.id(context, "bs_id_image"));
		 imageView.setImageBitmap(bm);
		 view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.itemOnListener(v);
				dialog.dismiss();
			}
		});
		 closeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.closeOnListener(v);
				dialog.dismiss();
			}
		});
	}
	
	
	/**
	 * dip 转换成px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	public static float getDensity(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		return density;
	}
	
	
}
