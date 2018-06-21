package dkeep.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Window {

    private JFrame frame;

    private Window() throws IOException, FontFormatException {

        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Window window = new Window();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() throws IOException, FontFormatException {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scrW = (int) screenSize.getWidth();
        int scrH = (int) screenSize.getHeight();

        frame = new JFrame();
        frame.setBounds((scrW - 1018) / 2, (scrH - 747) / 2, 1018, 747);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        Model model = new Model(frame);
        Controller controller = new Controller();

        MenuPanel menuPanel = new MenuPanel(model, controller);
        menuPanel.setBounds(0, 0, 1000, 700);
        frame.getContentPane().add(menuPanel);

        GamePanel gamePanel = new GamePanel(model, controller);
        gamePanel.setBounds(0, 0, 1070, 770);
        frame.getContentPane().add(gamePanel);

        EditPanel editPanel = new EditPanel(model, controller);
        editPanel.setBounds(0, 0, 1070, 770);
        frame.getContentPane().add(editPanel);

        controller.setPanels(menuPanel, gamePanel, editPanel);

    }


}

