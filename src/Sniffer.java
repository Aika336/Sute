import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.IpV4Packet;

import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.*;

import javax.swing.*;
import java.util.ArrayList;

public class Sniffer {
    public NetworkDevice device;
    public ArrayList<PacketTcp> tcp_packets;
    public ArrayList<PacketUdp> udp_packets;
    private boolean activeProcess;

    Sniffer() throws PcapNativeException {
        device = new NetworkDevice();
        tcp_packets = new ArrayList<>();
        udp_packets = new ArrayList<>();

        activeProcess = false;
    }

    private PacketListener listener = packet -> {
        if(packet.contains(IpV4Packet.class)) {
            IpV4Packet ip = packet.get(IpV4Packet.class);
            IpV4Packet.IpV4Header ipHeader = ip.getHeader();

            if (ipHeader.getProtocol() == IpNumber.TCP) {
                processTcpPacket(ipHeader, packet.get(TcpPacket.class));
            } else if (ipHeader.getProtocol() == IpNumber.UDP) {
                processUdpPacket(ipHeader, packet.get(UdpPacket.class));
            }
        }
    };

    private void processUdpPacket(IpV4Packet.IpV4Header ipHeader, UdpPacket udp) {
        UdpPacket.UdpHeader udpHeader = udp.getHeader();
        System.out.println(udpHeader);

        udp_packets.add(new PacketUdp(udpHeader, ipHeader));
    }

    private void processTcpPacket(IpV4Packet.IpV4Header ipHeader, TcpPacket tcp) {
        TcpPacket.TcpHeader tcpHeader = tcp.getHeader();
        System.out.println(ipHeader);

        tcp_packets.add(new PacketTcp(tcpHeader, ipHeader));
    }

    public void startProcess() {
        new Thread(() -> {
           try {
               device.handle.loop(-1, listener);
           }catch (NotOpenException | PcapNativeException | InterruptedException e) {
               e.printStackTrace();
           }
        }).start();
    }
    public void stopProcess() {
        try {
            device.handle.breakLoop();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }
    }
}
