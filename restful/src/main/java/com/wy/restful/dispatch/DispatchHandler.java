package com.wy.restful.dispatch;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.wy.restful.entity.Headers;
import com.wy.restful.util.ApplicationContextUtil;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class DispatchHandler extends ChannelInboundHandlerAdapter {

	private static Work work = ApplicationContextUtil.getApplicationContext().getBean(Work.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof FullHttpRequest) {
			if (msg instanceof FullHttpRequest) {
				FullHttpRequest request = (FullHttpRequest) msg;
				HttpMethod method = request.method();

				HttpHeaders httpHeaders = request.headers();
				Headers headers = Headers.getRequestHeaders();
				for (Entry<String, String> entry : httpHeaders.entries()) {
					headers.put(entry.getKey(), entry.getValue());
				}
				String content = request.content().toString(Charset.forName("UTF-8"));
				Object responseObj = work.invoke(method, request.uri(), content);
				String responseBody = JSON.toJSONString(responseObj);
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBody.getBytes()));
				response.headers().set(Headers.CONTENT_TYPE, "application/json");
				response.headers().set(Headers.CONTENT_LENGTH, response.content().readableBytes());
				headers = Headers.getResponseHeaders();
				if(headers!=null) {
					for (Map.Entry<String, String> entry : headers) {
						response.headers().set(entry.getKey(), entry.getValue());
					}
				}
				Headers.removeThreadLocal();

				ctx.writeAndFlush(response);
			}
		}
	}
}
