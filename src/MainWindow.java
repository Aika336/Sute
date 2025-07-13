import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainWindow extends JFrame {
    private JPanel buttonPanel;
    private JButton startButton, stopButton, selectDeviceButton;
    private Sniffer sniffer;

    public MainWindow(int width, int height, String title) throws PcapNativeException, NotOpenException, InterruptedException {
        sniffer = new Sniffer();
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initButtons();
        initLayout();

        sniffer.device.setNetworkDevice(3);
    }

    private void initButtons() {
        startButton = new JButton("Start Capture");
        stopButton = new JButton("Stop Capture");
        selectDeviceButton = new JButton("Select Device");

        startButton.addActionListener(new ButtonProcess());
        stopButton.addActionListener(new ButtonProcess());
        selectDeviceButton.addActionListener(new ButtonProcess());
    }

    private void initLayout() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JPanel buttonsBox = new JPanel();
        buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.Y_AXIS));

        buttonsBox.add(startButton);
        buttonsBox.add(Box.createVerticalStrut(10));
        buttonsBox.add(stopButton);
        buttonsBox.add(Box.createVerticalStrut(10));
        buttonsBox.add(selectDeviceButton);

        Border line = BorderFactory.createLineBorder(Color.CYAN, 2);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        Border compound = new CompoundBorder(line, margin);
        buttonsBox.setBorder(compound);

        buttonPanel.add(buttonsBox);

        add(buttonPanel, BorderLayout.WEST);
    }

    private class ButtonProcess implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(event.getSource().equals(startButton)) {
                sniffer.startProcess();
            }else if(event.getSource().equals(stopButton)) {
                sniffer.stopProcess();
            }else if(event.getSource().equals(selectDeviceButton)) {

            }
        }
    }
}
