package com.ambroz.formula.gui.swing.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import com.ambroz.formula.gamemodel.track.Track;
import com.ambroz.formula.gamemodel.labels.TrackMenuLabels;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.windows.SaveTrackWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class BuilderMenuBar extends JMenuBar implements PropertyChangeListener {

    public static final Font MENU_FONT = new Font("Segoe UI", 0, 16);

    private JButton createLeft, createRight, editPoints, switchStart, deletePoint, newTrack, saveTrack;

    private final TrackBuilder builder;
    private TrackMenuLabels builderMenuLabels;

    public BuilderMenuBar(TrackBuilder gModel) {
        this.builder = gModel;
        this.builder.addPropertyChangeListener(this);
        builderMenuLabels = new TrackMenuLabels(this.builder.getLanguage());
        initComponents();
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        createLeft = new JButton(builderMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));
        createRight = new JButton(builderMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));
        editPoints = new JButton(builderMenuLabels.getValue(TrackMenuLabels.EDIT));
        switchStart = new JButton(builderMenuLabels.getValue(TrackMenuLabels.REVERSE));
        deletePoint = new JButton(builderMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));
        newTrack = new JButton(builderMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));
        saveTrack = new JButton(builderMenuLabels.getValue(TrackMenuLabels.SAVE));

        addActions();

        add(createLeft);
        add(createRight);
        add(editPoints);
        add(new JSeparator(JSeparator.VERTICAL));
        add(switchStart);
        add(deletePoint);
        add(newTrack);
        add(saveTrack);
    }

    private void addActions() {
        createLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Track.LEFT);
                builder.repaintScene();
            }
        });

        createRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Track.RIGHT);
                builder.repaintScene();
            }
        });

        editPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.setStage(TrackBuilder.EDIT_PRESS);
                builder.getPoints().clear();
                builder.repaintScene();
            }
        });

        switchStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.setStage(TrackBuilder.BUILD_LEFT);
                builder.switchStart();
                builder.generateEndPoints(Track.LEFT);
                builder.repaintScene();
            }
        });

        deletePoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.deletePoint();
                builder.repaintScene();
            }
        });

        newTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.reset();
                builder.repaintScene();
            }
        });

        saveTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                SaveTrackWindow saveTrackWindow = new SaveTrackWindow(builder);
                saveTrackWindow.setLocation(dim.width / 2 - saveTrackWindow.getWidth() / 2, dim.height / 2 - saveTrackWindow.getHeight() / 2);
                saveTrackWindow.setVisible(true);
            }
        });

    }

    private void changeLanguage() {
        createLeft.setText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));
        createRight.setText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));
        editPoints.setText(builderMenuLabels.getValue(TrackMenuLabels.EDIT));
        deletePoint.setText(builderMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));
        switchStart.setText(builderMenuLabels.getValue(TrackMenuLabels.REVERSE));
        newTrack.setText(builderMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));
        saveTrack.setText(builderMenuLabels.getValue(TrackMenuLabels.SAVE));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("language")) {
            builderMenuLabels = new TrackMenuLabels(builder.getLanguage());
            changeLanguage();
        }
    }

}
