package l2p.commons.net.nio.impl;

import java.nio.ByteBuffer;

public abstract class ReceivablePacket<T extends MMOClient> extends l2p.commons.net.nio.ReceivablePacket<T> {
    protected T _client;
    protected ByteBuffer _buf;

    protected void setByteBuffer(ByteBuffer buf) {
        _buf = buf;
    }

    @Override
    protected ByteBuffer getByteBuffer() {
        return _buf;
    }

    protected void setClient(T client) {
        _client = client;
    }

    @Override
    public T getClient() {
        return _client;
    }

    protected abstract boolean read();
}