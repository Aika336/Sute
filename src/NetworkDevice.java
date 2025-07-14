import org.pcap4j.core.*;
import java.util.ArrayList;

public class NetworkDevice {
    private ArrayList<PcapNetworkInterface> interfaces;
    private PcapNetworkInterface currentDevice;
    private PcapHandle handle;

    NetworkDevice() throws PcapNativeException {
        interfaces = (ArrayList<PcapNetworkInterface>)Pcaps.findAllDevs();

        if (interfaces.isEmpty()) {
            System.out.println("No interfaces found!");
            return;
        }
    }

    public PcapNetworkInterface getCurrentDevice() {
        return currentDevice;
    }

    public PcapHandle getHandle() {
        return handle;
    }

    public ArrayList<PcapNetworkInterface> getInterfaces() throws PcapNativeException {
        return interfaces;
    }

    public boolean setNetworkDevice(int index) throws PcapNativeException {
        if(index >= 0 && index < interfaces.size()) {
            currentDevice = interfaces.get(index);
            handle = currentDevice.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 50);
            return true;
        }else
            return false;
    }

    public void setFilters(String filters) throws NotOpenException, PcapNativeException {
        handle.setFilter(filters, BpfProgram.BpfCompileMode.OPTIMIZE);
    }
}
