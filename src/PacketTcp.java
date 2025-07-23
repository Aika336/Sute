import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.TcpPort;
import org.pcap4j.packet.namednumber.UdpPort;

import java.net.Inet4Address;
import java.time.LocalTime;

public class PacketTcp {
    public TcpPacket.TcpHeader tcpHeader;
    public IpV4Packet.IpV4Header ipHeader;

    private String time;
    private Inet4Address sourceIp;
    private Inet4Address destinationIp;
    private IpNumber protocol;
    private int sizeOfPacket;
    private TcpPort sourcePort;
    private TcpPort destinationPort;

    PacketTcp(TcpPacket.TcpHeader tcpHeader, IpV4Packet.IpV4Header ipHeader) {
        this.tcpHeader = tcpHeader;
        this.ipHeader = ipHeader;

        time = LocalTime.now().toString();
        sourceIp = ipHeader.getSrcAddr();
        destinationIp = ipHeader.getDstAddr();
        protocol = ipHeader.getProtocol();
        sizeOfPacket = ipHeader.getTotalLengthAsInt();
        sourcePort = tcpHeader.getSrcPort();
        destinationPort = tcpHeader.getDstPort();
    }

    public String getTime() {
        return time;
    }

    public String getSourceIp() {
        return sourceIp.toString();
    }

    public String getDestinationIp() {
        return destinationIp.toString();
    }

    public String getProtocol() {
        return protocol.toString();
    }

    public int getSizeOfPacket() {
        return sizeOfPacket;
    }

    public TcpPort getSourcePort() {
        return sourcePort;
    }

    public TcpPort getDestinationPort() {
        return destinationPort;
    }
}
