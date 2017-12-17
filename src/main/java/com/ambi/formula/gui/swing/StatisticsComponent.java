package com.ambi.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gui.swing.subcomponents.StatisticPanel;

/**
 *
 *
 * @author Jiri Ambroz
 */
public final class StatisticsComponent extends JPanel implements PropertyChangeListener {

    private final GameModel model;
    private final StatisticPanel statisticPanel1, statisticPanel2;
    private final JLabel hint;

    public StatisticsComponent(GameModel model) {
        setLayout(new BorderLayout());
        setBackground(new Color(235, 255, 195));
        this.model = model;
        this.model.addPropertyChangeListener(this);

        statisticPanel1 = new StatisticPanel(model.getTurn().getFormula(1), model.getLanguage());
        statisticPanel2 = new StatisticPanel(model.getTurn().getFormula(2), model.getLanguage());

        hint = new JLabel("", JLabel.CENTER);
        hint.setFont(new Font("Arial", 0, 14));
        hint.setForeground(new Color(255, 0, 0));

        add(statisticPanel1, BorderLayout.WEST);
        add(hint, BorderLayout.CENTER);
        add(statisticPanel2, BorderLayout.EAST);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "hint":
                hint.setText((String) evt.getNewValue());
                hint.setFont(new Font("Arial", 0, 14));
                break;
            case "crash":
                hint.setText((String) evt.getNewValue());
                hint.setFont(new Font("Arial", 0, 20));
                break;
            case "winner":
                hint.setText((String) evt.getNewValue());
                hint.setFont(new Font("Arial", 0, 24));
                break;
            case "language":
                String labels = (String) evt.getNewValue();
                statisticPanel1.setLabels(labels);
                statisticPanel2.setLabels(labels);
                revalidate();
                updateUI();
                repaint();
                break;
        }
    }
}
