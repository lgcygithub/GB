package org.tron.core.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tron.common.overlay.server.Channel;
import org.tron.common.overlay.server.MessageQueue;
import org.tron.core.net.message.LgcyMessage;
import org.tron.core.net.peer.PeerConnection;

@Component
@Scope("prototype")
public class LgcyNetHandler extends SimpleChannelInboundHandler<LgcyMessage> {

  protected PeerConnection peer;

  private MessageQueue msgQueue;

  @Autowired
  private LgcyNetService tronNetService;

  @Override
  public void channelRead0(final ChannelHandlerContext ctx, LgcyMessage msg) throws Exception {
    msgQueue.receivedMessage(msg);
    tronNetService.onMessage(peer, msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    peer.processException(cause);
  }

  public void setMsgQueue(MessageQueue msgQueue) {
    this.msgQueue = msgQueue;
  }

  public void setChannel(Channel channel) {
    this.peer = (PeerConnection) channel;
  }

}