package com.ambroz.formula.gui.swing.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.ambroz.formula.gamemodel.labels.DialogLabels;
import com.ambroz.formula.gamemodel.labels.HintLabels;
import com.ambroz.formula.gamemodel.track.TrackBuilder;

/**
 * This dialog serves for asking user if he wants to load new Track even if the race still running.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class SaveTrackWindow extends JDialog {

    private final TrackBuilder builder;
    private final DialogLabels dialogLabels;
    private JLabel hintLabel;
    private JTextField nameField;
    private boolean saved;

    public SaveTrackWindow(TrackBuilder trackBuilder) {
        super();
        this.builder = trackBuilder;
        dialogLabels = new DialogLabels(this.builder.getLanguage());

        setPreferredSize(new Dimension(330, 110));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        setTitle(dialogLabels.getValue(DialogLabels.ATTENTION));
        setAlwaysOnTop(true);
        setResizable(false);

        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose();
            }
        });

    }

    // <editor-fold defaultstate="collapsed" desc="Initialization of GUI">
    private void initComponents() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                doClose();
            }
        });

        hintLabel = new JLabel("<html><div style='text-align: center;'>" + dialogLabels.getValue(DialogLabels.SAVE_LABEL) + "</div></html>");
        hintLabel.setFont(new Font("Tahoma", 0, 12));

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 30));

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                saved = builder.saveTrack(nameField.getText());
                doClose();
            }
        });

        add(hintLabel);
        add(nameField);

        pack();
    }// </editor-fold>

    private void doClose() {
        if (!saved) {
            builder.fireHint(HintLabels.HINT_FAILED);
        }
        dispose();
    }

}
