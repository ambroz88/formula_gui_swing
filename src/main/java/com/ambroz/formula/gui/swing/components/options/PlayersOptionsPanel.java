package com.ambroz.formula.gui.swing.components.options;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.race.TurnMaker;
import com.ambroz.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class PlayersOptionsPanel extends JPanel implements PropertyChangeListener {

    private static final int PANEL_HEIGHT = 60;

    private final RaceModel model;
    private JLabel labelShowTurns;
    private JComboBox showTurns;

    private OptionsLabels optionLabels;

    public PlayersOptionsPanel(RaceModel gameModel) {
        this.model = gameModel;
        this.model.addPropertyChangeListener(this);
        this.optionLabels = new OptionsLabels(model.getLanguage().toString());

        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - OptionsWindow.FRAME_MARGIN, PANEL_HEIGHT));
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N

        initComponents();
        addActions();
        addComponentsToPanel();
    }

    private void initComponents() {
        labelShowTurns = new JLabel(optionLabels.getValue(OptionsLabels.SHOW_TURNS));
        labelShowTurns.setPreferredSize(new Dimension(140, 25));
        showTurns = new JComboBox(new DefaultComboBoxModel(new Integer[]{TurnMaker.LENGTH_3, TurnMaker.LENGTH_5, TurnMaker.LENGTH_10, TurnMaker.LENGTH_20, TurnMaker.LENGTH_MAX}));
    }

    private void addActions() {
        showTurns.setSelectedItem(model.getTurnMaker().getLengthHist());
        showTurns.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                showTurnsItemStateChanged();
            }
        });
    }

    private void addComponentsToPanel() {
        add(labelShowTurns);
        add(showTurns);
    }

    private void showTurnsItemStateChanged() {
        model.getTurnMaker().setLengthHist(showTurns.getSelectedItem().toString());
        model.repaintScene();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.LANGUAGE)) {
            optionLabels = new OptionsLabels(model.getLanguage().toString());
            setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N
            labelShowTurns.setText(optionLabels.getValue(OptionsLabels.SHOW_TURNS));
        }
    }

}
