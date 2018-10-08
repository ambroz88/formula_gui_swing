package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.labels.GeneralLabels;
import com.ambroz.formula.gamemodel.labels.HintLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.Track;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gamemodel.utils.TrackIO;
import com.ambroz.formula.gui.swing.utils.Fonts;
import com.ambroz.formula.gui.swing.windows.ConfirmWindow;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class TrackListComponent extends JPanel implements ListSelectionListener, PropertyChangeListener {

    public static final int BUILD = 1;
    public static final int RACE = 0;
    public static final int LIST_WIDTH = 160;

    private final RaceModel raceModel;
    private final TrackBuilder builder;
    private GeneralLabels generalLabels;
    private CoordinatesPanel coordinates;
    private JLabel trackLabel;
    private JList<String> list;
    private int activeTab;

    public TrackListComponent(RaceModel gameModel, TrackBuilder trackBuilder) {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.BLACK, 1));

        raceModel = gameModel;
        builder = trackBuilder;
        generalLabels = new GeneralLabels(raceModel.getLanguage().toString());

        initComponents();
        addActions();
        addComponentsToPanel();
    }

    private void initComponents() {
        trackLabel = new JLabel(generalLabels.getValue(GeneralLabels.TRACK_TITLE));
        trackLabel.setPreferredSize(new Dimension(145, 30));
        trackLabel.setFont(Fonts.TITLE_FONT);

        list = new JList(TrackIO.getTracksArray());
        list.setLayout(new FlowLayout(FlowLayout.CENTER));

        coordinates = new CoordinatesPanel(raceModel, builder);
    }

    private void addActions() {
        this.raceModel.addPropertyChangeListener(this);
        this.builder.addPropertyChangeListener(this);

        list.addListSelectionListener(this);
        // Delete track when Delete is pressed
        list.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_DELETE && list.getSelectedIndex() != -1) {
                    String name = list.getSelectedValue();
                    TrackIO.deleteTrack(name);
                    updateTracks();
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }

        });
    }

    private void addComponentsToPanel() {
        add(trackLabel, BorderLayout.NORTH);
        add(list, BorderLayout.CENTER);
        add(coordinates, BorderLayout.SOUTH);
    }

    private void updateTracks() {
        list.setListData(TrackIO.getTracksArray());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (getActiveTab() == RACE) {

            if (raceModel.getStage() > RaceModel.FIRST_TURN) {
                callConfirmWindow();
            } else {
                loadTrackForRace();
            }

        } else if (getActiveTab() == BUILD) {
            loadTrackForBuilding();
        }
    }

    private void callConfirmWindow() throws HeadlessException {
        ConfirmWindow conf = new ConfirmWindow(raceModel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        conf.setLocation(dim.width / 2 - conf.getWidth() / 2, dim.height / 2 - conf.getHeight() / 2);
        conf.setVisible(true);
    }

    private void loadTrackForRace() {
        String name = list.getSelectedValue();
        if (name != null) {
            Track track = TrackIO.trackFromJSON(name);
            if (track != null) {
                raceModel.prepareGame(track);
            } else {
                raceModel.fireHint(HintLabels.WRONG_TRACK);
            }
        }
    }

    private void loadTrackForBuilding() {
        String name = list.getSelectedValue();
        if (name != null) {
            Track track = TrackIO.trackFromJSON(name);
            if (track != null) {
                builder.clearScene();
                builder.setTrack(track);
                builder.fireTrackReady(true);
                builder.repaintScene();
            } else {
                raceModel.fireHint(HintLabels.WRONG_TRACK);
            }
        }
    }

    public void setCoordinatesVisibility(boolean visibility) {
        coordinates.setVisible(visibility);
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.TRACK_SAVED)) {
            updateTracks();
        } else if (evt.getPropertyName().equals(PropertyChanger.RACE_LOAD_TRACK)) {
            loadTrackForRace();
        } else if (evt.getPropertyName().equals(PropertyChanger.LANGUAGE)) {
            generalLabels = new GeneralLabels(raceModel.getLanguage().toString());
            trackLabel.setText(generalLabels.getValue(GeneralLabels.TRACK_TITLE));
        }
    }

}
