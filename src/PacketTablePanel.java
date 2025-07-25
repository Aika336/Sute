import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PacketTablePanel extends JTable {
    private DefaultTableModel model;

    PacketTablePanel() {
        model = new DefaultTableModel(0, 0);
        setRowHeight(25);

        createPanel();
    }

    private void createPanel() {
        String[] header = { "Protocol", "Src IP", "Dst IP", "Src Port", "Dst Port", "Status" };
        model.setColumnIdentifiers(header);
        setModel(model);
    }

    public void addPacket(PachetInfo packetInfo) {
        String protocol = packetInfo.isTcpPakcet() ? "TCP" : "UDP";
        String srcIp = packetInfo.getIp4Header().getSrcAddr().getHostAddress();
        String dstIp = packetInfo.getIp4Header().getDstAddr().getHostAddress();
        int srcPort = packetInfo.isTcpPakcet() ?
                packetInfo.getTcpHeader().getSrcPort().valueAsInt() :
                packetInfo.getUdpHeader().getSrcPort().valueAsInt();
        int dstPort = packetInfo.isTcpPakcet() ?
                packetInfo.getTcpHeader().getDstPort().valueAsInt() :
                packetInfo.getUdpHeader().getDstPort().valueAsInt();

        model.addRow(new Object[]{protocol, srcIp, dstIp, srcPort, dstPort, "Captured"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}