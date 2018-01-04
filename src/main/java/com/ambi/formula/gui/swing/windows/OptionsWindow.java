package com.ambi.formula.gui.swing.windows;

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

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.labels.OptionsLabels;
import com.ambi.formula.gui.swing.windows.options.PaperOptionsPanel;
import com.ambi.formula.gui.swing.windows.options.PlayersOptionsPanel;
import com.ambi.formula.gui.swing.windows.options.RulesOptionsPanel;

/**
 *
 * @author Jiri Ambroz
 */
public final class OptionsWindow extends JFrame implements PropertyChangeListener {

    public static final int OPTIONS_WIDTH = 260;
    public static final int OPTIONS_HEIGHT = 530;

    private final JPanel paperPanel;
    private final JPanel playersPanel;
    private final JPanel rulesPanel;
    private final GameModel gameModel;

    public OptionsWindow(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.addPropertyChangeListener(this);

        paperPanel = new PaperOptionsPanel(this.gameModel);
        rulesPanel = new RulesOptionsPanel(this.gameModel);
        playersPanel = new PlayersOptionsPanel(this.gameModel);

        setTitle(new OptionsLabels(this.gameModel.getLanguage()).getValue(OptionsLabels.TITLE));
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

    private void initComponents() {
        setAlwaysOnTop(true);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        setPreferredSize(new Dimension(OPTIONS_WIDTH, OPTIONS_HEIGHT));

        add(paperPanel);
        add(rulesPanel);
        add(playersPanel);

        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "language":
                setTitle(new OptionsLabels(this.gameModel.getLanguage()).getValue(OptionsLabels.TITLE));
                break;
        }
    }
}
