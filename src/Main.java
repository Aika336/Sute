import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

class SimpleWindow {
    public static void main(String[] args) throws PcapNativeException, NotOpenException, InterruptedException {
        MainWindow window = new MainWindow(1280, 720, "Sute");
        window.setVisible(true);
    }
}