package dkeep.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static dkeep.gui.Controller.Event.leaveCustom;

public class EditPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Model model;
    private Controller controller;

    private JLabel lblCreateYourMaze;
    private JLabel lblMazeDimension;
    private JSlider slider;
    private JButton btnSave;
    private JButton btnCancel;

    private char[][] map;
    private int dimension = 10;

    @SuppressWarnings("unused")
    private BufferedImage chosenPicture;

    private int xWall, yWall;
    private int xDoor, yDoor;
    private int xHero, yHero;
    private int xOgre, yOgre;
    private int xKey, yKey;
    private int xGround, yGround;

    private char symbolchosenPicture;
    private int xPressed, yPressed;

    EditPanel(Model model, Controller manager) {
        this.model = model;
        controller = manager;

        setVisible(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        setLayout(null);

        initializeLabels();
        initializeSlider();
        initializeButtons();
        model.setFrame(dimension);
        createMap();
    }

    private void initializeLabels() {
        lblCreateYourMaze = new JLabel("Create Your Map");
        lblCreateYourMaze.setBounds(0, 0, 0, 0);
        lblCreateYourMaze.setFont(model.getFont());
        add(lblCreateYourMaze);

        lblMazeDimension = new JLabel("Map Dimension");
        lblMazeDimension.setBounds(0, 0, 0, 0);
        lblMazeDimension.setFont(model.getFont());
        add(lblMazeDimension);
    }

    private void initializeSlider() {
        slider = new JSlider();
        slider.setBounds(0, 0, 0, 0);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setMinimum(7);
        slider.setMaximum(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(10);
        slider.addChangeListener(new SliderListener());
        add(slider);
    }

    private void initializeButtons() {
        buttonSave();
        buttonCancel();
    }

    private void buttonSave() {
        btnSave = new JButton("Save");
        btnSave.setBounds(0, 0, 0, 0);
        btnSave.addActionListener(e -> {
            if (canSaveMap()) {
                model.setCustomMap(map);
                controller.updateState(leaveCustom);
                model.setFrame(model.getMenuDimension());
            } else
                JOptionPane.showMessageDialog(getRootPane(),
                        "This map is not valid. The map has to have at least one key and one door on the border, one and only one hero and no more than five ogres.");
        });
        add(btnSave);
    }

    private void buttonCancel() {
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(0, 0, 0, 0);
        btnCancel.addActionListener(e -> {
            controller.updateState(leaveCustom);
            model.setFrame(model.getMenuDimension());
        });
        add(btnCancel);
    }

    private void setCoordinates() {
        xWall = model.getWidth() - 140;
        yWall = 180;
        xDoor = model.getWidth() - 240;
        yDoor = 180;
        xHero = model.getWidth() - 140;
        yHero = 270;
        xOgre = model.getWidth() - 240;
        yOgre = 270;
        xKey = model.getWidth() - 140;
        yKey = 360;
        xGround = model.getWidth() - 240;
        yGround = 360;
    }

    private void createMap() {
        this.map = new char[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if (i == 0 || i == dimension - 1 || j == 0 || j == dimension - 1) {
                    map[i][j] = 'X';
                }
            }
        }
    }

    private void updateFrame() {
        model.setFrame(dimension);
        setCoordinates();
        lblCreateYourMaze.setBounds(model.getWidth() - 270, 13, 322, 32);
        lblMazeDimension.setBounds(model.getWidth() - 220, 70, 200, 16);
        slider.setBounds(model.getWidth() - 260, 100, 200, 53);
        btnSave.setBounds(model.getWidth() - 270, model.getHeight() - 90, 97, 25);
        btnCancel.setBounds(model.getWidth() - 150, model.getHeight() - 90, 97, 25);
    }

    @Override
    public void paintComponent(Graphics g) {
        updateFrame();
        super.paintComponent(g);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int x = j * 70;
                int y = i * 70;

                if (map[i][j] == 'X')
                    g.drawImage(model.getWall(), x, y, this);
                else if (map[i][j] == 'I')
                    g.drawImage(model.getDoor(), x, y, this);
                else if (map[i][j] == 'k') {
                    g.drawImage(model.getKey(), x, y, this);
                } else if (map[i][j] == 'A') {
                    g.drawImage(model.getGround(), x, y, this);
                    g.drawImage(model.getHeroArmed(), x, y, this);
                } else if (map[i][j] == 'O') {
                    g.drawImage(model.getGround(), x, y, this);
                    g.drawImage(model.getOgre(), x, y, this);
                } else
                    g.drawImage(model.getGround(), x, y, this);
            }
        }

        g.drawImage(model.getWall(), xWall, yWall, this);
        g.drawImage(model.getDoor2(), xDoor, yDoor, this);
        g.drawImage(model.getHeroArmed(), xHero, yHero, this);
        g.drawImage(model.getOgre(), xOgre, yOgre, this);
        g.drawImage(model.getKey2(), xKey, yKey, this);
        g.drawImage(model.getGround(), xGround, yGround, this);
    }

    private void choosePiece(int x, int y) {
        chooseWall(x, y);
        chooseDoor(x, y);
        chooseHero(x, y);
        chooseOgre(x, y);
        chooseKey(x, y);
        chooseGround(x, y);
    }

    private void chooseWall(int x, int y) {
        if (x >= xWall && x <= xWall + 70)
            if (y >= yWall && y <= yWall + 70) {
                this.chosenPicture = model.getWall();
                symbolchosenPicture = 'X';
            }
    }

    private void chooseDoor(int x, int y) {
        if (x >= xDoor && x <= xDoor + 70)
            if (y >= yDoor && y <= yDoor + 70) {
                this.chosenPicture = model.getDoor();
                symbolchosenPicture = 'I';
            }
    }

    private void chooseHero(int x, int y) {
        if (x >= xHero && x <= xHero + 70)
            if (y >= yHero && y <= yHero + 70) {
                this.chosenPicture = model.getHeroArmed();
                symbolchosenPicture = 'A';
            }
    }

    private void chooseOgre(int x, int y) {
        if (x >= xOgre && x <= xOgre + 70)
            if (y >= yOgre && y <= yOgre + 70) {
                this.chosenPicture = model.getOgre();
                symbolchosenPicture = 'O';
            }
    }

    private void chooseKey(int x, int y) {
        if (x >= xKey && x <= xKey + 70)
            if (y >= yKey && y <= yKey + 70) {
                this.chosenPicture = model.getKey();
                symbolchosenPicture = 'k';
            }
    }

    private void chooseGround(int x, int y) {
        if (x >= xGround && x <= xGround + 70)
            if (y >= yGround && y <= yGround + 70) {
                this.chosenPicture = model.getGround();
                symbolchosenPicture = ' ';
            }
    }

    private boolean canPaintMap(int i, int j) {

        return ((((symbolchosenPicture != 'I' && symbolchosenPicture != 'X') && !(i == 0 || j == 0 || i == dimension - 1 || j == dimension - 1)) || (symbolchosenPicture == 'I' || symbolchosenPicture == 'X')) && (map[i][j] == 'X' || symbolchosenPicture != 'I') || (symbolchosenPicture == 'I' && (i == 0 || j == 0 || i == dimension - 1 || j == dimension - 1)));

    }

    private void paintMap() {

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                int x = j * 70;
                int y = i * 70;

                if (xPressed > x && xPressed < x + 70)
                    if (yPressed > y && yPressed < y + 70) {

                        if (canPaintMap(i, j)) {
                            map[i][j] = symbolchosenPicture;
                            repaint();
                        }
                    }
            }
        }
    }

    private boolean canSaveMap() {

        int heroNum = 0;
        int ogreNum = 0;
        int doorNum = 0;
        int keyNum = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (map[i][j] == 'A')
                    heroNum++;
                if (map[i][j] == 'I' && (i == 0 || j == 0 || i == dimension - 1 || j == dimension - 1))
                    doorNum++;
                if (map[i][j] == 'k')
                    keyNum++;
                if (map[i][j] == 'O')
                    ogreNum++;
            }
        }

        return heroNum == 1 && doorNum >= 1 && keyNum >= 1 && ogreNum <= 5;

    }

    @Override
    public void mousePressed(MouseEvent e) {
        xPressed = e.getX();
        yPressed = e.getY();
        choosePiece(e.getX(), e.getY());
        paintMap();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        xPressed = e.getX();
        yPressed = e.getY();
        paintMap();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public class SliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent arg0) {
            dimension = slider.getValue();
            createMap();
            repaint();
        }
    }

}
