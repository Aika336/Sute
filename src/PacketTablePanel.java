import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PacketTablePanel extends JTable {
    private DefaultTableModel model;
    private MainWindow window;

    PacketTablePanel(MainWindow window) {
        model = new DefaultTableModel(0, 0);
        this.window = window;
        setRowHeight(25);

        createPanel();
    }

    private void createPanel() {
        String[] header = { "Protocol", "Src IP", "Dst IP", "Src Port", "Dst Port", "Time","Status" };
        model.setColumnIdentifiers(header);
        setModel(model);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var info = window.getSniffer().getPackets().get(getSelectedRow());
                window.getTextArea().setText(info.getIp4Header().toString());
            }
        });
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
        String getTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        model.addRow(new Object[]{protocol, srcIp, dstIp, srcPort, dstPort, getTime, "Captured"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}