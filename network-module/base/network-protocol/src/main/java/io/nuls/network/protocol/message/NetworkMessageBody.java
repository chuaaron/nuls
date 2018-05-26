package io.nuls.network.protocol.message;

import io.nuls.kernel.exception.NulsException;
import io.nuls.kernel.func.TimeService;
import io.nuls.kernel.model.BaseNulsData;
import io.nuls.kernel.model.NulsDigestData;
import io.nuls.kernel.utils.NulsByteBuffer;
import io.nuls.kernel.utils.NulsOutputStreamBuffer;
import io.nuls.kernel.utils.SerializeUtils;
import io.nuls.kernel.utils.VarInt;
import io.protostuff.Tag;

import java.io.IOException;

public class NetworkMessageBody extends BaseNulsData {


    private int handshakeType;

    private int severPort;

    private long bestBlockHeight;

    private NulsDigestData bestBlockHash;

    private long networkTime;

    private String nodeIp;


    public NetworkMessageBody() {

    }

    public NetworkMessageBody(int handshakeType, int severPort, long bestBlockHeight, NulsDigestData bestBlockHash, String ip) {
        this.handshakeType = handshakeType;
        this.severPort = severPort;
        this.bestBlockHeight = bestBlockHeight;
        this.bestBlockHash = bestBlockHash;
        this.networkTime = TimeService.currentTimeMillis();
        this.nodeIp = ip;
    }

    @Override
    public int size() {
        int s = 0;
        s += VarInt.sizeOf(handshakeType);
        s += VarInt.sizeOf(severPort);
        s += VarInt.sizeOf(bestBlockHeight);
        s += bestBlockHash.size();
        s += VarInt.sizeOf(networkTime);
        s += SerializeUtils.sizeOfString(nodeIp);
        return s;
    }

    /**
     * serialize important field
     */
    @Override
    protected void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        stream.write(new VarInt(handshakeType).encode());
        stream.write(new VarInt(severPort).encode());
        stream.write(new VarInt(bestBlockHeight).encode());
        stream.write(bestBlockHash.serialize());
        stream.write(new VarInt(networkTime).encode());
        stream.writeString(nodeIp);
    }

    @Override
    protected void parse(NulsByteBuffer buffer) throws NulsException {
        handshakeType = (int) buffer.readVarInt();
        severPort = (int) buffer.readVarInt();
        bestBlockHeight = (int) buffer.readVarInt();
        bestBlockHash = buffer.readHash();
        networkTime = buffer.readVarInt();
        nodeIp = new String(buffer.readByLengthByte());
    }

    public int getHandshakeType() {
        return handshakeType;
    }

    public void setHandshakeType(int handshakeType) {
        this.handshakeType = handshakeType;
    }

    public int getSeverPort() {
        return severPort;
    }

    public void setSeverPort(int severPort) {
        this.severPort = severPort;
    }

    public long getBestBlockHeight() {
        return bestBlockHeight;
    }

    public void setBestBlockHeight(long bestBlockHeight) {
        this.bestBlockHeight = bestBlockHeight;
    }

    public NulsDigestData getBestBlockHash() {
        return bestBlockHash;
    }

    public void setBestBlockHash(NulsDigestData bestBlockHash) {
        this.bestBlockHash = bestBlockHash;
    }

    public long getNetworkTime() {
        return networkTime;
    }

    public void setNetworkTime(long networkTime) {
        this.networkTime = networkTime;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }
}
