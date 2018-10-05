package com.ambroz.formula.gui.swing.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.race.TurnMaker;
import com.ambroz.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RulesOptionsPanel extends JPanel implements PropertyChangeListener {

    private static final int PANEL_HEIGHT = 170;

    private final RaceModel model;
    private OptionsLabels optionLabels;

    private ButtonGroup buttonGroup;
    private JRadioButton firstWins;
    private JRadioButton roundEnd;
    private JRadioButton collision;
    private JComboBox comboTurns;
    private JLabel labelTurns;
    private JLabel labelFinish;

    public RulesOptionsPanel(RaceModel gameModel) {
        this.model = gameModel;
        this.optionLabels = new OptionsLabels(model.getLanguage().toString());

        initComponents();
        addActions();
        addComponentsToPanel();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - OptionsWindow.FRAME_MARGIN, PANEL_HEIGHT));

        labelFinish = new JLabel(optionLabels.getValue(OptionsLabels.END_RULES));
        labelTurns = new JLabel(optionLabels.getValue(OptionsLabels.NO_TURNS));
        comboTurns = new JComboBox<>(new DefaultComboBoxModel<>(new Integer[]{TurnMaker.FOUR_TURNS, TurnMaker.FIVE_TURNS, TurnMaker.NINE_TURNS}));
        firstWins = new JRadioButton(optionLabels.getValue(OptionsLabels.RULE_FIRST));
        roundEnd = new JRadioButton(optionLabels.getValue(OptionsLabels.RULE_SECOND));
        collision = new JRadioButton(optionLabels.getValue(OptionsLabels.RULE_COLISION));

        buttonGroup = new ButtonGroup();
    }

    private void addActions() {
        model.addPropertyChangeListener(this);

        comboTurns.setSelectedItem(model.getTurnMaker().getTurnsCount());
        comboTurns.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                comboTurnsItemStateChanged();
            }
        });

        firstWins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                firstWinsActionPerformed();
            }
        });

        roundEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                roundEndActionPerformed();
            }
        });

        collision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                collisionActionPerformed();
            }
        });

        if (model.getTurnMaker().getFinishType() == TurnMaker.WIN_FIRST) {
            firstWins.setSelected(true);
        } else if (model.getTurnMaker().getFinishType() == TurnMaker.WIN_LAST_TURN) {
            roundEnd.setSelected(true);
        } else if (model.getTurnMaker().getFinishType() == TurnMaker.WIN_COLLISION) {
            collision.setSelected(true);
        }

    }

    private void addComponentsToPanel() {
        buttonGroup.add(firstWins);
        buttonGroup.add(roundEnd);
        buttonGroup.add(collision);

        GroupLayout rulesPanelLayout = new GroupLayout(this);
        setLayout(rulesPanelLayout);
        rulesPanelLayout.setHorizontalGroup(
                rulesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, rulesPanelLayout.createSequentialGroup()
                                .addGroup(rulesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(collision, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(roundEnd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(firstWins, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, rulesPanelLayout.createSequentialGroup()
                                .addGroup(rulesPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(labelFinish, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelTurns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(16, 16, 16)
                                .addComponent(comboTurns, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        rulesPanelLayout.setVerticalGroup(
                rulesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(rulesPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(rulesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboTurns, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelTurns, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelFinish)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(firstWins)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundEnd)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
                                .addComponent(collision)
                                .addContainerGap())
        );

    }

    private void firstWinsActionPerformed() {
        model.getTurnMaker().setFinishType(TurnMaker.WIN_FIRST);
    }

    private void roundEndActionPerformed() {
        model.getTurnMaker().setFinishType(TurnMaker.WIN_LAST_TURN);
    }

    private void collisionActionPerformed() {
        model.getTurnMaker().setFinishType(TurnMaker.WIN_COLLISION);
    }

    private void comboTurnsItemStateChanged() {
        model.getTurnMaker().setTurnsCount(Integer.valueOf(comboTurns.getSelectedItem().toString()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("language")) {
            optionLabels = new OptionsLabels(model.getLanguage().toString());
            setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));
            labelTurns.setText(optionLabels.getValue(OptionsLabels.NO_TURNS));
            labelFinish.setText(optionLabels.getValue(OptionsLabels.END_RULES));
            firstWins.setText(optionLabels.getValue(OptionsLabels.RULE_FIRST));
            roundEnd.setText(optionLabels.getValue(OptionsLabels.RULE_SECOND));
            collision.setText(optionLabels.getValue(OptionsLabels.RULE_COLISION));
        }
    }

}
