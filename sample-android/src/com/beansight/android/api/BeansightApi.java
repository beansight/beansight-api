package com.beansight.android.api;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.beansight.android.BeansightApplication;
import com.beansight.android.http.Http;
import com.beansight.android.http.Http.HttpRequestBuilder;
import com.beansight.android.models.Insight;
import com.beansight.android.models.InsightListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BeansightApi {

	final private static HttpClient client = new DefaultHttpClient(); 
	
	
	
	public static Insight show(String accessToken, String insightUniqueId) throws NotAuthenticatedException {
		Log.v("BeansightApi.show", String.format("access_token=%s insightUniqueId=%s" , accessToken, insightUniqueId));
		
		Insight insight = null;
		String url = String.format("%s/api/insights/show", BeansightApplication.domain);
		try {
			String result = Http.get(url).use(client)
				.data("access_token", accessToken)
				.data("insightUniqueId", insightUniqueId)
				.asString();
			Gson gson = new Gson();
			insight = gson.fromJson(result, Insight.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return insight;
	}
	
	public static List<InsightListItem> list(String accessToken, Integer from,
			Integer number, String sort, Integer category,
			String vote, String topic, Boolean closed, Boolean created) throws NotAuthenticatedException {
		
		String url = String.format("%s/api/insights/list", BeansightApplication.domain);
		HttpRequestBuilder httpRequestbuilder = Http.get(url).use(client).data("access_token", accessToken);
		if(from!=null) {
			httpRequestbuilder.data("from", from.toString());
		}
		if(number!=null) {
			httpRequestbuilder.data("number", number.toString());
		}
		if(sort!=null) {
			httpRequestbuilder.data("sort", sort);
		}
		if(category!=null) {
			httpRequestbuilder.data("category", category.toString());
		}
		if(vote!=null) {
			httpRequestbuilder.data("vote", vote);
		}
		if(topic!=null) {
			httpRequestbuilder.data("topic", topic);
		}
		if(closed!=null) {
			httpRequestbuilder.data("closed", closed.toString());
		}
		if(created!=null) {
			httpRequestbuilder.data("created", created.toString());
		}
		
		List<InsightListItem> insightListItems = null;
		try {
			String result = httpRequestbuilder.asString();
			Gson gson = new Gson();
	        Type insightListItemType = new TypeToken<List<InsightListItem>>() {}.getType();
	        insightListItems = gson.fromJson(result, insightListItemType);
		} catch (IOException e) {
			e.printStackTrace();
		} 			
	        
		return insightListItems;
	}
	
}
