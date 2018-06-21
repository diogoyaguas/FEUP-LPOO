package dkeep.gui;

public class Controller {

    private MenuPanel MenuPanel;
    private GamePanel GamePanel;
    private EditPanel EditPanel;

    Controller() {
    }

    public void setPanels(MenuPanel MenuPanel, GamePanel GamePanel, EditPanel EditPanel) {
        this.MenuPanel = MenuPanel;
        this.GamePanel = GamePanel;
        this.EditPanel = EditPanel;
    }

    public void updateState(Event evt) {
        if (evt == Event.newGame) {
            MenuPanel.setVisible(false);
            GamePanel.newGame();
            GamePanel.setVisible(true);
            GamePanel.setFocusable(true);
            GamePanel.requestFocusInWindow();
            EditPanel.setVisible(false);
        } else if (evt == Event.editMap) {
            MenuPanel.setVisible(false);
            GamePanel.setVisible(false);
            EditPanel.setVisible(true);
        } else if (evt == Event.leaveCustom) {
            MenuPanel.setVisible(true);
            GamePanel.setVisible(false);
            EditPanel.setVisible(false);
        } else if (evt == Event.gameOver) {
            MenuPanel.setVisible(true);
            GamePanel.setVisible(false);
            EditPanel.setVisible(false);
        }
    }

    // State Machine
    public enum Event {
        newGame, editMap, leaveCustom, gameOver
    }
}
