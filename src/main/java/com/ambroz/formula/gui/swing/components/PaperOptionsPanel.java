package com.ambroz.formula.gui.swing.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ambroz.formula.gamemodel.datamodel.Paper;
import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class PaperOptionsPanel extends JPanel implements PropertyChangeListener {

    private static final int PANEL_HEIGHT = 120;

    private final RaceModel raceModel;
    private final Paper paperModel;
    private OptionsLabels optionLabels;
    private boolean setModel;

    private JSpinner gridSpinner;
    private JSpinner paperHeight;
    private JSpinner paperWidth;
    private JLabel labelGrid;
    private JLabel labelHeight;
    private JLabel labelWidth;

    public PaperOptionsPanel(RaceModel gameModel) {
        raceModel = gameModel;
        paperModel = gameModel.getPaper();

        optionLabels = new OptionsLabels(gameModel.getLanguage().toString());
        setModel = true;

        initComponents();
        addActions();
        addComponentsToPanel();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14)));
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - OptionsWindow.FRAME_MARGIN, PANEL_HEIGHT));

        labelGrid = new JLabel(optionLabels.getValue(OptionsLabels.PAPER_SIZE));
        gridSpinner = new JSpinner(new SpinnerNumberModel(paperModel.getGridSize(), 10, 35, 1));
        paperHeight = new JSpinner(new SpinnerNumberModel(paperModel.getHeight(), 30, null, 10));
        paperWidth = new JSpinner(new SpinnerNumberModel(paperModel.getWidth(), 30, null, 10));
        labelHeight = new JLabel(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));
        labelWidth = new JLabel(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));
    }

    private void addActions() {
        raceModel.addPropertyChangeListener(this);
        paperModel.addPropertyChangeListener(this);

        gridSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                gridSpinnerStateChanged();
            }
        });

        paperHeight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                paperHeightStateChanged();
            }
        });

        paperWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                paperWidthStateChanged();
            }
        });
    }

    private void addComponentsToPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 5;
        c.ipady = 2;
        c.weightx = 0.7;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        add(labelGrid, c);
        c.gridy = 1;
        add(labelWidth, c);
        c.gridy = 2;
        add(labelHeight, c);

        c.weightx = 0.3;
        c.gridx = 1;
        c.gridy = 0;
        add(gridSpinner, c);
        c.gridy = 1;
        add(paperWidth, c);
        c.gridy = 2;
        add(paperHeight, c);
    }

    private void gridSpinnerStateChanged() {
        if (setModel) {
            paperModel.setGridSize((int) gridSpinner.getValue());
        }
    }

    private void paperHeightStateChanged() {
        if (setModel) {
            int oldValue = paperModel.getHeight();
            int newHeight = ((Number) paperHeight.getValue()).intValue();

            if (raceModel.getTrack().getMaxHeight() <= newHeight) {
                paperModel.setHeight(newHeight);
            } else {
                paperHeight.setValue(oldValue);
            }
        }
    }

    private void paperWidthStateChanged() {
        if (setModel) {
            int oldValue = paperModel.getWidth();
            int newWidth = ((Number) paperWidth.getValue()).intValue();

            if (raceModel.getTrack().getMaxWidth() <= newWidth) {
                paperModel.setWidth(newWidth);
            } else {
                paperWidth.setValue(oldValue);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "paperWidth":
                setModel = false;
                paperWidth.setValue(evt.getNewValue());
                setModel = true;
                break;
            case "paperHeight":
                setModel = false;
                paperHeight.setValue(evt.getNewValue());
                setModel = true;
                break;
            case "grid":
                setModel = false;
                gridSpinner.setValue(evt.getNewValue());
                setModel = true;
                break;
            case "language":
                optionLabels = new OptionsLabels(raceModel.getLanguage().toString());
                setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14)));

                labelGrid.setText(optionLabels.getValue(OptionsLabels.PAPER_SIZE));
                labelHeight.setText(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));
                labelWidth.setText(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));
                break;
        }
    }

}
