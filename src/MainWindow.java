import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel buttonPanel;
    private JButton startButton, stopButton, selectDeviceButton;

    public MainWindow(int width, int height, String title) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initButtons();
        initLayout();
    }

    private void initButtons() {
        startButton = new JButton("Start Capture");
        stopButton = new JButton("Stop Capture");
        selectDeviceButton = new JButton("Select Device");
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
}
