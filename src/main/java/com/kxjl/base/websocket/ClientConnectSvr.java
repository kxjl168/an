package com.kxjl.base.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.kxjl.base.util.ByteIntChange;
import com.kxjl.base.util.aes.AESEncryptUtil;
import com.kxjl.video.pojo.ClientInfo;
import com.kxjl.video.service.ClientService;
import com.kxjl.video.service.OnlineSeatsService;

/**
 * 线程池处理PC网络出口TCP长连接请求
 * 
 * @author Administrator
 *
 *
 * @WebServlet(urlPatterns = "/clientConnectSvr")
 */

@PropertySource("classpath:project.properties")
@Component
public class ClientConnectSvr {

	@Value("${PC_EXPORT_PORT}")
	private String PC_EXPORT_PORT;

	@Value("${MEDIA_SERVER_URL}")
	private String MEDIA_SERVER_URL;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823073680488148194L;

	private int port = 18000;
	private final int POOL_SIZE = 20;// 单个CPU线程池大小

	private static Logger logger = Logger.getLogger(ClientConnectSvr.class);

	@Autowired
	ClientService clientService;

	@Autowired
	OnlineSeatsService onlineSeatsService;

	public void startSocketServerAndOther() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					String pc_export_port = PC_EXPORT_PORT;
					try {
						port = Integer.valueOf(pc_export_port);
					} catch (Exception e) {

					}
					SocketServer test = new SocketServer();
					test.initServer(port);
					test.listen();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();

		// 检测在线坐席心跳是否超时
		new Thread(new OnlineSeatsClientCheck()).start();

