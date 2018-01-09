package com.ambi.formula.gui.swing.windows.options;

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

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.datamodel.Paper;
import com.ambi.formula.gamemodel.labels.OptionsLabels;
import com.ambi.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class PaperOptionsPanel extends JPanel implements PropertyChangeListener {

    private final JSpinner gridSpinner;
    private final JSpinner paperHeight;
    private final JSpinner paperWidth;
    private final JLabel labelGrid;
    private final JLabel labelHeight;
    private final JLabel labelWidth;

    private final GameModel model;
    private final Paper paperModel;
    private OptionsLabels optionLabels;
    private boolean setModel;

    public PaperOptionsPanel(GameModel gameModel) {
        this.model = gameModel;
        this.model.addPropertyChangeListener(this);

        this.optionLabels = new OptionsLabels(gameModel.getLanguage());
        this.setModel = true;

        this.labelGrid = new JLabel();
        this.gridSpinner = new JSpinner();
        this.paperHeight = new JSpinner();
        this.paperWidth = new JSpinner();
        this.labelHeight = new JLabel();
        this.labelWidth = new JLabel();
        this.paperModel = gameModel.getPaper();
        this.paperModel.addPropertyChangeListener(this);

        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14)));
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - 40, OptionsWindow.OPTIONS_HEIGHT / 4));

        labelGrid.setText(optionLabels.getValue(OptionsLabels.PAPER_SIZE));
        labelHeight.setText(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));
        labelWidth.setText(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));

        gridSpinner.setModel(new SpinnerNumberModel(this.paperModel.getGridSize(), 10, 35, 1));
        gridSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                gridSpinnerStateChanged(evt);
            }
        });

        paperHeight.setModel(new SpinnerNumberModel(this.paperModel.getHeight(), 30, null, 10));
        paperHeight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                paperHeightStateChanged(evt);
            }
        });

        paperWidth.setModel(new SpinnerNumberModel(this.paperModel.getWidth(), 30, null, 10));
        paperWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                paperWidthStateChanged(evt);
            }
        });

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

    private void gridSpinnerStateChanged(ChangeEvent evt) {
        if (setModel) {
            this.paperModel.setGridSize((int) gridSpinner.getValue());
        }
    }

    private void paperHeightStateChanged(ChangeEvent evt) {
        if (setModel) {
            int newHeght = ((Number) paperHeight.getValue()).intValue();
            if (model.getBuilder().getTrack().getMaxHeight() < newHeght) {
                this.paperModel.setHeight(newHeght);
            }
        }
    }

    private void paperWidthStateChanged(ChangeEvent evt) {
        if (setModel) {
            int newWidth = ((Number) paperWidth.getValue()).intValue();
            if (model.getBuilder().getTrack().getMaxWidth() < newWidth) {
                this.paperModel.setWidth(newWidth);

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
                optionLabels = new OptionsLabels(model.getLanguage());
                setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14)));
                setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - 40, OptionsWindow.OPTIONS_HEIGHT / 4));

                labelGrid.setText(optionLabels.getValue(OptionsLabels.PAPER_SIZE));
                labelHeight.setText(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));
                labelWidth.setText(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));
                break;
        }
    }
}
