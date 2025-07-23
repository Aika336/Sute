    import org.pcap4j.core.NotOpenException;
    import org.pcap4j.core.PcapNativeException;
    import org.pcap4j.core.PcapNetworkInterface;

    import javax.swing.*;
    import javax.swing.border.*;
    import javax.swing.event.ListSelectionEvent;
    import javax.swing.event.ListSelectionListener;
    import java.awt.*;

    import java.awt.event.ActionListener;
    import java.awt.event.ActionEvent;


    public class MainWindow extends JFrame {
        private JPanel buttonPanel;
        private JButton startButton, stopButton;
        private Sniffer sniffer;
        private JScrollPane devicesScroll;

        public MainWindow(int width, int height, String title) throws PcapNativeException, NotOpenException, InterruptedException {
            getContentPane().setBackground(Color.white);

            sniffer = new Sniffer();
            setTitle(title);
            setSize(width, height);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setLayout(new BorderLayout());

            initButtons();
            initDevicePanel();
            initLayout();
        }

        private void initButtons() {
            startButton = new JButton("Start Capture");
            stopButton = new JButton("Stop Capture");

            startButton.addActionListener(new ButtonProcess());
            stopButton.addActionListener(new ButtonProcess());
        }

        private void initLayout() throws PcapNativeException {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
            buttonPanel.setBackground(Color.white);

            JPanel buttonsBox = new JPanel();
            buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.Y_AXIS));
            buttonsBox.setBackground(Color.white);

            buttonsBox.add(startButton);
            buttonsBox.add(Box.createVerticalStrut(10));
            buttonsBox.add(stopButton);
            buttonsBox.add(Box.createVerticalStrut(10));
            buttonsBox.add(new JLabel("Selecting an interface:"));
            buttonsBox.add(Box.createVerticalStrut(10));
            buttonsBox.add(devicesScroll);

            buttonPanel.add(buttonsBox);

            Border line = BorderFactory.createLineBorder(Color.YELLOW, 2);
            Border margin = new EmptyBorder(10, 10, 10, 10);
            Border compound = new CompoundBorder(line, margin);
            buttonsBox.setBorder(compound);

            add(buttonPanel, BorderLayout.WEST);
        }

        private void initDevicePanel() throws PcapNativeException {
            var interfaces = sniffer.getDevice().getInterfaces();
            String[] interfaceNames = new String[interfaces.size()];

            for(int i = 0; i < interfaces.size(); i++)
                interfaceNames[i] = interfaces.get(i).getDescription();

            JList devices = new JList(interfaceNames);
            devices.setLayoutOrientation(JList.VERTICAL);

            devices.addListSelectionListener(e -> {
                try {
                    sniffer.getDevice().setNetworkDevice(devices.getSelectedIndex());
                } catch (PcapNativeException ex) {
                    ex.printStackTrace();
                }
            });

            devicesScroll = new JScrollPane(devices);
            devicesScroll.setPreferredSize(new Dimension(300, 150));
        }

        private class ButtonProcess implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if(event.getSource().equals(startButton)) {
                    sniffer.startProcess();
                }else if(event.getSource().equals(stopButton)) {
                    sniffer.stopProcess();
                }
            }
        }
    }
