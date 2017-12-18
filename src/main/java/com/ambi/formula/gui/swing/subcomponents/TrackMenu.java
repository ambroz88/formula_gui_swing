package com.ambi.formula.gui.swing.subcomponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ambi.formula.gamemodel.TrackBuilder;
import com.ambi.formula.gamemodel.datamodel.Track;
import com.ambi.formula.gamemodel.labels.TrackMenuLabels;
import com.ambi.formula.gui.swing.TopMenuBar;
import com.ambi.formula.gui.swing.windows.SaveTrackWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class TrackMenu extends JMenu implements PropertyChangeListener {

    private final TrackBuilder builder;
    private final JCheckBoxMenuItem createLeft, createRight, editPoints;
    private final JMenuItem switchStart, deletePoint, newTrack, saveTrack;
    private TrackMenuLabels trackMenuLabels;

    public TrackMenu(TrackBuilder model) {
        super();
        this.builder = model;
        this.builder.addPropertyChangeListener(this);

        trackMenuLabels = new TrackMenuLabels(builder.getModel().getLanguage());

        setText(trackMenuLabels.getValue(TrackMenuLabels.TITLE));

        createLeft = new JCheckBoxMenuItem(trackMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));
        createLeft.setFont(TopMenuBar.MENU_FONT);
        createLeft.setSelected(true);
        createLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Track.LEFT);
            }
        });
        createRight = new JCheckBoxMenuItem(trackMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));
        createRight.setFont(TopMenuBar.MENU_FONT);
        createRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Track.RIGHT);
            }
        });
        editPoints = new JCheckBoxMenuItem(trackMenuLabels.getValue(TrackMenuLabels.EDIT));
        editPoints.setFont(TopMenuBar.MENU_FONT);
        editPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createRight.setSelected(false);//odskrtne tlacitko pro tvorbu prave krajnice
                createLeft.setSelected(false);//odskrtne tlacitko pro tvorbu leve krajnice
                builder.editPoints();
            }
        });

        ButtonGroup bg = new ButtonGroup();
        bg.add(createLeft);
        bg.add(createRight);
        bg.add(editPoints);

        deletePoint = new JMenuItem(trackMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));
        deletePoint.setFont(TopMenuBar.MENU_FONT);
        deletePoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                deletePoints();
            }
        });
        switchStart = new JMenuItem(trackMenuLabels.getValue(TrackMenuLabels.REVERSE));
        switchStart.setFont(TopMenuBar.MENU_FONT);
        switchStart.setEnabled(false);
        switchStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.getModel().switchStart();
                createLeft.setSelected(true);//zaskrtne tlacitko pro tvorbu leve krajnice
            }
        });
        newTrack = new JMenuItem(trackMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));
        newTrack.setFont(TopMenuBar.MENU_FONT);
        newTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newTrack();
            }
        });

        saveTrack = new JMenuItem(trackMenuLabels.getValue(TrackMenuLabels.SAVE));
        saveTrack.setFont(TopMenuBar.MENU_FONT);
        saveTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

                SaveTrackWindow saveTrackWindow = new SaveTrackWindow(builder.getModel());
                saveTrackWindow.setLocation(dim.width / 2 - saveTrackWindow.getWidth() / 2, dim.height / 2 - saveTrackWindow.getHeight() / 2);
                saveTrackWindow.setVisible(true);
            }
        });

        add(createLeft);
        add(createRight);
        add(editPoints);
        add(deletePoint);
        add(new JPopupMenu.Separator());
        add(switchStart);
        add(newTrack);
        add(saveTrack);
    }

    private void deletePoints() {
        if (createLeft.isSelected()) {
            builder.deletePoint(Track.LEFT, Track.RIGHT);
        } else if (createRight.isSelected()) {
            builder.deletePoint(Track.RIGHT, Track.LEFT);
        } else {
            builder.deletePoint(0, 0);
        }
    }

    private void newTrack() {
        builder.getModel().resetGame();
        switchStart.setEnabled(false);
        createLeft.setSelected(true);
    }

    private void changeLanguage() {
        setText(trackMenuLabels.getValue(TrackMenuLabels.TITLE));
        createLeft.setText(trackMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));
        createRight.setText(trackMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));
        editPoints.setText(trackMenuLabels.getValue(TrackMenuLabels.EDIT));
        deletePoint.setText(trackMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));
        switchStart.setText(trackMenuLabels.getValue(TrackMenuLabels.REVERSE));
        newTrack.setText(trackMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));
        saveTrack.setText(trackMenuLabels.getValue(TrackMenuLabels.SAVE));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "startDraw":
                createLeft.setSelected(true);
                break;
            case "startGame":
                this.setEnabled(false);
                break;
            case "buildTrack":
                this.setEnabled(true);
                break;
            case "startVisible":
                switchStart.setEnabled((boolean) evt.getNewValue());
                break;
            case "language":
                trackMenuLabels = new TrackMenuLabels(builder.getModel().getLanguage());
                changeLanguage();
                break;
        }
    }

}
