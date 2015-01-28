package org.ajaybe.biu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BiuActivity extends ListActivity {

	private SimpleAdapter mAdapter;
	private List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_biu);

		mAdapter = new SimpleAdapter(this, getData(), R.layout.friend_list, new String[] {"portrait","user_name"}, new int[] {R.id.portrait, R.id.user_name});
        setListAdapter(mAdapter);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
	}
	
	private List<Map<String, Object>> getData() {
    	int count = getIntent().getIntExtra("count", 0);
    	String[] names = getIntent().getStringArrayExtra("users");
    	for (int i = 0; i < count; ++i) {
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("portrait", R.drawable.pt_boy);
			map.put("user_name", names[i]);
			
			mData.add(map);
    	}
    
		return mData;
	}	

}
