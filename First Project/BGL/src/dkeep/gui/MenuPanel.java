package dkeep.gui;

import javax.swing.*;
import java.awt.*;

import static dkeep.gui.Controller.Event.editMap;
import static dkeep.gui.Controller.Event.newGame;

public class MenuPanel extends JPanel {

    private Model model;
    private Controller controller;
    private JDialog settings;

    MenuPanel(Model model, Controller manager) {

        setVisible(true);
        this.model = model;
        this.controller = manager;

        this.settings = new DialogSettings(model);
        setLayout(null);

        initializeButtons();
    }

    private void initializeButtons() {
        buttonNewGame();
        buttonLoadGame();
        buttonSettings();
        buttonEditMap();
        buttonExit();
    }

    private void buttonNewGame() {
        JButton btnNewGame = new JButton("New Game");
        btnNewGame.setFont(model.getFont());
        btnNewGame.setBounds(500, 330, 200, 50);
        btnNewGame.addActionListener(arg0 -> controller.updateState(newGame));
        add(btnNewGame);
    }

    private void buttonLoadGame() {
        JButton btnLoad = new JButton("Load Game");
        btnLoad.setFont(model.getFont());
        btnLoad.addActionListener(e -> {
            model.loadPreviousGame();
            controller.updateState(newGame);
        });
        btnLoad.setBounds(500, 400, 200, 50);
        add(btnLoad);
    }

    private void buttonSettings() {
        JButton btnSettings = new JButton("Settings");
        btnSettings.setFont(model.getFont());
        btnSettings.addActionListener(e -> settings.setVisible(true));
        btnSettings.setBounds(500, 470, 200, 50);
        add(btnSettings);
    }

    private void buttonEditMap() {
        JButton btnCreateMaze = new JButton("Edit Map");
        btnCreateMaze.setFont(model.getFont());
        btnCreateMaze.addActionListener(arg0 -> controller.updateState(editMap));
        btnCreateMaze.setBounds(500, 540, 200, 50);
        add(btnCreateMaze);
    }

    private void buttonExit() {
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(model.getFont());
        btnExit.addActionListener(arg0 -> System.exit(0));
        btnExit.setBounds(500, 610, 200, 50);
        add(btnExit);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(model.getMainMenu(), 0, 0, this);
    }
}
