package dkeep.gui;

import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static dkeep.gui.Controller.Event.gameOver;

public class GamePanel extends JPanel implements KeyListener {

    private Model model;
    private Controller controller;

    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;
    private JButton save;
    private JButton exit;
    private JLabel lblControllers;

    GamePanel(Model model, Controller manager) {
        setVisible(false);
        this.model = model;
        controller = manager;
        addKeyListener(this);
        setLayout(null);

        lblControllers = new JLabel("Controllers");
        lblControllers.setFont(model.getFont());
        add(lblControllers);

        initializeButtons();
        newGame();
        updateDisplay();
    }

    public void newGame() {
        this.model.newGame();
    }

    private void initializeButtons() {
        buttonUp();
        buttonDown();
        buttonLeft();
        buttonRight();
        buttonSave();
        buttonExit();
    }

    private void buttonUp() {
        up = new JButton("Up");
        up.addActionListener(arg0 -> {
            processKeys('w');
            requestFocusInWindow();
        });
        up.setFont(model.getFont());
        add(up);
    }

    private void buttonDown() {
        down = new JButton("Down");
        down.addActionListener(e -> {
            processKeys('s');
            requestFocusInWindow();
        });
        down.setFont(model.getFont());
        add(down);
    }

    private void buttonLeft() {
        left = new JButton("Left");
        left.addActionListener(e -> {
            processKeys('a');
            requestFocusInWindow();
        });
        left.setFont(model.getFont());
        add(left);
    }

    private void buttonRight() {
        right = new JButton("Right");
        right.addActionListener(e -> {
            processKeys('d');
            requestFocusInWindow();
        });
        right.setFont(model.getFont());
        add(right);
    }

    private void buttonSave() {
        save = new JButton("Save Game");
        save.addActionListener(e -> {
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("BGL/tmp/game.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(model.getGame());
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }

        });
        save.setFont(model.getFont());
        add(save);
    }
    
    private void buttonExit(){
    	exit = new JButton("Exit");
    	exit.addActionListener(e -> {
    		controller.updateState(gameOver);
    		model.setFrame(model.getMenuDimension());});
    	exit.setFont(model.getFont());
    	add(exit);
    }

    private void processKeys(char move) {

        model.updateMove(move);
        model.updateGame(move);
        repaint();

        if (model.getGameState() == Game.State.DEFEAT) {
            JOptionPane.showMessageDialog(getRootPane(), "Game Over!");
            controller.updateState(gameOver);
            model.deleteGame();
            model.newGame();
            model.setFrame(model.getMapDimension());
        } else if (model.getGameState() == Game.State.WIN && model.getLevel() == 2) {
            JOptionPane.showMessageDialog(getRootPane(), "You Won!");
            controller.updateState(gameOver);
            model.loadGame();
            model.setFrame(model.getMenuDimension());
        } else if (model.getLevel() == 1 && model.getGameState() == Game.State.WIN) {
            JOptionPane.showMessageDialog(getRootPane(), "Level Up!");
            model.levelUp();
            model.loadGame();
            repaint();
        }
    }

    private void updateDisplay() {
        model.setFrame(model.getMapDimension());
        up.setBounds(model.getWidth() - 200, 80, 90, 40);
        down.setBounds(model.getWidth() - 200, 200, 90, 40);
        left.setBounds(model.getWidth() - 260, 140, 90, 40);
        right.setBounds(model.getWidth() - 140, 140, 90, 40);
        lblControllers.setBounds(model.getWidth() - 200, 10, 180, 40);
        save.setBounds(model.getWidth() - 235, 300, 150, 40);
        exit.setBounds(model.getWidth() - 235, model.getHeight() - 150, 150, 40);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < model.getMapDimension(); i++) {
            for (int j = 0; j < model.getMapDimension(); j++) {

                if (Game.board.getBoard()[i][j] == 'X')
                    g.drawImage(model.getWall(), j * 70, i * 70, this);
                else
                    g.drawImage(model.getGround(), j * 70, i * 70, this);
            }
        }

