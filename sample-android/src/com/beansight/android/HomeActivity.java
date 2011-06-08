package com.beansight.android;

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
import com.beansight.android.models.Insight;
import com.beansight.android.models.InsightListItem;

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
		
		List<InsightListItem> insightListItems = null;
		try {
			insightListItems = BeansightApi.list(accessToken, null, null, null, null, null, null, null, null);
		} catch (NotAuthenticatedException e) {
			startActivity( new Intent(this, WebViewActivity.class) );
		}
		
		
		if (insightListItems != null) {
			for (InsightListItem item : insightListItems) {
				this.arrayAdapter.add(item.getContent());
			}
			this.arrayAdapter.notifyDataSetChanged();
		}

    }
   
}