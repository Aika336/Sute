import org.pcap4j.core.PcapNativeException;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainWindow extends JFrame {
    private JButton startButton, stopButton;
    private JList<String> devicesList;
    private JTextArea infoArea;
    private JPanel leftPanel;

    private Sniffer sniffer;
    private PacketTablePanel packetTablePanel;

    public MainWindow(int width, int height, String title) throws PcapNativeException{
        packetTablePanel = new PacketTablePanel(this);
        sniffer = new Sniffer(packetTablePanel);

        getContentPane().setBackground(Color.white);

        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());

        add(createLeftPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() throws PcapNativeException {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        leftPanel.setBackground(Color.white);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Кнопки Start/Stop
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonRow.setBackground(Color.white);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        startButton.addActionListener(new ButtonProcess());
        stopButton.addActionListener(new ButtonProcess());
        buttonRow.add(startButton);
        buttonRow.add(stopButton);

        // Метка и список интерфейсов
        JLabel interfaceLabel = new JLabel("Selecting an interface:");
        interfaceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        var interfaces = sniffer.getDevice().getInterfaces();
        String[] interfaceNames = new String[interfaces.size()];
        for (int i = 0; i < interfaces.size(); i++)
            interfaceNames[i] = interfaces.get(i).getDescription();

        devicesList = new JList<>(interfaceNames);
        devicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        devicesList.setLayoutOrientation(JList.VERTICAL);
        devicesList.addListSelectionListener(e -> {
            try {
                sniffer.getDevice().setNetworkDevice(devicesList.getSelectedIndex());
            } catch (PcapNativeException ex) {
                ex.printStackTrace();
            }
        });

        JScrollPane listScroll = new JScrollPane(devicesList);
        listScroll.setPreferredSize(new Dimension(250, 120));

        // Текстовая область для информации о пакете
        JLabel infoLabel = new JLabel("Packet information:");
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoArea = new JTextArea(24, 24);
        infoArea.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(infoArea);

            // Добавление компонентов по порядку
        leftPanel.add(buttonRow);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(interfaceLabel);
        leftPanel.add(listScroll);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(infoLabel);
        leftPanel.add(infoScroll);

        return leftPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setPreferredSize(new Dimension(getWidth() - leftPanel.getWidth(), getHeight()));

        JScrollPane scrollPane = new JScrollPane(packetTablePanel);
        scrollPane.setPreferredSize(new Dimension(getWidth() / 2 - leftPanel.getWidth(), getHeight()));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        return tablePanel;
    }

    public JTextArea getTextArea() {
        return infoArea;
    }

    public Sniffer getSniffer() {
        return sniffer;
    }

    private class ButtonProcess implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(event.getSource().equals(startButton)) {
                sniffer.startProcess();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }else if(event.getSource().equals(stopButton)) {
                sniffer.stopProcess();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        }
    }
}