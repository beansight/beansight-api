package com.beansight.android.api;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.beansight.android.http.Http;
import com.beansight.android.http.Http.HttpRequestBuilder;
import com.beansight.android.models.InsightItem;
import com.beansight.android.models.InsightItemResponse;
import com.beansight.android.models.InsightListItem;
import com.beansight.android.models.InsightListItemResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BeansightApi {

	final private static HttpClient client = new DefaultHttpClient(); 
	private static String domain = "http://10.0.2.2:9000";
	
	
	public static InsightItem show(String accessToken, String id) throws NotAuthenticatedException {
		Log.v("BeansightApi.show", String.format("access_token=%s insightUniqueId=%s" , accessToken, id));
		
		InsightItemResponse insightItemResponse = null;
		String url = String.format("%s/api/insights/show", domain);
		try {
			String result = Http.get(url).use(client)
				.data("access_token", accessToken)
				.data("insightUniqueId", id)
				.asString();
			Gson gson = new Gson();
			insightItemResponse = gson.fromJson(result, InsightItemResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return insightItemResponse.getResponse();
	}
	
	public static List<InsightListItem> list(String accessToken, Integer from,
			Integer number, String sort, Integer category,
			String vote, String topic, Boolean closed, Boolean created) throws NotAuthenticatedException {
		
		String url = String.format("%s/api/insights/list", domain);
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
		
		InsightListItemResponse insightListResponse = null;
		try {
			String result = httpRequestbuilder.asString();
			Gson gson = new Gson();
//	        Type insightListItemType = new TypeToken<InsightListItemResponse>() {}.getType();
			insightListResponse = gson.fromJson(result, InsightListItemResponse.class);
		} catch (Throwable e) {
			e.printStackTrace();
		} 			
	        
		return insightListResponse.getResponse();
	}
	
}
