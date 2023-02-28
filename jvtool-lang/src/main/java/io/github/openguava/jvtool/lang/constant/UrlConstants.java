package io.github.openguava.jvtool.lang.constant;

/**
 * Url相关常量
 * @author openguava
 *
 */
public class UrlConstants {

	/** http 协议 */
	public static final String URL_PROTOCOL_HTTP = "http";
	
	/** http 端口 */
	public static final int URL_PORT_HTTP = 80;
	
	/** http 前缀 */
	public static final String URL_PREFIX_HTTP = URL_PROTOCOL_HTTP + "://";
	
	/** https 协议 */
	public static final String URL_PROTOCOL_HTTPS = "https";
	
	/** https 端口 */
	public static final int URL_PORT_HTTPS = 443;
	
	/** https 前缀 */
	public static final String URL_PREFIX_HTTPS = URL_PROTOCOL_HTTPS + "://";
	
	/** websockt http 协议 */
	public static final String URL_PROTOCOL_WS = "ws";
	
	/** websocket http 端口 */
	public static final int URL_PORT_WS = 80;
	
	/** websocket http 前缀 */
	public static final String URL_PREFIX_WS = URL_PROTOCOL_WS + "://";
	
	/** websocket https 协议 */
	public static final String URL_PROTOCOL_WSS = "wss";
	
	/** websocket https 端口 */
	public static final int URL_PORT_WSS = 443;
	
	/** websocket https 前缀 */
	public static final String URL_PREFIX_WSS = URL_PROTOCOL_WSS + "://";
	
	/** ftp 协议 */
	public static final String URL_PROTOCOL_FTP = "ftp";
	
	/** ftp 端口 */
	public static final int URL_PORT_FTP = 21;
	
	/** ftp 前缀 */
	public static final String URL_PREFIX_FTP = URL_PROTOCOL_FTP + "://";
	
	/** rtsp 协议 */
	public static final String URL_PROTOCOL_RTSP = "rtsp";
	
	/** rtsp 端口 */
	public static final int URL_PORT_RTSP = 554;
	
	/** rtsp 前缀 */
	public static final String URL_PREFIX_RTSP = URL_PROTOCOL_RTSP + "://";
	
	/** rtsp TLS/SSL 协议 */
	public static final String URL_PROTOCOL_RTSPS = "rtsps";
	
	/** rtsp TLS/SSL 端口 */
	public static final int URL_PORT_RTSPS = 322;
	
	/** rtsp TLS/SSL 前缀 */
	public static final String URL_PREFIX_RTSPS = URL_PROTOCOL_RTSPS + "://";
	
	/** rtmp 协议 */
	public static final String URL_PROTOCOL_RTMP = "rtmp";
	
	/** rtmp 端口 */
	public static final int URL_PORT_RTMP = 1935;
	
	/** rtmp 前缀 */
	public static final String URL_PREFIX_RTMP = URL_PROTOCOL_RTMP + "://";
	
	public static final String URL_PROTOCOL_FILE = "file";
	
	public static final String URL_PROTOCOL_CLASSPATH = "classpath";
	
	public static final String URL_PROTOCOL_CLASSPATH_PREFIX = URL_PROTOCOL_CLASSPATH + ":";
}
