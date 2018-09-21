package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.utils.Fonts;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class HintPanel extends JPanel implements PropertyChangeListener {

    private final RaceModel raceModel;
    private final TrackBuilder builder;
    private JLabel hintLabel;

    public HintPanel(RaceModel gameModel, TrackBuilder trackBuilder) {
        raceModel = gameModel;
        this.raceModel.addPropertyChangeListener(this);
        builder = trackBuilder;
        this.builder.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(700, 50));

        hintLabel = new JLabel("Let's have fun!!!", JLabel.CENTER);
        hintLabel.setFont(Fonts.MENU_FONT);
        hintLabel.setForeground(new Color(255, 0, 0));

        add(hintLabel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("hint")) {
            hintLabel.setText((String) evt.getNewValue());
            hintLabel.setFont(Fonts.MENU_FONT);
        } else if (evt.getPropertyName().equals("winner")) {
            hintLabel.setText((String) evt.getNewValue());
            hintLabel.setFont(new Font("Arial", 0, 24));
        } else if (evt.getPropertyName().equals("crash")) {
            hintLabel.setText((String) evt.getNewValue());
            hintLabel.setFont(new Font("Arial", 0, 20));
        }
    }

}
