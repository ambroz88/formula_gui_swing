package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.enums.Language;
import com.ambroz.formula.gamemodel.labels.StatisticLabels;
import com.ambroz.formula.gamemodel.race.Formula;
import com.ambroz.formula.gui.swing.utils.Fonts;
import com.ambroz.formula.gui.swing.windows.OptionsWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FormulaPanel extends JPanel implements PropertyChangeListener {

    private static final int PANEL_HEIGHT = 85;

    private final Formula model;
    private StatisticLabels statLabels;

    private JLabel playerLabel;
    private JLabel movements;
    private JLabel distance;
    private JLabel waits;
    private JPanel colorPanel;

    public FormulaPanel(Formula formulaModel) {
        this.model = formulaModel;

        initComponents();
        addActions();
        addComponentsToPanel();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(TrackListComponent.LIST_WIDTH - OptionsWindow.FRAME_MARGIN, PANEL_HEIGHT));
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        playerLabel = new JLabel(model.getName());
        playerLabel.setForeground(new Color(model.getColor()));
        playerLabel.setFont(Fonts.PLAYER_NAME);

        colorPanel = new JPanel();
        colorPanel.setBackground(new Color(model.getColor()));
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorPanel.setPreferredSize(new Dimension(20, 20));

        movements = new JLabel();
        distance = new JLabel();

        waits = new JLabel("");
        waits.setForeground(new Color(model.getColor()));

    }

    private void addActions() {
        model.addPropertyChangeListener(this);

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
        JPanel player = new JPanel(new BorderLayout());
        player.add(playerLabel, BorderLayout.CENTER);
        player.add(colorPanel, BorderLayout.EAST);
        player.setPreferredSize(new Dimension(TrackListComponent.LIST_WIDTH - OptionsWindow.FRAME_MARGIN / 2, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 4, 0);
        add(player, c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        add(movements, c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 2, 0);
        add(distance, c);

        c.gridy = 3;
        add(waits, c);

    }

    public void setLanguage(Language language) {
        statLabels = new StatisticLabels(language);
        updateMoves();
        updateDistance();
    }

    public Formula getFormula() {
        return model;
    }

    private void chooseColor() {
        Color color = JColorChooser.showDialog(colorPanel, "Choose a color for " + model.getName(), colorPanel.getBackground());
        if (color != null) {
            this.model.setColor(color.getRGB());
        }
    }

    private void updateMoves() {
        movements.setText(statLabels.getValue(StatisticLabels.MOVES) + " " + model.getMoves());
    }

    private void updateDistance() {
        distance.setText(statLabels.getValue(StatisticLabels.DISTANCE) + " " + model.getDist());
    }

    private void updatePlayerName() {
        playerLabel.setText(model.getName());
    }

    private void updateColor() {
        Color color = new Color(model.getColor());

        playerLabel.setForeground(color);
        waits.setForeground(color);
        colorPanel.setBackground(color);
    }

    private void updateWait() {
        int stops = model.getWait();
        if (stops > 0) {
            waits.setText(stops + " " + statLabels.getValue(StatisticLabels.WAIT));
            waits.setVisible(true);
        } else {
            waits.setVisible(false);
        }
        waits.repaint();
    }

    public void reset() {
        updateMoves();
        updateDistance();
        updateWait();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.FORMULA_MOVES)) {
            updateMoves();
        } else if (evt.getPropertyName().equals(PropertyChanger.FORMULA_DISTANCE)) {
            updateDistance();
        } else if (evt.getPropertyName().equals(PropertyChanger.FORMULA_RESET)) {
            reset();
        } else if (evt.getPropertyName().equals(PropertyChanger.FORMULA_NAME)) {
            updatePlayerName();
        } else if (evt.getPropertyName().equals(PropertyChanger.FORMULA_COLOUR)) {
            updateColor();
        } else if (evt.getPropertyName().equals(PropertyChanger.FORMULA_WAIT)) {
//                updateWait();
        }
    }

}
