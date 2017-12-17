package com.ambi.formula.gui.swing.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.MakeTurn;
import com.ambi.formula.gamemodel.labels.OptionsLabels;

/**
 *
 * @author Jiri Ambroz
 */
public final class OptionsWindow extends JFrame implements PropertyChangeListener {

    private final GameModel model;
    private boolean setModel;
    private OptionsLabels optionLabels;

    public OptionsWindow(GameModel model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);

        optionLabels = new OptionsLabels(this.model.getLanguage());

        setModel = true;
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        paperPanel = new javax.swing.JPanel();
        labelGrid = new javax.swing.JLabel();
        gridSpinner = new javax.swing.JSpinner();
        noteLabel = new javax.swing.JLabel();
        labelHeight = new javax.swing.JLabel();
        labelWidth = new javax.swing.JLabel();
        paperHeight = new javax.swing.JSpinner();
        paperWidth = new javax.swing.JSpinner();
        rulesPanel = new javax.swing.JPanel();
        labelFinish = new javax.swing.JLabel();
        labelTurns = new javax.swing.JLabel();
        comboTurns = new javax.swing.JComboBox<>();
        firstWins = new javax.swing.JRadioButton();
        roundEnd = new javax.swing.JRadioButton();
        collision = new javax.swing.JRadioButton();
        playersPanel = new javax.swing.JPanel();
        name1 = new javax.swing.JTextField();
        name2 = new javax.swing.JTextField();
        labelFirst = new javax.swing.JLabel();
        labelSecond = new javax.swing.JLabel();
        colorPanel1 = new javax.swing.JPanel();
        colorPanel2 = new javax.swing.JPanel();
        labelShowTurns = new javax.swing.JLabel();
        showTurns = new javax.swing.JComboBox();

        buttonGroup1.add(firstWins);
        buttonGroup1.add(roundEnd);
        buttonGroup1.add(collision);

        setTitle(optionLabels.getValue(OptionsLabels.TITLE));
        setAlwaysOnTop(true);
        setResizable(false);

        paperPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));

        labelGrid.setText(optionLabels.getValue(OptionsLabels.PAPER_SIZE));

        gridSpinner.setModel(new javax.swing.SpinnerNumberModel(this.model.gridSize(), 10, 35, 1));
        gridSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                gridSpinnerStateChanged(evt);
            }
        });

        noteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noteLabel.setText(optionLabels.getValue(OptionsLabels.NOTE));

        labelHeight.setText(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));

        labelWidth.setText(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));

        paperHeight.setModel(new javax.swing.SpinnerNumberModel(this.model.getPaperHeight(), 30, null, 10));
        paperHeight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                paperHeightStateChanged(evt);
            }
        });

        paperWidth.setModel(new javax.swing.SpinnerNumberModel(this.model.getPaperWidth(), 30, null, 10));
        paperWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                paperWidthStateChanged(evt);
            }
        });

        javax.swing.GroupLayout paperPanelLayout = new javax.swing.GroupLayout(paperPanel);
        paperPanel.setLayout(paperPanelLayout);
        paperPanelLayout.setHorizontalGroup(
            paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paperPanelLayout.createSequentialGroup()
                .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paperPanelLayout.createSequentialGroup()
                        .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(labelGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelHeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(paperPanelLayout.createSequentialGroup()
                        .addComponent(noteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paperHeight)
                    .addComponent(gridSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                    .addComponent(paperWidth, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(15, 15, 15))
        );
        paperPanelLayout.setVerticalGroup(
            paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paperPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGrid)
                    .addComponent(gridSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelWidth)
                    .addComponent(paperWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(paperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHeight)
                    .addComponent(paperHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noteLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        rulesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        labelFinish.setText(optionLabels.getValue(OptionsLabels.END_RULES));

        labelTurns.setText(optionLabels.getValue(OptionsLabels.NO_TURNS));

        comboTurns.setModel(new javax.swing.DefaultComboBoxModel<>(new Integer[] {MakeTurn.FOUR_TURNS, MakeTurn.FIVE_TURNS, MakeTurn.NINE_TURNS}));
        comboTurns.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTurnsItemStateChanged(evt);
            }
        });
        comboTurns.setSelectedItem(model.getTurn().getTurnsCount());

        if (model.getTurn().getFinishType() == MakeTurn.FIRST_WIN) {
            firstWins.setSelected(true);
        }
        firstWins.setText(optionLabels.getValue(OptionsLabels.RULE_FIRST));
        firstWins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstWinsActionPerformed(evt);
            }
        });

        if (model.getTurn().getFinishType() == MakeTurn.SECOND_CHANCE) {
            roundEnd.setSelected(true);
        }
        roundEnd.setText(optionLabels.getValue(OptionsLabels.RULE_SECOND));
        roundEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundEndActionPerformed(evt);
            }
        });

        collision.setText(optionLabels.getValue(OptionsLabels.RULE_COLISION));
        if (model.getTurn().getFinishType() == MakeTurn.COLLISION) {
            collision.setSelected(true);
        }
        collision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collisionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rulesPanelLayout = new javax.swing.GroupLayout(rulesPanel);
        rulesPanel.setLayout(rulesPanelLayout);
        rulesPanelLayout.setHorizontalGroup(
            rulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rulesPanelLayout.createSequentialGroup()
                .addGroup(rulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(collision, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(roundEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstWins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rulesPanelLayout.createSequentialGroup()
                .addGroup(rulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelFinish, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTurns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addComponent(comboTurns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rulesPanelLayout.setVerticalGroup(
            rulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rulesPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(rulesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTurns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTurns, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelFinish)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(firstWins)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundEnd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
                .addComponent(collision)
                .addContainerGap())
        );

        playersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        name1.setText(optionLabels.getValue(OptionsLabels.PLAYER1));
        model.getTurn().getFormula(1).setName(name1.getText());
        name1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                name1CaretUpdate(evt);
            }
        });

        name2.setText(optionLabels.getValue(OptionsLabels.PLAYER2));
        model.getTurn().getFormula(2).setName(name2.getText());
        name2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                name2CaretUpdate(evt);
            }
        });

        labelFirst.setText("1.");

        labelSecond.setText("2.");

        colorPanel1.setBackground(new java.awt.Color(0, 204, 51));
        model.getTurn().getFormula(1).setColor(colorPanel1.getBackground());
        colorPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorPanel1.setPreferredSize(new Dimension(20, 20));
        colorPanel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chooseColor(1, colorPanel1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moveBorder(1, Color.black, 1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                moveBorder(1, Color.blue, 2);
            }
        });

        javax.swing.GroupLayout colorPanel1Layout = new javax.swing.GroupLayout(colorPanel1);
        colorPanel1.setLayout(colorPanel1Layout);
        colorPanel1Layout.setHorizontalGroup(
            colorPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel1Layout.setVerticalGroup(
            colorPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel2.setBackground(new java.awt.Color(255, 51, 51));
        model.getTurn().getFormula(2).setColor(colorPanel2.getBackground());
        colorPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorPanel2.setPreferredSize(new Dimension(20, 20));
        colorPanel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                chooseColor(2, colorPanel2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moveBorder(2, Color.black, 1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                moveBorder(2, Color.blue, 2);
            }
        });

        javax.swing.GroupLayout colorPanel2Layout = new javax.swing.GroupLayout(colorPanel2);
        colorPanel2.setLayout(colorPanel2Layout);
        colorPanel2Layout.setHorizontalGroup(
            colorPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel2Layout.setVerticalGroup(
            colorPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        labelShowTurns.setText(optionLabels.getValue(OptionsLabels.SHOW_TURNS));

        showTurns.setModel(new javax.swing.DefaultComboBoxModel(new Integer[] {MakeTurn.LENGTH_3, MakeTurn.LENGTH_5, MakeTurn.LENGTH_10, MakeTurn.LENGTH_20, MakeTurn.LENGTH_MAX}));
        showTurns.setSelectedItem(this.model.getTurn().getLengthHist());
        showTurns.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showTurnsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout playersPanelLayout = new javax.swing.GroupLayout(playersPanel);
        playersPanel.setLayout(playersPanelLayout);
        playersPanelLayout.setHorizontalGroup(
            playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(playersPanelLayout.createSequentialGroup()
                        .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelFirst)
                            .addComponent(labelSecond))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(name2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(name1))
                        .addGap(31, 31, 31)
                        .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(colorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colorPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playersPanelLayout.createSequentialGroup()
                        .addComponent(labelShowTurns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showTurns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        playersPanelLayout.setVerticalGroup(
            playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelFirst)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelSecond)
                        .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(colorPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(playersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelShowTurns)
                    .addComponent(showTurns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rulesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(playersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(paperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rulesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(playersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gridSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_gridSpinnerStateChanged
        if (setModel) {
            model.setGridSize((int) gridSpinner.getValue());
        }
    }//GEN-LAST:event_gridSpinnerStateChanged

    private void name1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_name1CaretUpdate
        model.getTurn().getFormula(1).setName(name1.getText());
    }//GEN-LAST:event_name1CaretUpdate

    private void name2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_name2CaretUpdate
        model.getTurn().getFormula(2).setName(name2.getText());
    }//GEN-LAST:event_name2CaretUpdate

    private void firstWinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstWinsActionPerformed
        model.getTurn().setFinishType(MakeTurn.FIRST_WIN);
    }//GEN-LAST:event_firstWinsActionPerformed

    private void roundEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundEndActionPerformed
        model.getTurn().setFinishType(MakeTurn.SECOND_CHANCE);
    }//GEN-LAST:event_roundEndActionPerformed

    private void collisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collisionActionPerformed
        model.getTurn().setFinishType(MakeTurn.COLLISION);
    }//GEN-LAST:event_collisionActionPerformed

    private void comboTurnsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTurnsItemStateChanged
        model.getTurn().setTurns(Integer.valueOf(comboTurns.getSelectedItem().toString()));
    }//GEN-LAST:event_comboTurnsItemStateChanged

    private void showTurnsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showTurnsItemStateChanged
        model.getTurn().setLengthHist(showTurns.getSelectedItem());
    }//GEN-LAST:event_showTurnsItemStateChanged

    private void paperHeightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_paperHeightStateChanged
        if (setModel) {
            model.setPaperHeight(((Number) paperHeight.getValue()).intValue());
        }
    }//GEN-LAST:event_paperHeightStateChanged

    private void paperWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_paperWidthStateChanged
        if (setModel) {
            model.setPaperWidth(((Number) paperWidth.getValue()).intValue());
        }
    }//GEN-LAST:event_paperWidthStateChanged

    private void moveBorder(int id, Color color, int thickness) {
        if (id == 1) {
            colorPanel1.setBorder(new LineBorder(color, thickness));
        } else {
            colorPanel2.setBorder(new LineBorder(color, thickness));
        }
    }

    private void chooseColor(int id, JPanel panel) {
        Color color = JColorChooser.showDialog(panel, "Choose a color for " + model.getTurn().getFormula(id).getName(), panel.getBackground());
        if (color != null) {
            panel.setBackground(color);
            model.getTurn().getFormula(id).setColor(color);
        }
    }

    private void changeLanguage() {
        setTitle(optionLabels.getValue(OptionsLabels.TITLE));
        paperPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PAPER_TITLE), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));
        labelGrid.setText(optionLabels.getValue(OptionsLabels.PAPER_SIZE));
        labelWidth.setText(optionLabels.getValue(OptionsLabels.PAPER_WIDTH));
        labelHeight.setText(optionLabels.getValue(OptionsLabels.PAPER_HEIGHT));
        noteLabel.setText(optionLabels.getValue(OptionsLabels.NOTE));
        rulesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.RULE_TITLE), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));
        labelTurns.setText(optionLabels.getValue(OptionsLabels.NO_TURNS));
        labelFinish.setText(optionLabels.getValue(OptionsLabels.END_RULES));
        firstWins.setText(optionLabels.getValue(OptionsLabels.RULE_FIRST));
        roundEnd.setText(optionLabels.getValue(OptionsLabels.RULE_SECOND));
        collision.setText(optionLabels.getValue(OptionsLabels.RULE_COLISION));
        playersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14)));
        name1.setText(optionLabels.getValue(OptionsLabels.PLAYER1));
        name2.setText(optionLabels.getValue(OptionsLabels.PLAYER2));
        labelShowTurns.setText(optionLabels.getValue(OptionsLabels.SHOW_TURNS));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton collision;
    private javax.swing.JPanel colorPanel1;
    private javax.swing.JPanel colorPanel2;
    private javax.swing.JComboBox comboTurns;
    private javax.swing.JRadioButton firstWins;
    private javax.swing.JSpinner gridSpinner;
    private javax.swing.JLabel labelFinish;
    private javax.swing.JLabel labelFirst;
    private javax.swing.JLabel labelGrid;
    private javax.swing.JLabel labelHeight;
    private javax.swing.JLabel labelSecond;
    private javax.swing.JLabel labelShowTurns;
    private javax.swing.JLabel labelTurns;
    private javax.swing.JLabel labelWidth;
    private javax.swing.JTextField name1;
    private javax.swing.JTextField name2;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JSpinner paperHeight;
    private javax.swing.JPanel paperPanel;
    private javax.swing.JSpinner paperWidth;
    private javax.swing.JPanel playersPanel;
    private javax.swing.JRadioButton roundEnd;
    private javax.swing.JPanel rulesPanel;
    private javax.swing.JComboBox showTurns;
    // End of variables declaration//GEN-END:variables

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
                optionLabels = new OptionsLabels(this.model.getLanguage());
                changeLanguage();
                break;
        }
    }
}
