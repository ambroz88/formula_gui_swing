package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CoordinatesPanel extends JPanel implements PropertyChangeListener {

    private static final int PANEL_HEIGHT = 25;

    private final RaceModel raceModel;
    private final TrackBuilder builder;
    private JLabel coordinates;

    public CoordinatesPanel(RaceModel gameModel, TrackBuilder trackBuilder) {
        this.raceModel = gameModel;
        this.builder = trackBuilder;

        initComponents();
        addActions();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(TrackListComponent.LIST_WIDTH - OptionsWindow.FRAME_MARGIN, PANEL_HEIGHT));

        coordinates = new JLabel("", JLabel.CENTER);
        add(coordinates);
    }

    private void addActions() {
        raceModel.addPropertyChangeListener(this);
        builder.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("mouseMoving")) {
            coordinates.setText(evt.getNewValue().toString());
        }
    }
}
