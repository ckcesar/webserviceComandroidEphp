package com.example.n1e.webservice.conexao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ConexaoHttpClient {

	public static final int HTTP_TIMEOUT = 30 * 1000;
	private static HttpClient httpClient;

	private static HttpClient getHttpClient(){
		if (httpClient == null){
			httpClient = new DefaultHttpClient();
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);

		}
		return httpClient;
	}


	public static String executaHttpPost(String url, ArrayList<NameValuePair> ParametroPost) throws Exception{

		BufferedReader bufferedReader = null;

		try{
			HttpClient Client = getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(ParametroPost);
			httpPost.setEntity(formEntity);
			HttpResponse httpResponse = Client.execute(httpPost);
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line + LS);
			}

			bufferedReader.close();
			String resultado = stringBuffer.toString();
			return resultado;
		}
		finally{
			if(bufferedReader != null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}


	}
	

}
