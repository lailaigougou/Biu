package org.ajaybe.biu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ChatReceiver extends BroadcastReceiver {
	
	private static String TAG = "ChatReceiver";
	private static LinkedList<IObserver> mObservers = new LinkedList<IObserver>();

	@Override
	public void onReceive(Context ctx, Intent intent) {
		Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	
            System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	String title   = bundle.getString(JPushInterface.EXTRA_TITLE);
        	String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        	String extras  = bundle.getString(JPushInterface.EXTRA_EXTRA);
        	String type    = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        	
        	try {
	        	Log.e("ChatReceiver", title + " " + message + " " + extras + " " + type);
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        	JSONObject object = new JSONObject(extras);
	        	
	        	ChatEntity entity = new ChatEntity(object.getString("username"), sdf.format(new Date()), message, true);
				for (IObserver ob : mObservers) {
					ob.onReceive(entity);
				}
        	} catch (JSONException e) {
        		e.printStackTrace();
        	}
            System.out.println("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            String title   = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        	String extras  = bundle.getString(JPushInterface.EXTRA_EXTRA);
        	
        	if (title.equals("BiuChat")) {
        		try {
					JSONObject object = new JSONObject(extras);
					Intent i = new Intent(ctx, ChatActivity.class);  //自定义打开的界面
					i.putExtra("title", object.getString("username"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                ctx.startActivity(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
        		
        	} else if (title.equals("Biu")) {
        		try {
					JSONObject object = new JSONObject(extras);
					Intent i = new Intent(ctx, BiuedActivity.class);  //自定义打开的界面
					i.putExtra("username", object.getString("username"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                ctx.startActivity(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
        		
        	}
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }

	}

	public static void registerObserver(IObserver ob) {
		mObservers.add(ob);
	}
	
	public static void unregisterObserver(IObserver ob) {
		mObservers.remove(ob);
	}
	
	public interface IObserver {
		public void onReceive(ChatEntity entity);
	}
	
}
