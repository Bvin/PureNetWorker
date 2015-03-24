package cn.bvin.library.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @ClassName: NetWoker 
 * @Description: 负责连接网络，从网络上传、下载数据
 * @author: Bvin
 * @date: 2015年3月19日 下午5:22:42
 */
public class NetWoker {

	
	private void postRequest(String url,WrapWriteable param) {
		//如果API在Gingerbread 2.3以上就用 URLConnection
		if (param.getHttpEntity()!=null) {
			postWithHttpClient(url, param.getHttpEntity());
		} else {
			postWithHttpURLConnection(url, param);
		}
	} 
	
	
	/**
	 * HttpClient方式的POST方法，以HttpEntity实体的方式读写
	 * @param url
	 * @param entity
	 * @return: WrapResponces
	 */
	private WrapResponces postWithHttpClient(String url,HttpEntity entity) {
		try {
			//1.构建一个PostRequest
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			//2.构建HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			//3.执行请求得到一个HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//4.判断状态码，处理返回数据
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity outEntity = httpResponse.getEntity();
				return new WrapResponces(outEntity.getContent(), outEntity.getContentLength());
			} else {//状态码!=200
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * HttpURLConnection的POST方法，以流的方式读写
	 * @param urlStr URL字符串
	 * @param buffer 要写入的数据
	 * @return: WrapResponces
	 */
	private WrapResponces postWithHttpURLConnection(String urlStr, WrapWriteable writeable) {
		try {
			//1.构建URL
			URL url = new URL(urlStr);
			//2.执行请求得到HttpURLConnection
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			//3.获取一个输出流，用于写入数据 
            OutputStream outputStream = httpConn.getOutputStream();  
            if (writeable.getFile()!=null) {//写文件
				write(outputStream, writeable.getFile());
			} else if (writeable.getBuffer()!=null) {//写Byte数组
				write(outputStream, writeable.getBuffer());
			} else {
				outputStream.close();
				return null;
			}
            outputStream.close();
			if (httpConn.getResponseCode() == HttpStatus.SC_OK) {
				return new WrapResponces(httpConn.getInputStream(), httpConn.getContentLength());
			} else {//状态码!=200
				return null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void uploadWithEntity(String url) {
		//TODO 以MultipartEntity的方式去上传数据
	}
	
	
	
	
	private void write(OutputStream outputStream,File file) {
		try {
			InputStream is = new FileInputStream(file);
			byte[] buffer = new byte[1024];    
            int len = 0;    
            while ((len = is.read(buffer)) != -1) {    
            	write(outputStream, buffer, 0, len);
            }    
            is.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write(OutputStream outputStream, byte[] buffer, int offset, int count) {
		try {
			outputStream.write(buffer, offset, count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void write(OutputStream outputStream, byte[] buffer) {
		try {
			outputStream.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
