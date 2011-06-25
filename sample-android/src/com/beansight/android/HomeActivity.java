package com.beansight.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.beansight.android.api.BeansightApi;
import com.beansight.android.api.NotAuthenticatedException;
import com.beansight.android.models.InsightDetail;
import com.beansight.android.models.InsightListItem;
import com.beansight.android.models.InsightListItemResponse;

public class HomeActivity extends Activity {
	
	private ArrayAdapter arrayAdapter;
	private String accessToken;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
		SharedPreferences prefs = getSharedPreferences(BeansightApplication.BEANSIGHT_PREFS, 0);
		accessToken = prefs.getString("access_token", null);
        
		List<String> insightList = new ArrayList<String>();
		ListView insightListView = (ListView)findViewById(R.id.homeListInsights);
		arrayAdapter = new ArrayAdapter(this, R.layout.insight_item, insightList);
		insightListView.setAdapter(arrayAdapter);
		
		InsightListItemResponse insightListItemResponse = null;
		try {
		insightListItemResponse = BeansightApi.list(accessToken, null, null, null, null, null, null, null, null);
			if (insightListItemResponse != null && !insightListItemResponse.getMeta().isAuthenticated()) {
				startActivity( new Intent(this, WebViewActivity.class) );
			}
		} catch (IOException e) {
			e.printStackTrace();
			// FIXME should handle this better ...
		}
		
		
		if (insightListItemResponse != null && insightListItemResponse.getResponse() != null) {
			for (InsightListItem item : insightListItemResponse.getResponse()) {
				this.arrayAdapter.add(item.getContent());
			}
			this.arrayAdapter.notifyDataSetChanged();
		}

    }
   
}