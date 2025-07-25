import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.*;

import java.util.ArrayList;

public class Sniffer {
    private NetworkDevice device;
    public ArrayList<PachetInfo> packets;
    private ProcessThread processThread;
    private PacketTablePanel packetTablePanel;

    Sniffer(PacketTablePanel packetTablePanel) throws PcapNativeException {
        this.packetTablePanel = packetTablePanel;
        device = new NetworkDevice();
        processThread = new ProcessThread(listener, device);

        packets = new ArrayList<>();

    }

    public NetworkDevice getDevice() {
        return device;
    }
    public ArrayList<PachetInfo> getPackets() { return packets; }
    public void setFilters(String str) throws NotOpenException, PcapNativeException { device.getHandle().setFilter(str, BpfProgram.BpfCompileMode.OPTIMIZE);}

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
        PachetInfo pachetInfo = new PachetInfo(udpHeader, ipHeader);

        packets.add(pachetInfo);
        packetTablePanel.addPacket(pachetInfo);
    }

    private void processTcpPacket(IpV4Packet.IpV4Header ipHeader, TcpPacket tcp) {
        TcpPacket.TcpHeader tcpHeader = tcp.getHeader();
        PachetInfo pachetInfo = new PachetInfo(tcpHeader, ipHeader);

        packets.add(pachetInfo);
        packetTablePanel.addPacket(pachetInfo);
    }


    public void startProcess() {
        processThread.start();
    }
    public void stopProcess() {
        processThread.stop();
    }
}
