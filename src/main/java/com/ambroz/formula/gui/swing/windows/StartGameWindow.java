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
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.enums.FormulaType;
import com.ambroz.formula.gamemodel.labels.PrepareGameLabels;
import com.ambroz.formula.gamemodel.race.Formula;
import com.ambroz.formula.gamemodel.race.RaceModel;

/**
 *
 * @author Jiri Ambroz
 */
public final class StartGameWindow extends JDialog {

    private static final int PANEL_WIDTH = 340;

    private final RaceModel model;
    private JButton start;
    private final PrepareGameLabels gameLabels;

    public StartGameWindow(RaceModel gameModel) {
        this.model = gameModel;
        gameLabels = new PrepareGameLabels(model.getLanguage().toString());

        initComponent();
        addActions();
        addComponentsToPanel();

        pack();
    }

    private void initComponent() {
        setTitle(gameLabels.getValue(PrepareGameLabels.TITLE));
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        setAlwaysOnTop(true);
        setResizable(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, 180));

        start = new JButton(gameLabels.getValue(PrepareGameLabels.START_GAME));
        getRootPane().setDefaultButton(start);
    }

    private void addActions() {
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

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                model.resetPlayers();
                model.startGame();
                dispose();
            }
        });
    }

    private void addComponentsToPanel() {
        for (int i = 1; i <= this.model.getTurnMaker().getFormulaCount(); i++) {
            add(new StartPlayerPanel(i));
        }

        add(start);
    }

    private class StartPlayerPanel extends JPanel implements PropertyChangeListener {

        private final Formula formulaModel;
        private JLabel orderLabel;
        private JComboBox<String> playerType;
        private JTextField nameField;
        private JPanel colorPanel;

        public StartPlayerPanel(int formulaID) {
            this.formulaModel = model.getTurnMaker().getFormula(formulaID);

            initFormula(formulaID);

            initComponents(formulaID);
            addActions();
            addComponentsToPanel();
        }

        private void initFormula(int formulaID) {
            if (formulaModel.getName().isEmpty()) {
                formulaModel.setName(getInitName(formulaID));
            }

            if (formulaModel.getColor() == 0) {
                formulaModel.setColor(getInitColor(formulaID));
            }
        }

        private void initComponents(int formulaID) {
            setPreferredSize(new Dimension(PANEL_WIDTH, 30));
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

            orderLabel = new JLabel(formulaID + ".");
//            playerType = new JComboBox<>(new String[]{gameLabels.getValue(FormulaType.None.toString()), gameLabels.getValue(FormulaType.Player.toString()), gameLabels.getValue(FormulaType.ComputerEasy.toString()), gameLabels.getValue(FormulaType.ComputerMedium.toString())});
            playerType = new JComboBox<>(new String[]{gameLabels.getValue(FormulaType.Player.toString())});
            playerType.setPreferredSize(new Dimension(PANEL_WIDTH / 3, 20));

            nameField = new JTextField(formulaModel.getName());
            nameField.setPreferredSize(new Dimension(PANEL_WIDTH / 4, 20));

            colorPanel = new JPanel();
            colorPanel.setBackground(new Color(formulaModel.getColor()));
            colorPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            colorPanel.setPreferredSize(new Dimension(20, 20));

            if (formulaModel.getType() == FormulaType.None) {
                nameField.setEnabled(false);
                colorPanel.setVisible(false);
            }
        }

        private void addActions() {
            formulaModel.addPropertyChangeListener(this);

            playerType.setSelectedItem(gameLabels.getValue(this.formulaModel.getType().toString()));
            playerType.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent evt) {
                    formulaModel.setType(FormulaType.getType(playerType.getSelectedIndex()));
                    if (playerType.getSelectedIndex() != 0) {
                        nameField.setEnabled(true);
                        colorPanel.setVisible(true);
                    } else {
                        nameField.setEnabled(false);
                        colorPanel.setVisible(false);
                    }
                }
            });

            nameField.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent evt) {
                    formulaModel.setName(nameField.getText());
                }
            });

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
        }

        private void addComponentsToPanel() {
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
                rgbColor = new Color(50, 0, 200).getRGB();
            } else if (playerNumber == 2) {
                rgbColor = new Color(200, 0, 0).getRGB();
            } else if (playerNumber == 3) {
                rgbColor = new Color(0, 150, 0).getRGB();
            } else {
                rgbColor = new Color(150, 0, 150).getRGB();
            }
            return rgbColor;
        }

        private String getInitName(int playerNumber) {
            String name;
            if (playerNumber == 1) {
                name = "Alfred";
            } else if (playerNumber == 2) {
                name = "Christina";
            } else if (playerNumber == 3) {
                name = "Norbert";
            } else {
                name = "Victorie";
            }
            return name;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(PropertyChanger.FORMULA_COLOUR)) {
                colorPanel.setBackground(new Color(formulaModel.getColor()));
            }
        }

    }
}
