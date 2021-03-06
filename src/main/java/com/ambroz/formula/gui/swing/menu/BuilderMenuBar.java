package com.ambroz.formula.gui.swing.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.enums.Side;
import com.ambroz.formula.gamemodel.labels.TrackMenuLabels;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.components.HintPanel;
import com.ambroz.formula.gui.swing.windows.SaveTrackWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class BuilderMenuBar extends JMenuBar implements PropertyChangeListener {

    private final TrackBuilder builder;
    private TrackMenuLabels builderMenuLabels;
    private HintPanel hintPanel;

    private JButton createLeft;
    private JButton createRight;
    private JButton editPoints;
    private JButton switchStart;
    private JButton deletePoint;
    private JButton newTrack;
    private JButton saveTrack;

    public BuilderMenuBar(TrackBuilder gModel) {
        this.builder = gModel;
        builderMenuLabels = new TrackMenuLabels(this.builder.getLanguage());

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
        createLeft = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Left 36x36.png")));
        createRight = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Right 36x36.png")));
        editPoints = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Edit 36x36.png")));

        switchStart = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Switch 36x36.png")));
        switchStart.setEnabled(false);
        deletePoint = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Back 36x36.png")));
        newTrack = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Delete All 36x36.png")));
        saveTrack = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Save 36x36.png")));
        saveTrack.setEnabled(false);

        hintPanel = new HintPanel(builder);
    }

    private void addInsets() {
        Insets emptyInsets = new Insets(0, 0, 0, 0);
        createLeft.setMargin(emptyInsets);
        createRight.setMargin(emptyInsets);
        editPoints.setMargin(emptyInsets);
        switchStart.setMargin(emptyInsets);
        deletePoint.setMargin(emptyInsets);
        newTrack.setMargin(emptyInsets);
        saveTrack.setMargin(emptyInsets);
    }

    private void addToolTips() {
        createLeft.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_LEFT));
        createRight.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.BUILD_RIGHT));
        editPoints.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.EDIT));
        switchStart.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.REVERSE));
        deletePoint.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.DELETE_LAST));
        newTrack.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.NEW_TRACK));
        saveTrack.setToolTipText(builderMenuLabels.getValue(TrackMenuLabels.SAVE));
    }

    private void addActions() {
        builder.addPropertyChangeListener(this);

        createLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Side.Left);
            }
        });

        createRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startBuild(Side.Right);
            }
        });

        editPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.startEditing();
            }
        });

        switchStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.switchTrack();
            }
        });

        deletePoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.deletePoint();
            }
        });

        newTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                builder.clearScene();
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

    private void addButtonsToBar() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttons.add(createLeft);
        buttons.add(createRight);
        buttons.add(editPoints);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(0, 0, 20, 40);
        buttons.add(separator);

        buttons.add(switchStart);
        buttons.add(deletePoint);
        buttons.add(newTrack);
        buttons.add(saveTrack);

        add(buttons, BorderLayout.WEST);
        add(hintPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.LANGUAGE)) {
            builderMenuLabels = new TrackMenuLabels(builder.getLanguage());
            addToolTips();
        } else if (evt.getPropertyName().equals(PropertyChanger.TRACK_READY)) {
            saveTrack.setEnabled((Boolean) evt.getNewValue());
            switchStart.setEnabled((Boolean) evt.getNewValue());
        }
    }

}