        paintCharacters(g);
        paintObjects(g);
    }

    private void paintCharacters(Graphics g) {
        paintOgres(g);
        paintGuard(g);
        paintHero(g);
    }

    private void paintOgres(Graphics g) {
        if (model.getCurrentMap().getOgres() != null)
            for (int i = 0; i < model.getGame().ogres.size(); i++) {

                if (model.getGame().ogres.get(i).getStun())
                    g.drawImage(model.getOgreStun(), model.getGame().ogres.get(i).getx() * 70,
                            model.getGame().ogres.get(i).gety() * 70, this);
                else
                    g.drawImage(model.getOgre(), model.getGame().ogres.get(i).getx() * 70,
                            model.getGame().ogres.get(i).gety() * 70, this);

                g.drawImage(model.getClub(), model.getGame().ogres.get(i).getClubX() * 70,
                        model.getGame().ogres.get(i).getClubY() * 70, this);
            }
    }

    private void paintGuard(Graphics g) {
        if (model.getCurrentMap().getGuard() != null) {

            for (int i = 0; i < model.getGame().guards.size(); i++) {
                if (model.getGame().guards.get(i).getc() == 'G')
                    g.drawImage(model.getGuard(), model.getGame().guards.get(i).getx() * 70,
                            model.getGame().guards.get(i).gety() * 70, this);
                else
                    g.drawImage(model.getGuardSleep(), model.getGame().guards.get(i).getx() * 70,
                            model.getGame().guards.get(i).gety() * 70, this);
            }
        }
    }

    private void paintHero(Graphics g) {
        if (model.getGame().hero.getArmed())
            g.drawImage(model.getHeroArmed(), model.getGame().hero.getx() * 70,
                    model.getGame().hero.gety() * 70, this);
        else
            g.drawImage(model.getHero(), model.getGame().hero.getx() * 70,
                    model.getGame().hero.gety() * 70, this);
    }

    private void paintObjects(Graphics g) {
        paintDoors(g);
        paintKeyLevers(g);
    }

    private void paintDoors(Graphics g) {
        for (int i = 0; i < model.getGame().doors.size(); i++) {
            if (model.getGame().doors.get(i).getOpen())
                g.drawImage(model.getDoorOpen(), model.getGame().doors.get(i).getx() * 70,
                        model.getGame().doors.get(i).gety() * 70, this);
            else if (model.getGame().doors.get(i).getx() == 0 || model.getGame().doors.get(i).gety() == 0 || model.getGame().doors.get(i).getx() == Game.board.getBoard()[0].length || model.getGame().doors.get(i).gety() == Game.board.getBoard().length) {
                g.drawImage(model.getDoor(), model.getGame().doors.get(i).getx() * 70,
                        model.getGame().doors.get(i).gety() * 70, this);
            } else {
                g.drawImage(model.getDoor(), model.getGame().doors.get(i).getx() * 70,
                        model.getGame().doors.get(i).gety() * 70, this);
            }
        }

    }

    private void paintKeyLevers(Graphics g) {
        for (int i = 0; i < model.getGame().keylevers.size(); i++) {
            if (Game.board.getLever_key() == 0) {
                if (model.getGame().keylevers.get(i).getActive())
                    g.drawImage(model.getLeverActive(), model.getGame().keylevers.get(i).getx() * 70, model.getGame().keylevers.get(i).gety() * 70, this);
                else
                    g.drawImage(model.getLever(), model.getGame().keylevers.get(i).getx() * 70, model.getGame().keylevers.get(i).gety() * 70, this);
            } else
                g.drawImage(model.getKey(), model.getGame().keylevers.get(i).getx() * 70, model.getGame().keylevers.get(i).gety() * 70, this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                processKeys('a');
                break;
            case KeyEvent.VK_RIGHT:
                processKeys('d');
                break;
            case KeyEvent.VK_UP:
                processKeys('w');
                break;
            case KeyEvent.VK_DOWN:
                processKeys('s');
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
