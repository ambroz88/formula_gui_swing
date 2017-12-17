package com.ambi.formula.gui.swing.subcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.ambi.formula.gamemodel.datamodel.Formula;
import com.ambi.formula.gamemodel.labels.StatisticLabels;

/**
 * This class represents panel for one formula where are shown distance, moves
 * and some other characteristics of formula. Formula serves as a model.
 *
 * @author Jiri Ambroz
 */
public final class StatisticPanel extends JPanel implements PropertyChangeListener {

    private final JLabel playerLabel, finish, movements, distance;
    private final Formula model;
    private StatisticLabels statLabels;

    public StatisticPanel(Formula f, String language) {
        this.setLayout(new GridLayout(2, 2));
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setPreferredSize(new Dimension(250, 40));
        setBackground(new Color(255, 255, 202));
        this.model = f;
        this.model.addPropertyChangeListener(this);

        statLabels = new StatisticLabels(language);

        playerLabel = new JLabel(f.getName(), JLabel.CENTER);
        playerLabel.setForeground(f.getColor());
        playerLabel.setFont(new Font("Tahoma", 1, 13));

        finish = new JLabel("", JLabel.CENTER);
        finish.setForeground(f.getColor());

        movements = new JLabel();
        movements.setText(statLabels.getValue(StatisticLabels.MOVES) + " 0");
        distance = new JLabel();
        distance.setText(statLabels.getValue(StatisticLabels.DISTANCE) + " 0");

        this.add(playerLabel);
        this.add(movements);
        this.add(finish);
        this.add(distance);
    }

    public void setMoves(int number) {
        movements.setText(statLabels.getValue(StatisticLabels.MOVES) + "" + number);
        movements.repaint();
    }

    public void setDistance(double number) {
        distance.setText(statLabels.getValue(StatisticLabels.DISTANCE) + "" + number);
        distance.repaint();
    }

    public void setPlayerName(String name) {
        playerLabel.setText(name);
        playerLabel.repaint();
    }

    public void setColor(Color color) {
        playerLabel.setForeground(color);
        finish.setForeground(color);
        playerLabel.repaint();
        finish.repaint();
    }

    public void setFinish(int stops) {
        if (stops > 0) {
            finish.setText(stops + " " + statLabels.getValue(StatisticLabels.WAIT));
            finish.setVisible(true);
        } else {
            finish.setVisible(false);
        }
        finish.repaint();
    }

    public void setLabels(String language) {
        statLabels = new StatisticLabels(language);
        movements.setText(statLabels.getValue(StatisticLabels.MOVES));
        distance.setText(statLabels.getValue(StatisticLabels.DISTANCE));
    }

    public void reset() {
        movements.setText(statLabels.getValue(StatisticLabels.MOVES) + " 0");
        distance.setText(statLabels.getValue(StatisticLabels.DISTANCE) + " 0");
        movements.repaint();
        distance.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "move":
                setMoves(model.getMoves());
                break;
            case "dist":
                setDistance(model.getDist());
                break;
            case "reset":
                reset();
                break;
            case "name":
                setPlayerName(model.getName());
                break;
            case "color":
                setColor(model.getColor());
                break;
            case "stop":
                setFinish(model.getWait());
                break;
        }
    }

}
