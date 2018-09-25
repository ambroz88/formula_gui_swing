package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.ambroz.formula.gamemodel.labels.StartMenuLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RaceMenuBar extends JMenuBar implements PropertyChangeListener {

    private final RaceModel raceModel;
    private StartMenuLabels startLabels;
    private HintPanel hintPanel;
    private JButton playAgain;
    private JButton newGame;

    public RaceMenuBar(RaceModel model) {
        this.raceModel = model;
        this.raceModel.addPropertyChangeListener(this);
        startLabels = new StartMenuLabels(this.raceModel.getLanguage());

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        initButtons();

        addInsets();
        addToolTips();
        addActions();

        addButtonsToBar();
    }

    private void initButtons() {
        newGame = new JButton(new ImageIcon(getClass().getClassLoader().getResource("NewGame 36x36.png")));
        playAgain = new JButton(new ImageIcon(getClass().getClassLoader().getResource("PlayAgain 36x36.png")));
        hintPanel = new HintPanel(raceModel);
    }

    private void addInsets() {
        Insets emptyInsets = new Insets(0, 0, 0, 0);
        newGame.setMargin(emptyInsets);
        playAgain.setMargin(emptyInsets);
    }

    private void addToolTips() {
        newGame.setToolTipText(startLabels.getValue(StartMenuLabels.START_GAME));
        playAgain.setToolTipText(startLabels.getValue(StartMenuLabels.START_AGAIN));
    }

    private void addActions() {
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                raceModel.prepareGame(raceModel.getTrack());
            }
        });
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                raceModel.prepareGame(raceModel.getTrack());
            }
        });
    }

    private void addButtonsToBar() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttons.add(newGame);
        buttons.add(playAgain);

        add(buttons, BorderLayout.WEST);
        add(hintPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("language")) {
            startLabels = new StartMenuLabels(raceModel.getLanguage());
            addToolTips();
        }
    }

}
