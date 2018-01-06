package com.ambi.formula.gui.swing.windows.options;

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

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.MakeTurn;
import com.ambi.formula.gamemodel.labels.OptionsLabels;
import com.ambi.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class RulesOptionsPanel extends JPanel implements PropertyChangeListener {

    private final GameModel model;
    private final ButtonGroup buttonGroup;
    private final JRadioButton firstWins;
    private final JRadioButton roundEnd;
    private final JRadioButton collision;
    private final JComboBox comboTurns;
    private final JLabel labelTurns;
    private final JLabel labelFinish;
    private OptionsLabels optionLabels;

    public RulesOptionsPanel(GameModel gameModel) {
        this.model = gameModel;
        this.model.addPropertyChangeListener(this);
        this.optionLabels = new OptionsLabels(model.getLanguage());

        this.labelFinish = new JLabel();
        this.labelTurns = new JLabel();
        this.comboTurns = new JComboBox<>();
        this.firstWins = new JRadioButton();
        this.roundEnd = new JRadioButton();
        this.collision = new JRadioButton();

        this.buttonGroup = new ButtonGroup();
        buttonGroup.add(firstWins);
        buttonGroup.add(roundEnd);
        buttonGroup.add(collision);

        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - 40, OptionsWindow.OPTIONS_HEIGHT / 3));

        labelFinish.setText(optionLabels.getValue(OptionsLabels.END_RULES));
        labelTurns.setText(optionLabels.getValue(OptionsLabels.NO_TURNS));

        comboTurns.setModel(new DefaultComboBoxModel<>(new Integer[]{MakeTurn.FOUR_TURNS, MakeTurn.FIVE_TURNS, MakeTurn.NINE_TURNS}));
        comboTurns.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                comboTurnsItemStateChanged(evt);
            }
        });
        comboTurns.setSelectedItem(model.getTurnMaker().getTurnsCount());

        if (model.getTurnMaker().getFinishType() == MakeTurn.FIRST_WIN) {
            firstWins.setSelected(true);
        }
        firstWins.setText(optionLabels.getValue(OptionsLabels.RULE_FIRST));
        firstWins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                firstWinsActionPerformed(evt);
            }
        });

        if (model.getTurnMaker().getFinishType() == MakeTurn.SECOND_CHANCE) {
            roundEnd.setSelected(true);
        }
        roundEnd.setText(optionLabels.getValue(OptionsLabels.RULE_SECOND));
        roundEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                roundEndActionPerformed(evt);
            }
        });

        collision.setText(optionLabels.getValue(OptionsLabels.RULE_COLISION));
        if (model.getTurnMaker().getFinishType() == MakeTurn.COLLISION) {
            collision.setSelected(true);
        }
        collision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                collisionActionPerformed(evt);
            }
        });

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

    private void firstWinsActionPerformed(ActionEvent evt) {
        model.getTurnMaker().setFinishType(MakeTurn.FIRST_WIN);
    }

    private void roundEndActionPerformed(ActionEvent evt) {
        model.getTurnMaker().setFinishType(MakeTurn.SECOND_CHANCE);
    }

    private void collisionActionPerformed(ActionEvent evt) {
        model.getTurnMaker().setFinishType(MakeTurn.COLLISION);
    }

    private void comboTurnsItemStateChanged(ItemEvent evt) {
        model.getTurnMaker().setTurnsCount(Integer.valueOf(comboTurns.getSelectedItem().toString()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "language":
                optionLabels = new OptionsLabels(model.getLanguage());
                setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));
                labelTurns.setText(optionLabels.getValue(OptionsLabels.NO_TURNS));
                labelFinish.setText(optionLabels.getValue(OptionsLabels.END_RULES));
                firstWins.setText(optionLabels.getValue(OptionsLabels.RULE_FIRST));
                roundEnd.setText(optionLabels.getValue(OptionsLabels.RULE_SECOND));
                collision.setText(optionLabels.getValue(OptionsLabels.RULE_COLISION));
                break;
        }
    }
}
