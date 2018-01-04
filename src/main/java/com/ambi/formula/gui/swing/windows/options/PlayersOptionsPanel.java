package com.ambi.formula.gui.swing.windows.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.MakeTurn;
import com.ambi.formula.gamemodel.datamodel.Formula;
import com.ambi.formula.gamemodel.labels.OptionsLabels;
import com.ambi.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class PlayersOptionsPanel extends JPanel implements PropertyChangeListener {

    private final GameModel model;
    private final JLabel labelShowTurns;
    private final JComboBox showTurns;

    private OptionsLabels optionLabels;

    public PlayersOptionsPanel(GameModel gameModel) {
        this.model = gameModel;
        this.model.addPropertyChangeListener(this);
        this.optionLabels = new OptionsLabels(model.getLanguage());

        labelShowTurns = new JLabel(optionLabels.getValue(OptionsLabels.SHOW_TURNS));
        showTurns = new JComboBox();

        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - 40, OptionsWindow.OPTIONS_HEIGHT / 4));
        setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 3));

        for (int i = 1; i <= model.getTurn().getFormulaCount(); i++) {
            add(new PlayerPanel(i));
        }

        add(createTurnsPanel());
    }

    private void showTurnsItemStateChanged(ItemEvent evt) {
        model.getTurn().setLengthHist(showTurns.getSelectedItem());
    }

    private JPanel createTurnsPanel() {
        labelShowTurns.setPreferredSize(new Dimension(110, 25));
        showTurns.setModel(new DefaultComboBoxModel(new Integer[]{MakeTurn.LENGTH_3, MakeTurn.LENGTH_5, MakeTurn.LENGTH_10, MakeTurn.LENGTH_20, MakeTurn.LENGTH_MAX}));
        showTurns.setSelectedItem(this.model.getTurn().getLengthHist());
        showTurns.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                showTurnsItemStateChanged(evt);
            }
        });

        JPanel turnsPanel = new JPanel();
        turnsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        turnsPanel.add(labelShowTurns);
        turnsPanel.add(showTurns);
        return turnsPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "language":
                optionLabels = new OptionsLabels(model.getLanguage());
                setBorder(BorderFactory.createTitledBorder(null, optionLabels.getValue(OptionsLabels.PLAYERS), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", 0, 14))); // NOI18N
                labelShowTurns.setText(optionLabels.getValue(OptionsLabels.SHOW_TURNS));
                break;
        }
    }

    private class PlayerPanel extends JPanel implements PropertyChangeListener {

        private final JTextField nameField;
        private final JPanel colorPanel;
        private final JLabel orderLabel;
        private final Formula formulaModel;

        public PlayerPanel(int formulaID) {
            this.formulaModel = model.getTurn().getFormula(formulaID);
            this.formulaModel.addPropertyChangeListener(this);

            nameField = new JTextField();
            orderLabel = new JLabel(formulaID + ".");
            colorPanel = new JPanel();

            formulaModel.setName(optionLabels.getValue(OptionsLabels.PLAYER) + " " + formulaID);
            formulaModel.setColor(getInitColor(formulaID));

            initComponents();
        }

        private void initComponents() {
            setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH - 60, 30));
            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

            nameField.setText(formulaModel.getName());
            nameField.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent evt) {
                    formulaModel.setName(nameField.getText());
                }
            });
            nameField.setPreferredSize(new Dimension(this.getPreferredSize().width / 2, 20));

            colorPanel.setBackground(new Color(formulaModel.getColor()));
            colorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            colorPanel.setPreferredSize(new Dimension(20, 20));
            colorPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    chooseColor();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    colorPanel.setBorder(new LineBorder(Color.black, 1));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    colorPanel.setBorder(new LineBorder(Color.blue, 2));
                }
            });

            GroupLayout colorPanel1Layout = new GroupLayout(colorPanel);
            colorPanel.setLayout(colorPanel1Layout);
            colorPanel1Layout.setHorizontalGroup(
                    colorPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGap(0, 20, Short.MAX_VALUE)
            );
            colorPanel1Layout.setVerticalGroup(
                    colorPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGap(0, 20, Short.MAX_VALUE)
            );

            add(orderLabel);
            add(nameField);
            add(colorPanel);
        }

        private void chooseColor() {
            Color color = JColorChooser.showDialog(colorPanel, "Choose a color for " + this.formulaModel.getName(), colorPanel.getBackground());
            if (color != null) {
                this.formulaModel.setColor(color.getRGB());
            }
        }

        private int getInitColor(int playerNumber) {
            int rgbColor;
            if (playerNumber == 1) {
                rgbColor = new Color(0, 204, 51).getRGB();
            } else if (playerNumber == 2) {
                rgbColor = new Color(255, 51, 51).getRGB();
            } else {
                rgbColor = new Color(50, 50, 255).getRGB();
            }
            return rgbColor;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            switch (evt.getPropertyName()) {
                case "color":
                    colorPanel.setBackground(new Color(formulaModel.getColor()));
                    break;
            }
        }
    }
}
