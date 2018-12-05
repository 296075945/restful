package com.wy.restful.bootstrap;

import com.wy.restful.config.ServerProperties;
import com.wy.restful.dispatch.DispatchHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class Server {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	private Boolean isWork = false;

	private ServerProperties serverProperties;

	public Server(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	public void start() throws InterruptedException {
		synchronized (isWork) {
			if (isWork.equals(Boolean.FALSE)) {
				bossGroup = getBoosGroup();
				workerGroup = getWorkerGroup();
				ServerBootstrap b = new ServerBootstrap();
				b.group(bossGroup, workerGroup);
				b.channel(getChannelClass());
				b.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new HttpServerCodec());
						pipeline.addLast(new HttpObjectAggregator(serverProperties.getMaxMsgLength()));
						pipeline.addLast(new DispatchHandler());
					}
				});
				b.option(ChannelOption.SO_BACKLOG, 128);
				b.childOption(ChannelOption.SO_KEEPALIVE, true);
				b.bind(serverProperties.getPort()).sync();
				isWork = true;
			}
		}
	}

	public void shutdown() {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}

	private EventLoopGroup getBoosGroup() {
		return new NioEventLoopGroup(1);
	}

	private EventLoopGroup getWorkerGroup() {
		return new NioEventLoopGroup(1);
	}

	private Class<? extends ServerChannel> getChannelClass() {
		return NioServerSocketChannel.class;
	}

}
