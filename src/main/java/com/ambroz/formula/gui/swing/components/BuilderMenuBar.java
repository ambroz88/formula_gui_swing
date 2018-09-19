package com.ambroz.formula.gui.swing.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import com.ambroz.formula.gamemodel.labels.TrackMenuLabels;
import com.ambroz.formula.gamemodel.track.Track;
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
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        Insets emptyInsets = new Insets(0, 0, 0, 0);

        createLeft = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Left 36x36.png")));
        createLeft.setMargin(emptyInsets);
        createRight = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Right 36x36.png")));
        createRight.setMargin(emptyInsets);
        editPoints = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Edit 36x36.png")));
        editPoints.setMargin(emptyInsets);

        switchStart = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Switch 36x36.png")));
        switchStart.setMargin(emptyInsets);
        deletePoint = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Back 36x36.png")));
        deletePoint.setMargin(emptyInsets);
        newTrack = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Delete All 36x36.png")));
        newTrack.setMargin(emptyInsets);
        saveTrack = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Save 36x36.png")));
        saveTrack.setMargin(emptyInsets);

        addActions();

        add(createLeft);
        add(createRight);
        add(editPoints);
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(0, 0, 20, 40);
        add(separator);
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
        createLeft.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));

        createRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Track.RIGHT);
                builder.repaintScene();
            }
        });
        createRight.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));

        editPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.setStage(TrackBuilder.EDIT_PRESS);
                builder.getPoints().clear();
                builder.repaintScene();
            }
        });
        editPoints.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.EDIT));

        switchStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.setStage(TrackBuilder.BUILD_LEFT);
                builder.getTrack().switchStart();
                builder.generateEndPoints(Track.LEFT);
                builder.repaintScene();
            }
        });
        switchStart.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.REVERSE));

        deletePoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.deletePoint();
                builder.repaintScene();
            }
        });
        deletePoint.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));

        newTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.reset();
                builder.repaintScene();
            }
        });
        newTrack.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));

        saveTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                SaveTrackWindow saveTrackWindow = new SaveTrackWindow(builder);
                saveTrackWindow.setLocation(dim.width / 2 - saveTrackWindow.getWidth() / 2, dim.height / 2 - saveTrackWindow.getHeight() / 2);
                saveTrackWindow.setVisible(true);
            }
        });
        saveTrack.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.SAVE));

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
