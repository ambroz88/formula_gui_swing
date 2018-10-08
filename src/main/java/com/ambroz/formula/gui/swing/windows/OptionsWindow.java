package com.ambroz.formula.gui.swing.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.ambroz.formula.gamemodel.labels.OptionsLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gui.swing.components.options.PaperOptionsPanel;
import com.ambroz.formula.gui.swing.components.options.PlayersOptionsPanel;
import com.ambroz.formula.gui.swing.components.options.RulesOptionsPanel;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class OptionsWindow extends JFrame implements PropertyChangeListener {

    public static final int OPTIONS_WIDTH = 260;
    public static final int OPTIONS_HEIGHT = 450;
    public static final int FRAME_MARGIN = 40;

    private final RaceModel raceModel;
    private OptionsLabels optionLabels;

    private JPanel paperPanel;
    private JPanel playersPanel;
    private JPanel rulesPanel;

    public OptionsWindow(RaceModel gameModel) {
        this.raceModel = gameModel;
        optionLabels = new OptionsLabels(raceModel.getLanguage().toString());

        setTitle(optionLabels.getValue(OptionsLabels.TITLE));
        setAlwaysOnTop(true);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        setPreferredSize(new Dimension(OPTIONS_WIDTH, OPTIONS_HEIGHT));

        initComponents();
        addActions();
        addComponents();
    }

    private void initComponents() {
        paperPanel = new PaperOptionsPanel(raceModel);
        rulesPanel = new RulesOptionsPanel(raceModel);
        playersPanel = new PlayersOptionsPanel(raceModel);
    }

    private void addActions() {
        raceModel.addPropertyChangeListener(this);

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

    private void addComponents() {
        add(paperPanel);
        add(rulesPanel);
        add(playersPanel);

        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("language")) {
            optionLabels = new OptionsLabels(raceModel.getLanguage().toString());
            setTitle(optionLabels.getValue(OptionsLabels.TITLE));
        }
    }
}
