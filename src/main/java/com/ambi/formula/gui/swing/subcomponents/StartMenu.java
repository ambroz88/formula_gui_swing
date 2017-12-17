package com.ambi.formula.gui.swing.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.labels.StartMenuLabels;
import com.ambi.formula.gui.swing.TopMenuBar;

/**
 *
 * @author Jiri Ambroz
 */
public final class StartMenu extends JMenu implements PropertyChangeListener {

    private final GameModel gModel;
    private final JMenuItem onePlayer, onePlayerAgain, twoPlayers, twoPlayersAgain, endGame;
    private StartMenuLabels startMenuLabels;

    public StartMenu(GameModel model) {
        super();
        this.gModel = model;
        this.gModel.addPropertyChangeListener(this);
        startMenuLabels = new StartMenuLabels(gModel.getLanguage());

        setText(startMenuLabels.getValue(StartMenuLabels.START));
        setEnabled(false);
        onePlayer = new JMenuItem();
        onePlayer.setFont(TopMenuBar.MENU_FONT);
        onePlayer.setText(startMenuLabels.getValue(StartMenuLabels.ONE));
        onePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                onePlayer();
            }
        });

        onePlayerAgain = new JMenuItem();
        onePlayerAgain.setFont(TopMenuBar.MENU_FONT);
        onePlayerAgain.setText(startMenuLabels.getValue(StartMenuLabels.ONE_AGAIN));
        onePlayerAgain.setVisible(false);
        onePlayerAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                gModel.prepareGame(1);
            }
        });

        twoPlayers = new JMenuItem();
        twoPlayers.setFont(TopMenuBar.MENU_FONT);
        twoPlayers.setText(startMenuLabels.getValue(StartMenuLabels.TWO));
        twoPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                twoPlayer();
            }
        });

        twoPlayersAgain = new JMenuItem();
        twoPlayersAgain.setFont(TopMenuBar.MENU_FONT);
        twoPlayersAgain.setText(startMenuLabels.getValue(StartMenuLabels.TWO_AGAIN));
        twoPlayersAgain.setVisible(false);
        twoPlayersAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                gModel.prepareGame(2);
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

        add(onePlayer);
        add(onePlayerAgain);
        add(twoPlayers);
        add(twoPlayersAgain);
        add(endGame);
    }

    private void onePlayer() {
        gModel.prepareGame(1);
        onePlayerAgain.setVisible(true);
        onePlayer.setVisible(false);
    }

    private void twoPlayer() {
        gModel.prepareGame(2);
        twoPlayersAgain.setVisible(true);
        twoPlayers.setVisible(false);
    }

    private void changeLanguage() {
        setText(startMenuLabels.getValue(StartMenuLabels.START));
        onePlayer.setText(startMenuLabels.getValue(StartMenuLabels.ONE));
        onePlayerAgain.setText(startMenuLabels.getValue(StartMenuLabels.ONE_AGAIN));
        twoPlayers.setText(startMenuLabels.getValue(StartMenuLabels.TWO));
        twoPlayersAgain.setText(startMenuLabels.getValue(StartMenuLabels.TWO_AGAIN));
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
