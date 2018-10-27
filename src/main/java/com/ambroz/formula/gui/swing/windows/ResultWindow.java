package com.ambroz.formula.gui.swing.windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.Record;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class ResultWindow extends JDialog {

    public static final int RESULT_WIDTH = 400;
    public static final int RESULT_HEIGHT = 440;
    private RaceModel raceModel;

    public ResultWindow(RaceModel raceModel) {
        this.raceModel = raceModel;
        initDialog();
        addActions();
    }

    private void initDialog() throws SecurityException {
//        setTitle(optionLabels.getValue(OptionsLabels.TITLE));
        setTitle("Score Board for track " + raceModel.getTrack().getBoard().getName().toUpperCase());
        setAlwaysOnTop(true);
//        setResizable(false);
        setLayout(new GridLayout(0, 3, 5, 5));
        setSize(new Dimension(RESULT_WIDTH, RESULT_HEIGHT));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);

        generateResults();
    }

    private void generateResults() {
        List<Record> scoreList = raceModel.getTrack().getBoard().getResults();

        int resultSize = scoreList.size();
        if (resultSize > 10) {
            resultSize = 10;
        }

        for (int i = 0; i < resultSize; i++) {
            createRecordPanel(scoreList.get(resultSize - 1 - i), i);
        }

        createHeaderPanel();
        revalidate();
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

    }

    private void createHeaderPanel() {
        JLabel nameLabel = new JLabel("Player Name", JLabel.CENTER);
        JLabel distanceLabel = new JLabel("Distance", JLabel.CENTER);
        JLabel moveLabel = new JLabel("Number of Moves", JLabel.CENTER);

        add(nameLabel, 0, 0);
        add(distanceLabel, 0, 1);
        add(moveLabel, 0, 2);
    }

    private void createRecordPanel(Record record, int row) {
        JLabel nameLabel = new JLabel(record.getName(), JLabel.CENTER);
        JLabel distanceLabel = new JLabel("" + record.getDistance(), JLabel.CENTER);
        JLabel moveLabel = new JLabel("" + record.getMoves(), JLabel.CENTER);

        add(nameLabel, row, 0);
        add(distanceLabel, row, 1);
        add(moveLabel, row, 2);
    }
}
