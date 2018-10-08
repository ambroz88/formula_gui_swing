package com.ambroz.formula.gui.swing.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.ambroz.formula.gamemodel.enums.FormulaType;
import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.labels.PrepareGameLabels;
import com.ambroz.formula.gamemodel.race.Formula;
import com.ambroz.formula.gamemodel.race.RaceModel;

/**
 *
 * @author Jiri Ambroz
 */
public final class StartGameWindow extends JFrame {

    private final RaceModel model;
    private final PrepareGameLabels gameLabels;

    public StartGameWindow(RaceModel gameModel) {
        this.model = gameModel;
        gameLabels = new PrepareGameLabels(model.getLanguage().toString());

        setTitle(gameLabels.getValue(PrepareGameLabels.TITLE));
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        setAlwaysOnTop(true);
        setResizable(false);
        setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH, 180));

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

        for (int i = 1; i <= this.model.getTurnMaker().getFormulaCount(); i++) {
            add(new PlayerPanel(i));
        }

        JButton start = new JButton(gameLabels.getValue(PrepareGameLabels.START_GAME));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                model.startGame();
                dispose();
            }
        });
        add(start);
        pack();
    }

    private class PlayerPanel extends JPanel implements PropertyChangeListener {

        private final JLabel orderLabel;
        private final JComboBox<String> playerType;
        private final JTextField nameField;
        private final JPanel colorPanel;
        private final Formula formulaModel;

        public PlayerPanel(int formulaID) {
            this.formulaModel = model.getTurnMaker().getFormula(formulaID);
            this.formulaModel.addPropertyChangeListener(this);

            orderLabel = new JLabel(formulaID + ".");
            playerType = new JComboBox<>(new String[]{gameLabels.getValue(FormulaType.Player.toString()),
                gameLabels.getValue(FormulaType.ComputerEasy.toString()),
                gameLabels.getValue(FormulaType.ComputerMedium.toString())});
            playerType.setSelectedItem(gameLabels.getValue(this.formulaModel.getType().toString()));
            playerType.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent evt) {
                    formulaModel.setType(FormulaType.getType(playerType.getSelectedIndex()));
                }
            });
            nameField = new JTextField();
            colorPanel = new JPanel();

            formulaModel.setName(new OptionsLabels(model.getLanguage().toString()).getValue(OptionsLabels.PLAYER) + " " + formulaID);

            formulaModel.setColor(getInitColor(formulaID));

            initComponents();
        }

        private void initComponents() {
            setPreferredSize(new Dimension(OptionsWindow.OPTIONS_WIDTH, 30));
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

            nameField.setText(formulaModel.getName());
            nameField.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent evt) {
                    formulaModel.setName(nameField.getText());
                }
            });
            nameField.setPreferredSize(new Dimension(this.getPreferredSize().width / 4, 20));

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
            add(playerType);
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
