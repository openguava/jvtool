package io.github.openguava.jvtool.lang.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.net.ServerSocketFactory;

import io.github.openguava.jvtool.lang.result.IResult;
import io.github.openguava.jvtool.lang.result.R;
import io.github.openguava.jvtool.lang.constant.HttpConstants;
import io.github.openguava.jvtool.lang.exception.UtilException;

/**
 * 网络工具类
 * 
 * @author openguava
 *
 */
public class NetworkUtils {

	public static final String HOST_LOCALHOST = "localhost";

	public static final int PORT_RANGE_MIN = 1024;

	public static final int PORT_RANGE_MAX = 65535;

	protected NetworkUtils() {

	}

	/**
	 * 获取网络接口集合
	 * 
	 * @return
	 */
	public static List<NetworkInterface> getNetworkInterfaces() {
		List<NetworkInterface> networkInterfaces = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			if (ifaces == null) {
				return networkInterfaces;
			}
			while (ifaces.hasMoreElements()) {
				networkInterfaces.add(ifaces.nextElement());
			}
		} catch (Exception e) {
			LogUtils.debug(NetworkUtils.class, e.getMessage(), e);
		}
		return networkInterfaces;
	}

	/**
	 * 获取网卡地址列表
	 * 
	 * @return
	 */
	public static List<InetAddress> getInetAddresses() {
		// addresses
		List<InetAddress> addresses = new ArrayList<>();
		// ifaces
		List<NetworkInterface> ifaces = getNetworkInterfaces();
		if (ifaces.isEmpty()) {
			return addresses;
		}
		for (NetworkInterface iface : ifaces) {
			for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
				InetAddress inetAddr = inetAddrs.nextElement();
				// 忽略回环地址
				if (inetAddr.isLoopbackAddress()) {
					continue;
				}
				addresses.add(inetAddr);
			}
		}
		if (addresses.isEmpty()) {
			try {
				addresses.add(InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				LogUtils.debug(NetworkUtils.class, e.getMessage(), e);
			}
		}
		return addresses;
	}

	/**
	 * 获得指定地址信息中的MAC地址，使用分隔符“-”
	 * 
	 * @param inetAddress {@link InetAddress}
	 * @return
	 */
	public static String getMacAddress(InetAddress inetAddress) {
		return getMacAddress(inetAddress, "-");
	}

	/**
	 * 获得指定地址信息中的MAC地址
	 * 
	 * @param inetAddress {@link InetAddress}
	 * @param separator   分隔符，推荐使用“-”或者“:”
	 * @return
	 */
	public static String getMacAddress(InetAddress inetAddress, String separator) {
		if (null == inetAddress) {
			return null;
		}
		byte[] mac;
		try {
			mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
		} catch (SocketException e) {
			throw new UtilException(e);
		}
		if (null != mac) {
			final StringBuilder sb = new StringBuilder();
			String s;
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append(separator);
				}
				// 字节转换为整数
				s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 主机是否可以访问
	 * 
	 * @param host    主机
	 * @param timeout 超时时间(毫秒)
	 * @return
	 */
	public static boolean isHostReachable(String host, int timeout) {
		try {
			return InetAddress.getByName(host).isReachable(timeout);
		} catch (IOException e) {
			LogUtils.debug(NetworkUtils.class, e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 主机端口是否可以(tcp)连接
	 * 
	 * @param host    主机
	 * @param port    端口号
	 * @param timeout 超时时间(毫秒)
	 * @return
	 */
	public static boolean isHostConnectable(String host, int port, Integer timeout) {
		try (Socket socket = new Socket();) {
			if (timeout != null && timeout.intValue() > 0) {
				socket.connect(new InetSocketAddress(host, port), timeout.intValue());
			} else {
				socket.connect(new InetSocketAddress(host, port));
			}
			// 释放socket
			IoUtils.close(socket);
			// 返回
			return true;
		} catch (Exception e) {
			LogUtils.debug(NetworkUtils.class, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 是否为有效的端口
	 * 
	 * @param port 端口号
	 * @return
	 */
	public static boolean isValidPort(int port) {
		// 有效端口是0～65535
		return port >= 0 && port <= 0xFFFF;
	}

	/**
	 * 判断 tcp 端口是否可用
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isTcpPortAvailable(String host, int port) {
		try {
			if (StringUtils.isEmpty(host)) {
				host = HOST_LOCALHOST;
			}
			ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1,
					InetAddress.getByName(host));
			serverSocket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 判断 udp 端口是否可用
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isUdpPortAvailable(String host, int port) {
		try {
			if (StringUtils.isEmpty(host)) {
				host = HOST_LOCALHOST;
			}
			DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName(host));
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 发送tcp报文
	 * 
	 * @param host           主机
	 * @param port           端口
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    数据接收超时时间
	 * @param writeBytes     发送的数据
	 * @return
	 */
	public static IResult<byte[]> sendTcpPacket(String host, int port, Integer connectTimeout, Integer readTimeout,
			final byte[] writeBytes) {
		ByteArrayOutputStream readStream = new ByteArrayOutputStream();
		IResult<String> retSend = sendTcpPacket(host, port, connectTimeout, readTimeout, writeBytes, readStream);
		if (retSend.isError()) {
			IoUtils.close(readStream);
			return R.setFail(retSend.getMsg());
		}
		byte[] bytes = readStream.toByteArray();
		IoUtils.close(readStream);
		return R.setOk(bytes, "");
	}

	/**
	 * 发送tcp报文
	 * 
	 * @param host           主机
	 * @param port           端口
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    数据接收超时时间
	 * @param writeBytes     发送的数据
	 * @param readStream     读取输出流
	 * @return
	 */
	public static IResult<String> sendTcpPacket(String host, int port, Integer connectTimeout, Integer readTimeout,
			final byte[] writeBytes, final OutputStream readStream) {
		return sendTcpPacket(host, port, connectTimeout, readTimeout, writeBytes, IoUtils.toReadFunc(readStream));
	}

	/**
	 * 发送tcp报文
	 * 
	 * @param host           主机
	 * @param port           端口
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    数据接收超时时间
	 * @param writeBytes     发送的数据
	 * @param readFunc     数据读取回调
	 * @return
	 */
	public static IResult<String> sendTcpPacket(String host, int port, Integer connectTimeout, Integer readTimeout,
			final byte[] writeBytes, Function<InputStream, IResult<?>> readFunc) {
		return sendTcpPacket(host, port, connectTimeout, readTimeout,
				IoUtils.toWriteFunc(new ByteArrayInputStream(writeBytes), true, true), readFunc);
	}

	/**
	 * 发送tcp报文
	 * 
	 * @param host           主机
	 * @param port           端口
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    数据接收超时时间
	 * @param writeFunc    数据发送回调
	 * @param readFunc     数据读取回调
	 * @return
	 */
	public static IResult<String> sendTcpPacket(String host, int port, Integer connectTimeout, Integer readTimeout,
			Function<OutputStream, IResult<?>> writeFunc, Function<InputStream, IResult<?>> readFunc) {
		try (Socket socket = new Socket();) {
			// readTimeout
			if (readTimeout != null && readTimeout.intValue() > 0) {
				socket.setSoTimeout(readTimeout);
			}
			SocketAddress socketAddress = new InetSocketAddress(host, port);
			// connectTimeout
			if (connectTimeout != null && connectTimeout.intValue() > 0) {
				socket.connect(socketAddress, connectTimeout.intValue());
			} else {
				socket.connect(socketAddress);
			}
			return sendTcpPacket(socket, readTimeout, writeFunc, readFunc);
		} catch (Exception e) {
			return R.setFail("请求失败:" + e.getMessage());
		}
	}

	/**
	 * 发送tcp报文
	 * 
	 * @param host           主机
	 * @param port           端口
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    数据接收超时时间
	 * @param writeFunc    数据发送回调
	 * @param readFunc     数据读取回调
	 * @return
	 */
	public static IResult<String> sendTcpPacket(Socket socket, Integer readTimeout,
			Function<OutputStream, IResult<?>> writeFunc, Function<InputStream, IResult<?>> readFunc) {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			// readTimeout
			if (readTimeout != null && readTimeout.intValue() > 0) {
				socket.setSoTimeout(readTimeout);
			}
			// outputStream
			if (writeFunc != null) {
				outputStream = socket.getOutputStream();
				IResult<?> ret = writeFunc.apply(outputStream);
				if (!ret.isSuccess()) {
					throw new IOException(ret.getMsg());
				}
			}
			// inputStream
			if (readFunc != null) {
				inputStream = socket.getInputStream();
				IResult<?> ret = readFunc.apply(inputStream);
				if (!ret.isSuccess()) {
					throw new IOException(ret.getMsg());
				}
			}
			// return
			return R.setOk();
		} catch (SocketTimeoutException e) {
			LogUtils.error(NetworkUtils.class, "", e);
			return R.setFail("连接服务器超时");
		} catch (SocketException e) {
			LogUtils.error(NetworkUtils.class, "", e);
			return R.setFail(e.getMessage());
		} catch (IOException e) {
			return R.setFail("访问失败:" + e.getMessage());
		} catch (Exception e) {
			return R.setFail("请求失败:" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送 udp 报文
	 * 
	 * @param host
	 * @param port
	 * @param bytes
	 * @return
	 */
	public static IResult<byte[]> sendUdpPacket(String host, int port, byte[] bytes) {
		try (DatagramSocket socket = new DatagramSocket();) {
			return sendUdpPacket(socket, host, port, bytes);
		} catch (Exception e) {
			LogUtils.error(NetworkUtils.class, e.getMessage(), e);
			return R.setFail(e.getMessage());
		}
	}

	/**
	 * 发送 udp 报文
	 * 
	 * @param socket
	 * @param host
	 * @param port
	 * @param bytes
	 * @return
	 */
	public static IResult<byte[]> sendUdpPacket(DatagramSocket socket, String host, int port, byte[] bytes) {
		try {
			SocketAddress socketAddress = new InetSocketAddress(host, port);
			socket.send(new DatagramPacket(bytes, bytes.length, socketAddress));
			return R.setOk(bytes, "");
		} catch (Exception e) {
			LogUtils.error(NetworkUtils.class, e.getMessage(), e);
			return R.setFail(e.getMessage());
		}
	}

	/**
	 * 发送 http GET请求
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionConsumer 连接动作
	 * @return
	 */
	public static IResult<byte[]> sendHttpGetPacket(String httpUrl, final Consumer<HttpURLConnection> connectionConsumer) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IResult<Integer> ret = sendHttpGetPacket(httpUrl, connectionConsumer, outputStream);
		if (ret.isError()) {
			IoUtils.close(outputStream);
			return R.setFail(ret.getMsg());
		}
		byte[] bytes = outputStream.toByteArray();
		IoUtils.close(outputStream);
		return R.setOk(bytes, "");
	}

	/**
	 * 发送 http GET请求报文
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionConsumer 连接动作
	 * @param readStream       读取输出流
	 * @return
	 */
	public static IResult<Integer> sendHttpGetPacket(String httpUrl, final Consumer<HttpURLConnection> connectionConsumer,
			final OutputStream readStream) {
		return sendHttpPacket(httpUrl, new Consumer<HttpURLConnection>() {

			@Override
			public void accept(HttpURLConnection connection) {
				try {
					connection.setDoOutput(false);
					connection.setDoInput(true);
					connection.setRequestMethod(HttpConstants.HTTP_REQUEST_METHOD_GET);
					if (connectionConsumer != null) {
						connectionConsumer.accept(connection);
					}
				} catch (Exception e) {
					LogUtils.error(NetworkUtils.class, e.getMessage(), e);
				}
			}
		}, null, readStream);
	}

	/**
	 * 发送 http POST请求报文
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionConsumer 连接动作
	 * @param writeStream      写入输入流
	 * @return
	 */
	public static IResult<byte[]> sendHttpPostPacket(String httpUrl, final Consumer<HttpURLConnection> connectionConsumer,
			final InputStream writeStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IResult<Integer> ret = sendHttpPostPacket(httpUrl, connectionConsumer, writeStream, outputStream);
		if (ret.isError()) {
			IoUtils.close(outputStream);
			return R.setFail(ret.getMsg());
		}
		byte[] bytes = outputStream.toByteArray();
		IoUtils.close(outputStream);
		return R.setOk(bytes, null);
	}

	/**
	 * 发送 http POST请求报文
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionConsumer 连接动作
	 * @param writeStream      写入输入流
	 * @param readStream       读取输出流
	 * @return
	 */
	public static IResult<Integer> sendHttpPostPacket(String httpUrl, final Consumer<HttpURLConnection> connectionConsumer,
			final InputStream writeStream, final OutputStream readStream) {
		return sendHttpPacket(httpUrl, new Consumer<HttpURLConnection>() {

			@Override
			public void accept(HttpURLConnection connection) {
				try {
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setRequestMethod(HttpConstants.HTTP_REQUEST_METHOD_POST);
					if (connectionConsumer != null) {
						connectionConsumer.accept(connection);
					}
				} catch (Exception e) {
					LogUtils.error(NetworkUtils.class, e.getMessage(), e);
				}
			}
		}, writeStream, readStream);
	}

	/**
	 * 发送 http 报文
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionConsumer 连接动作
	 * @param writeStream      写入输入流
	 * @param readStream       读取输出流
	 * @return
	 */
	public static IResult<Integer> sendHttpPacket(String httpUrl, Consumer<HttpURLConnection> connectionConsumer,
			final InputStream writeStream, final OutputStream readStream) {
		return sendHttpPacket(httpUrl, connectionConsumer, IoUtils.toWriteFunc(writeStream),
				IoUtils.toReadFunc(readStream));
	}

	/**
	 * 发送 http 报文
	 * 
	 * @param httpUrl          http链接地址
	 * @param connectionAction 连接动作
	 * @param connectionConsumer      写入请求动作
	 * @param readFunc       读取响应动作
	 * @return
	 */
	public static IResult<Integer> sendHttpPacket(String httpUrl, Consumer<HttpURLConnection> connectionConsumer,
			Function<OutputStream, IResult<?>> writeFunc, Function<InputStream, IResult<?>> readFunc) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(httpUrl);
			connection = (HttpURLConnection) url.openConnection();
			// 设置是否读入
			connection.setDoInput(true);
			// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
			connection.setInstanceFollowRedirects(true);
			if (connectionConsumer != null) {
				connectionConsumer.accept(connection);
			}
			return sendHttpPacket(connection, writeFunc, readFunc);
		} catch (Exception e) {
			LogUtils.error(NetworkUtils.class, e.getMessage(), e);
			return R.setFail(e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 发送 http 报文
	 * 
	 * @param connection  http连接
	 * @param writeStream 写入输入流
	 * @param readStream  读取输出流
	 * @return
	 */
	public static IResult<Integer> sendHttpPacket(HttpURLConnection connection, final InputStream writeStream,
			final OutputStream readStream) {
		return sendHttpPacket(connection, IoUtils.toWriteFunc(writeStream), IoUtils.toReadFunc(readStream));
	}

	/**
	 * 发送 http 报文
	 * 
	 * @param connection  http连接对象
	 * @param writeAction 写入请求动作
	 * @param readAction  读取响应动作
	 * @return
	 */
	public static IResult<Integer> sendHttpPacket(HttpURLConnection connection,
			Function<OutputStream, IResult<?>> writeAction, Function<InputStream, IResult<?>> readAction) {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			// 执行 http 连接
			connection.connect();
			// 写入请求数据
			if (connection.getDoOutput() && writeAction != null) {
				outputStream = connection.getOutputStream();
				writeAction.apply(outputStream);
				outputStream.flush();
				outputStream.close();
				outputStream = null;
			}
			// 判断结果
			int statusCode = connection.getResponseCode();
			if (statusCode != HttpConstants.HTTP_STATUSCODE_OK) {
				return R.setFail(connection.getResponseMessage());
			}
			if (!connection.getDoInput()) {
				return R.setOk();
			}
			// 读取响应数据
			inputStream = connection.getInputStream();
			if (readAction != null) {
				readAction.apply(inputStream);
			}
			inputStream.close();
			inputStream = null;
			// 返回结果
			return R.setOk(statusCode, connection.getResponseMessage());
		} catch (Exception e) {
			LogUtils.error(NetworkUtils.class, e.getMessage(), e);
			return R.setFail(e.getMessage());
		} finally {
			if (outputStream != null) {
				IoUtils.close(outputStream);
			}
			if (inputStream != null) {
				IoUtils.close(inputStream);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(sendHttpGetPacket("https://www.baidu.com/", null).getData());
	}
}
