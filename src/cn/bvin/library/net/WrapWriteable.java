package cn.bvin.library.net;

import java.io.File;

import org.apache.http.HttpEntity;

/**
 * 
 * @ClassName: WrapWriteable 
 * @Description: 写入类型封装类
 * @author: Bvin
 * @date: 2015年3月20日 下午4:47:20
 */
public class WrapWriteable {

	private byte[] buffer;
	
	private File file;
	
	private HttpEntity httpEntity;

	/**
	 * 一次性写如一个byte数组，适合数据不大的写入情景
	 * @param buffer
	 */
	public WrapWriteable(byte[] buffer) {
		super();
		this.buffer = buffer;
	}

	/**
	 * TODO 传string可能需要区分是用HttpClient还是用URLConection
	 * @param buffer
	 */
	public WrapWriteable(String buffer) {
		this(buffer.getBytes());
	}
	
	/**
	 * 写入文件类型
	 * @param file
	 */
	public WrapWriteable(File file) {
		super();
		this.file = file;
	}
	
	/**
	 * 写入HttpEntity类型
	 * @param httpEntity
	 */
	public WrapWriteable(HttpEntity httpEntity) {
		super();
		this.httpEntity = httpEntity;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public File getFile() {
		return file;
	}

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	
}
