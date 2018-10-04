package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CoordinatesPanel extends JPanel implements PropertyChangeListener {

    private final RaceModel raceModel;
    private final TrackBuilder builder;
    private final JLabel coordinates;

    public CoordinatesPanel(RaceModel gameModel, TrackBuilder trackBuilder) {
        this.raceModel = gameModel;
        this.raceModel.addPropertyChangeListener(this);
        this.builder = trackBuilder;
        this.builder.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(180, 45));

        this.coordinates = new JLabel("", JLabel.CENTER);
        add(coordinates);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("mouseMoving")) {
            this.coordinates.setText(evt.getNewValue().toString());
        }
    }
}
