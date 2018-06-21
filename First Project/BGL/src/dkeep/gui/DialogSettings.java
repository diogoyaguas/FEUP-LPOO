package dkeep.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;


class DialogSettings extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private Model model;
    private JComboBox<String> comboBox;
    private JPanel buttonPane;
    private JLabel invalidNumber;
    private JLabel maxNumber;
    private JTextField EnemyNumber;
    private JButton Save;

    DialogSettings(Model model) {

        this.model = model;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scrW = (int) screenSize.getWidth();
        int scrH = (int) screenSize.getHeight();

        setBounds((scrW - 500) / 2, (scrH - 250) / 2, 500, 250);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setModal(true);

        initializeLabels();
        initializeButtons();
        initializeText();
        initializeComboBox();
    }

    private void initializeLabels() {
        JLabel lblSettings = new JLabel("Settings");
        lblSettings.setFont(new Font("Monospaced", Font.PLAIN, 30));
        lblSettings.setBounds(161, 10, 200, 50);
        contentPanel.add(lblSettings);

        JLabel lblTypeOfGuard = new JLabel("Type of Guard");
        lblTypeOfGuard.setFont(model.getFont());
        lblTypeOfGuard.setBounds(10, 70, 200, 16);
        contentPanel.add(lblTypeOfGuard);

        JLabel lblNumberOfEnemies = new JLabel("Number of Enemies");
        lblNumberOfEnemies.setFont(model.getFont());
        lblNumberOfEnemies.setBounds(10, 120, 200, 20);
        contentPanel.add(lblNumberOfEnemies);

        initializeErrorLabels();

    }

    private void initializeErrorLabels() {
        invalidNumber = new JLabel("Invalid Number");
        invalidNumber.setFont(new Font("Monospaced", Font.PLAIN, 14));
        invalidNumber.setBounds(200, 145, 200, 20);
        invalidNumber.setVisible(false);
        contentPanel.add(invalidNumber);

        maxNumber = new JLabel("Max Number: 5");
        maxNumber.setFont(new Font("Monospaced", Font.PLAIN, 14));
        maxNumber.setBounds(200, 145, 200, 20);
        maxNumber.setVisible(false);
        contentPanel.add(maxNumber);
    }

    private void initializeButtons() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        buttonSave();
        buttonCancel();
    }

    private void buttonSave() {
        Save = new JButton("Save");
        Save.addActionListener(e -> {
            model.setGuardType(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
            model.setEnemyNum(Integer.parseInt(EnemyNumber.getText()));
            dispose();
        });
        Save.setEnabled(false);
        buttonPane.add(Save);
    }

    private void buttonCancel() {
        JButton Cancel = new JButton("Cancel");
        Cancel.addActionListener(e -> dispose());
        Cancel.setActionCommand("Cancel");
        buttonPane.add(Cancel);
    }

    private void initializeText() {
        EnemyNumber = new JTextField();
        EnemyNumber.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                checkIfValid();
                enableSave();
            }

            public void insertUpdate(DocumentEvent e) {
                checkIfValid();
                enableSave();
            }

            public void removeUpdate(DocumentEvent e) {
                checkIfValid();
                enableSave();
            }

        });
        EnemyNumber.setFont(model.getFont());
        EnemyNumber.setBounds(200, 120, 150, 25);
        contentPanel.add(EnemyNumber);
    }

    private void checkIfValid() {
        String s = EnemyNumber.getText();
        if (s.length() == 0) {
            maxNumber.setVisible(false);
            invalidNumber.setVisible(false);
            return;
        }
        int number;
        try {
            number = Integer.parseInt(s);
        } catch (Exception e1) {
            invalidNumber.setVisible(true);
            maxNumber.setVisible(false);
            return;
        }
        if (number > 5) maxNumber.setVisible(true);
        else if (number <= 0) {
            invalidNumber.setVisible(true);
            maxNumber.setVisible(false);
            return;
        } else maxNumber.setVisible(false);
        invalidNumber.setVisible(false);
    }

    private boolean checkIfempty() {
        String s = EnemyNumber.getText();
        return s.length() == 0;
    }

    private void enableSave() {
        if (maxNumber.isVisible() || invalidNumber.isVisible() || checkIfempty()) Save.setEnabled(false);
        else Save.setEnabled(true);
    }

    private void initializeComboBox() {
        comboBox = new JComboBox<>();
        comboBox.setFont(model.getFont());
        comboBox.setBounds(200, 70, 150, 25);
        comboBox.addItem("Rookie");
        comboBox.addItem("Drunken");
        comboBox.addItem("Suspicious");
        contentPanel.add(comboBox);
    }
}
