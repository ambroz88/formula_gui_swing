package com.ambi.formula.gui.swing.subcomponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.labels.StartMenuLabels;
import com.ambi.formula.gui.swing.TopMenuBar;
import com.ambi.formula.gui.swing.windows.StartGameWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class StartMenu extends JMenu implements PropertyChangeListener {

    private final GameModel gModel;
    private final JMenuItem startNewGame, startAgain, endGame;
    private StartMenuLabels startMenuLabels;

    public StartMenu(GameModel model) {
        super();
        this.gModel = model;
        this.gModel.addPropertyChangeListener(this);
        startMenuLabels = new StartMenuLabels(gModel.getLanguage());

        setText(startMenuLabels.getValue(StartMenuLabels.START));
        setEnabled(false);
        startNewGame = new JMenuItem();
        startNewGame.setFont(TopMenuBar.MENU_FONT);
        startNewGame.setText(startMenuLabels.getValue(StartMenuLabels.START_GAME));
        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                startGame();
            }
        });

        startAgain = new JMenuItem();
        startAgain.setFont(TopMenuBar.MENU_FONT);
        startAgain.setText(startMenuLabels.getValue(StartMenuLabels.START_AGAIN));
        startAgain.setVisible(false);
        startAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                gModel.prepareGame();
            }
        });

        endGame = new JMenuItem();
        endGame.setFont(TopMenuBar.MENU_FONT);
        endGame.setText(startMenuLabels.getValue(StartMenuLabels.QUIT));
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                gModel.endGame();
            }
        });

        add(startNewGame);
        add(startAgain);
        add(endGame);
    }

    private void startGame() {
        StartGameWindow window = new StartGameWindow(gModel);
        window.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getWidth() / 2, dim.height / 2 - window.getHeight() / 2);

        startAgain.setVisible(true);
    }

    private void changeLanguage() {
        setText(startMenuLabels.getValue(StartMenuLabels.START));
        startNewGame.setText(startMenuLabels.getValue(StartMenuLabels.START_GAME));
        startAgain.setText(startMenuLabels.getValue(StartMenuLabels.START_AGAIN));
        endGame.setText(startMenuLabels.getValue(StartMenuLabels.QUIT));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "startVisible":
                setEnabled((boolean) evt.getNewValue());
                break;
            case "language":
                startMenuLabels = new StartMenuLabels(gModel.getLanguage());
                changeLanguage();
                break;
        }
    }
}
