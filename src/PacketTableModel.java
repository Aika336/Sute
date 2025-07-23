import javax.swing.*;

import java.util.ArrayList;

public class PacketTableModel {
    private JTable panel;
    private ArrayList<PacketTcp> tcp_packets;
    private ArrayList<PacketUdp> udp_packets;

    PacketTableModel(ArrayList<PacketTcp> tcp_packets, ArrayList<PacketUdp> udp_packets) {
        this.tcp_packets = tcp_packets;
        this.udp_packets = udp_packets;

        createPanel();
    }

    public JTable getPanel() {
        return panel;
    }

    private void createPanel() {
    }
}