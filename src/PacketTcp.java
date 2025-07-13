import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;

public class PacketTcp {
    public TcpPacket.TcpHeader tcpHeader;
    public IpV4Packet.IpV4Header ipHeader;

    PacketTcp(TcpPacket.TcpHeader tcpHeader, IpV4Packet.IpV4Header ipHeader) {
        this.tcpHeader = tcpHeader;
        this.ipHeader = ipHeader;
    }
}
