    import org.pcap4j.core.NotOpenException;
    import org.pcap4j.core.PcapNativeException;
    import org.pcap4j.core.PcapNetworkInterface;

    import javax.swing.*;
    import javax.swing.border.*;
    import java.awt.*;

    import java.awt.event.ActionListener;
    import java.awt.event.ActionEvent;


    public class MainWindow extends JFrame {
        private JButton startButton, stopButton;
        private Sniffer sniffer;
        private JList<String> devicesList;
        private JTextArea infoArea;

        public MainWindow(int width, int height, String title) throws PcapNativeException, NotOpenException, InterruptedException {
            getContentPane().setBackground(Color.white);

            sniffer = new Sniffer();
            setTitle(title);
            setSize(width, height);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setLayout(new BorderLayout());

            add(createLeftPanel(), BorderLayout.WEST);
        }

        private JPanel createLeftPanel() throws PcapNativeException {
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setPreferredSize(new Dimension(300, getHeight()));
            leftPanel.setBackground(Color.white);
            leftPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
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
            infoArea = new JTextArea(6, 25);
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
