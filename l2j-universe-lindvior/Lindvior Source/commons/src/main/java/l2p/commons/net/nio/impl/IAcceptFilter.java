package l2p.commons.net.nio.impl;

import java.nio.channels.SocketChannel;

public interface IAcceptFilter {
    public boolean accept(SocketChannel sc);
}
