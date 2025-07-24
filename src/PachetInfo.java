import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;

public class PachetInfo {

    private UdpPacket.UdpHeader udpHeader;
    private TcpPacket.TcpHeader tcpHeader;
    private IpV4Packet.IpV4Header ipV4Header;
    private boolean switchA;

    PachetInfo(TcpPacket.TcpHeader tcpHeader, IpV4Packet.IpV4Header ipV4Header) {
        this.ipV4Header = ipV4Header;
        this.tcpHeader = tcpHeader;
        switchA = true;
    }

    PachetInfo(UdpPacket.UdpHeader udpHeader, IpV4Packet.IpV4Header ipV4Header) {
        this.ipV4Header = ipV4Header;
        this.udpHeader = udpHeader;
        switchA = false;
    }

    public boolean isTcpPakcet() { return switchA; }

    public IpV4Packet.IpV4Header getIp4Header() { return ipV4Header; }
    public UdpPacket.UdpHeader getUdpHeader() { return udpHeader; }
    public TcpPacket.TcpHeader getTcpHeader() { return tcpHeader; }
}
