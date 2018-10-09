package com.ambroz.formula.gui.swing.windows;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.ambroz.formula.gamemodel.labels.DialogLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;

/**
 * This dialog serves for asking user if he wants to load new Track even if the race still running.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class ConfirmWindow extends JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    private static final int RET_NO = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    private static final int RET_YES = 1;

    private final RaceModel model;
    private final DialogLabels dialogLabels;
    private JTextArea hintLabel;
    private JButton noButton;
    private JButton yesButton;

    public ConfirmWindow(RaceModel raceModel) {
        this.model = raceModel;
        dialogLabels = new DialogLabels(model.getLanguage());

        initDialog();

        initComponents();
        addActions();
        addComponentsToDialog();

        pack();
    }

    private void initDialog() throws SecurityException {
        setPreferredSize(new Dimension(340, 115));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        setTitle(dialogLabels.getValue(DialogLabels.ATTENTION));
        setAlwaysOnTop(true);
        setResizable(false);
    }

    private void initComponents() {
        yesButton = new JButton(dialogLabels.getValue(DialogLabels.YES));
        getRootPane().setDefaultButton(yesButton);
        noButton = new JButton(dialogLabels.getValue(DialogLabels.NO));

        StyledDocument doc = new DefaultStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        hintLabel = new JTextArea(doc);
        hintLabel.setEditable(false);
        hintLabel.setBackground(new Color(240, 240, 240));
        hintLabel.setFont(new Font("Tahoma", 0, 12));
        hintLabel.setText(dialogLabels.getValue(DialogLabels.CONDITION)
                + "\n" + dialogLabels.getValue(DialogLabels.EVENT));

    }

    private void addActions() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                closeDialog();
            }
        });

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_NO);
            }
        });

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                yesButtonActionPerformed();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                noButtonActionPerformed();
            }
        });

    }

    private void addComponentsToDialog() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        add(hintLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void yesButtonActionPerformed() {
        doClose(RET_YES);
    }

    private void noButtonActionPerformed() {
        doClose(RET_NO);
    }

    /**
     * Closes the dialog
     */
    private void closeDialog() {
        doClose(RET_NO);
    }

    private void doClose(int retStatus) {
        if (retStatus == RET_YES) {
            model.fireLoadTrack();
        }
        dispose();
    }

}
