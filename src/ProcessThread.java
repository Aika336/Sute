import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapNativeException;

public class ProcessThread {
    private NetworkDevice device;
    private PacketListener function;
    private Thread captureProcess;
    private boolean activeProcess;

    public ProcessThread(PacketListener function,  NetworkDevice device) {
        this.function = function;
        this.device = device;

        captureProcess = null;
        activeProcess = false;
    }

    public void start() {
        if(activeProcess)
            return;

        activeProcess = true;
        captureProcess = new Thread(() -> {
            try {
                device.getHandle().loop(-1, function);
            }catch (InterruptedException e) {
                System.out.println("Capture interrupted (breakLoop).");
            } catch (PcapNativeException | NotOpenException e) {
                e.printStackTrace();
            } finally {
                activeProcess = false;
                System.out.println("Capture thread finished.");
            }
        });
        captureProcess.start();
    }

    public void stop() {
        if(!activeProcess)
            return;

        try {
            device.getHandle().breakLoop();
        } catch (NotOpenException e) {
            e.printStackTrace();
        }

        try {
            if (captureProcess != null) {
                captureProcess.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activeProcess = false;
    }
}