		new Thread(new DistributionOnlineSeats()).start();
		SocketClient.getInstance().setOnlineSeatsSeervice(MEDIA_SERVER_URL, onlineSeatsService);
	}

	// boolean sendflag = true;

	/**
	 * Nio socket服务器，事件驱动方式，用线程池进行多线程处理读取事件
	 */
	public class SocketServer {
		// 线程池
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		// 通道管理器
		private Selector selector;

		/**
		 * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
		 * 
		 * @param port 绑定的端口号
		 * @throws IOException
		 */
		public void initServer(int port) throws IOException {
			// 获得一个ServerSocket通道
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			// 设置通道为非阻塞
			serverChannel.configureBlocking(false);
			// 将该通道对应的ServerSocket绑定到port端口
			serverChannel.socket().bind(new InetSocketAddress(port));
			// 获得一个通道管理器
			this.selector = Selector.open();
			// 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
			// 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			logger.info("服务端启动成功！");
		}

		/**
		 * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
		 * 
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		public void listen() throws IOException {
			// 轮询访问selector
			while (true) {
				// 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
				selector.select();
				// 获得selector中选中的项的迭代器，选中的项为注册的事件
				Iterator ite = this.selector.selectedKeys().iterator();
				while (ite.hasNext()) {
					SelectionKey key = (SelectionKey) ite.next();
					// 删除已选的key,以防重复处理
					ite.remove();
					// 客户端请求连接事件
					try {
						if (key.isAcceptable()) {
							ServerSocketChannel server = (ServerSocketChannel) key.channel();
							// 获得和客户端连接的通道
							SocketChannel channel = server.accept();
							// 设置成非阻塞
							channel.configureBlocking(false);
							// 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
							channel.register(this.selector, SelectionKey.OP_READ);
							// 获得了可读的事件

							ClientInfo clientInfo = new ClientInfo();
							clientInfo.setChannel(channel);
							clientInfo.setType(0);
							clientInfo.setMsgTime(System.currentTimeMillis());
							SocketClient.getInstance().putSocketInfo(channel, clientInfo, 0);
						} else if (key.isReadable()) {
							// 取消可读触发标记
							key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
							// 加入线程池
							pool.execute(new ThreadHandlerChannel(key));
						}
					} catch (CancelledKeyException ex) {
						continue;
					}
				}
			}
		}
	}

	/**
	 * 用多线程处理读取客户端发来的信息的事件
	 * 
	 * @param SelectionKey
	 * @throws IOException
	 */
	class ThreadHandlerChannel extends Thread {
		private SelectionKey key;

		ThreadHandlerChannel(SelectionKey key) {
			this.key = key;
		}

		@Override
		public void run() {
			// 服务器可读取消息:得到事件发生的Socket通道
			SocketChannel channel = (SocketChannel) key.channel();
			// 创建读取的缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			// 把读取到的缓冲区内容存入到流内，到最后一次性取出来，建议定义自定义协议先传内容长度然后再传内容，就可以根据长度建立对应长度缓冲区然后读取数据
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				int size = 0;
				while ((size = channel.read(buffer)) > 0) {
					buffer.flip();
					baos.write(buffer.array(), 0, size);
					buffer.clear();
				}
				baos.close();
				if (baos.size() > 0) {
					byte[] buf = baos.toByteArray();
					int index = 0;
					byte[] data_value = new byte[4];
					for (int i = 0; i < 4; i++) {
						data_value[i] = buf[i + index];
					}
					// 获取报文长度
					int length = ByteIntChange.ByteFourToInt(data_value);
					if (index + 4 + length > buf.length) {
						logger.info("数据长度不正确");
					} else {
						byte[] data_valueInfo = new byte[length];
						for (int i = 0; i < length; i++) {
							data_valueInfo[i] = buf[i + 4 + index];
						}
						String info = AESEncryptUtil.decrypt(data_valueInfo, SocketClient.aesPassword);
						if (info != null) {
							ClientInfo clientInfo = SocketClient.getInstance().getSocketInfo(channel);
							if (clientInfo != null) {
								// 解析JSON数据
								try {
									JSONObject jsonIn = new JSONObject(info);
									// JSONObject jsonIn = new JSONObject(new String(buf));
									int type = jsonIn.getInt("type");
									switch (type) {
									case 1: {
										JSONObject jsonUserInfo = jsonIn.getJSONObject("userinfo");
										clientInfo.setIdentyID(jsonUserInfo.getString("IdentyID"));
										clientInfo.setName(jsonUserInfo.getString("name"));
										clientInfo.setMobilePhone(jsonUserInfo.getString("mobilePhone"));
										clientInfo.setIdentyID(jsonUserInfo.getString("IdentyID"));
										clientInfo.setWeichatID(jsonIn.getString("weichatID"));
										JSONObject jsonLocation = jsonIn.getJSONObject("location");
										clientInfo.setArea(jsonLocation.getString("area"));
										clientInfo.setNote(jsonLocation.getString("note"));
										clientInfo.setType(type);
										clientInfo.setMsgTime(System.currentTimeMillis());
										// 已经实名认证 找空闲在线坐席
										SocketClient.getInstance().putSocketClinetInfo(channel, 1, clientInfo);

										break;
									}
									case 2: {
										String ackCode = jsonIn.getString("ackCode");
										if (ackCode.equals("200")) {
											// 接听
											SocketClient.getInstance().setOnlineSeatsStatus(clientInfo.getUserid(), 4);
											SocketClient.getInstance().SendWeChatAlarmStatus(clientInfo.getSession(),
													"200", clientInfo.getAcceptTime());
										} else if (ackCode.equals("201")) {
											// 拒接
											SocketClient.getInstance().SendWeChatAlarmStatus(clientInfo.getSession(),
													"201", clientInfo.getAcceptTime());
										} else if (ackCode.equals("202")) {
											// 超时
											SocketClient.getInstance().SendWeChatAlarmStatus(clientInfo.getSession(),
													"202", clientInfo.getAcceptTime());
										}
									}
									case 3: {

										String userid = jsonIn.getString("userID");
										logger.info("收到连接请求，用户ID为" + userid);
										clientInfo.setUserid(userid);
										clientInfo.setType(type);
										clientInfo.setMsgTime(System.currentTimeMillis());
										// 根据userid 获取在线坐席信息
										// 存在 继续操作
										if (onlineSeatsService.CheckOnlineSeatsUserId(userid) == true) {
											SocketClient.getInstance().putSocketClinetInfo(channel, 3, clientInfo);
											String infoResponse = "{\"type\":4}";
											byte[] v_datapath = AESEncryptUtil.encryptB(infoResponse,
													SocketClient.aesPassword);
											byte[] buffResponse1 = new byte[4 + v_datapath.length];
											byte[] bLength = ByteIntChange.IntToByteFour(v_datapath.length);
											for (int i = 0; i < bLength.length; i++) {
												buffResponse1[i] = bLength[i];
											}

											for (int i = 0; i < v_datapath.length; i++) {
												buffResponse1[4 + i] = v_datapath[i];
											}
											ByteBuffer writeBuf = ByteBuffer.allocate(buffResponse1.length);
											writeBuf.put(buffResponse1);
											writeBuf.flip();
											channel.write(writeBuf);
										}
										// 不存在 则断开连接
										else {
											size = -1;
										}
										break;

									}
									case 5: {
										logger.info("收到心跳信息，用户ID为" + clientInfo.getUserid());
										clientInfo.setMsgTime(System.currentTimeMillis());
										String infoResponse = "{\"type\":5}";
										byte[] v_datapath = AESEncryptUtil.encryptB(infoResponse,
												SocketClient.aesPassword);
										byte[] buffResponse1 = new byte[4 + v_datapath.length];
										byte[] bLength = ByteIntChange.IntToByteFour(v_datapath.length);
										for (int i = 0; i < bLength.length; i++) {
											buffResponse1[i] = bLength[i];
										}

										for (int i = 0; i < v_datapath.length; i++) {
											buffResponse1[4 + i] = v_datapath[i];
										}
										ByteBuffer writeBuf = ByteBuffer.allocate(buffResponse1.length);
										writeBuf.put(buffResponse1);
										writeBuf.flip();
										channel.write(writeBuf);
										break;
									}
									default:
										break;
									}

								} catch (Exception e) {

								}
							}
						} else {
							logger.info("数据解密失败");
						}

					}
				}
				if (size == -1) {
					logger.info("客户端断开。。。");

					SocketClient.getInstance().removeClientSocket(channel);
					channel.close();

				} else {
					// 没有可用字节,继续监听OP_READ
					key.interestOps(key.interestOps() | SelectionKey.OP_READ);
					key.selector().wakeup();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				try {
					channel.close();
					SocketClient.getInstance().removeClientSocket(channel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

	/**
	 * 检测在线客服心跳连接是否超时
	 * 
	 * @author Administrator
	 *
	 */
	public class OnlineSeatsClientCheck implements Runnable {

		OnlineSeatsClientCheck() {
		};

		public void run() {
			while (true) {
				SocketClient.getInstance().checkOverTime();
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 分配在线坐席
	 * 
	 * @author Administrator
	 *
	 */
	public class DistributionOnlineSeats implements Runnable {

		DistributionOnlineSeats() {
		};

		public void run() {
			while (true) {
				SocketClient.getInstance().DistributionOnlineSeats();
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * unicode 转换成 中文
	 * 
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
}
