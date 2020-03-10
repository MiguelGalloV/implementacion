package utils.api.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;

import utils.api.api.UtilsApi;

@Component(service = UtilsApi.class)
public class UtilsApiImpl implements UtilsApi {

	@Override
	public String serviceGetMethod(String url, Map<String, String> parametros) throws IOException {

		// Procesar parametros
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : parametros.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

			// Adicion de parametros a url
			URIBuilder builder = new URIBuilder().setPath(url).setParameters(params);

			URI uri = builder.build();

			// Creating a HttpGet object
			HttpGet httpGet = new HttpGet(uri);

			// add request headers
			httpGet.addHeader("x-stf-auth", "1");
			httpGet.setHeader("Content-type", "application/json; charset=utf-8");

			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {

					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			String responseBody = httpClient.execute(httpGet, responseHandler);
			return responseBody;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String servicePostMethod(String url, String body) throws IOException {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			StringEntity stringEntity = new StringEntity(body);
			httpPost.setEntity(stringEntity);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {

					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			String responseBody = httpClient.execute(httpPost, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);

			return responseBody;
		}
	}

	@Override
	public String decodeJwtMethod(String token) throws IOException {
		String jwtToken = token;

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		String base64EncodedSignature = split_string[2];

		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));

		String body = new String(base64Url.decode(base64EncodedBody));

		return body;
	}

	@Override
	public String servicePutMethod(String url, String userID, String body) throws IOException {
		
		String userID1= "5e430c3653e48445c3fe4c9d";
		
				try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

					// Adicion de parametros a url
					URIBuilder builder = new URIBuilder().setPath(url).setCustomQuery(userID1).setPath("actualizar-asignacion");

					URI uri = builder.build();

					// Creating a HttpGet object
					HttpPut httpPut = new HttpPut(uri);

					// add request headers
					httpPut.addHeader("x-stf-auth", "1");
					httpPut.setHeader("Content-type", "application/json; charset=utf-8");
					
	
					StringEntity stringEntity = new StringEntity(body); 
					 
					httpPut.setEntity(stringEntity);
					
					// Create a custom response handler
					ResponseHandler<String> responseHandler = response -> {
						int status = response.getStatusLine().getStatusCode();
						if (status >= 200 && status < 300) {

							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status);
						}
					};
					String responseBody = httpClient.execute(httpPut, responseHandler);
					System.out.println("----------------------------------------");
					System.out.println(responseBody);

					return responseBody;
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "";
				}
	}
}
