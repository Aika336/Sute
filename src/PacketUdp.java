import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.namednumber.IpNumber;
import org.pcap4j.packet.namednumber.UdpPort;

import java.net.Inet4Address;
import java.time.LocalTime;

public class PacketUdp {
    public UdpPacket.UdpHeader udpHeader;
    public IpV4Packet.IpV4Header ipHeader;

    private String time;
    private Inet4Address sourceIp;
    private Inet4Address destinationIp;
    private IpNumber protocol;
    private int sizeOfPacket;
    private UdpPort sourcePort;
    private UdpPort destinationPort;

    PacketUdp(UdpPacket.UdpHeader udpHeader,  IpV4Packet.IpV4Header ipHeader) {
        this.udpHeader = udpHeader;
        this.ipHeader = ipHeader;

        time = LocalTime.now().toString();
        sourceIp = ipHeader.getSrcAddr();
        destinationIp = ipHeader.getDstAddr();
        protocol = ipHeader.getProtocol();
        sizeOfPacket = ipHeader.getTotalLengthAsInt();
        destinationPort = udpHeader.getDstPort();
        sourcePort = udpHeader.getSrcPort();
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

    public UdpPort getSourcePort() {
        return sourcePort;
    }

    public UdpPort getDestinationPort() {
        return destinationPort;
    }
}
