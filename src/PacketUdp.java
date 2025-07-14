import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.UdpPacket;

public class PacketUdp {
    public UdpPacket.UdpHeader udpHeader;
    public IpV4Packet.IpV4Header ipHeader;

    PacketUdp(UdpPacket.UdpHeader udpHeader,  IpV4Packet.IpV4Header ipHeader) {
        this.udpHeader = udpHeader;
        this.ipHeader = ipHeader;
    }
}
