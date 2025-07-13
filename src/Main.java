import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.factory.PacketFactory;

import javax.swing.*;
import java.awt.*;

class SimpleWindow {
    public static void main(String[] args) throws PcapNativeException, NotOpenException, InterruptedException {
        // Создаём окно
        MainWindow window = new MainWindow(1280, 720, "Sute");
        window.setVisible(true);

        Sniffer cho = new Sniffer();
        cho.device.setNetworkDevice(3);

        cho.processPackets();
    }
}
