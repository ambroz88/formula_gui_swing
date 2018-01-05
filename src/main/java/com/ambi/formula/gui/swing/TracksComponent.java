package com.ambi.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.datamodel.Track;
import com.ambi.formula.gamemodel.labels.HintLabels;
import com.ambi.formula.gamemodel.utils.TrackIO;
import com.ambi.formula.gui.swing.windows.ConfirmWindow;

/**
 *
 * @author Jiri Ambroz
 */
public final class TracksComponent extends JPanel implements ListSelectionListener, PropertyChangeListener {

    private final GameModel model;
    private final JLabel trackLabel;
    private JList<String> list;
    private int index;

    public TracksComponent(GameModel gameModel) {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.BLACK, 1));

        this.model = gameModel;
        this.model.addPropertyChangeListener(this);
        index = -1;

        trackLabel = new JLabel("     Available Tracks:     ");
        trackLabel.setHorizontalTextPosition(JLabel.CENTER);

        initTrackList();
        updateTracks();

        add(trackLabel, BorderLayout.NORTH);
        add(list, BorderLayout.CENTER);
    }

    private void initTrackList() {
        list = new JList<>();
        list.setLayout(new FlowLayout(FlowLayout.CENTER));
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

    private void updateTracks() {
        List<String> tracks = TrackIO.getAvailableTracks();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String track : tracks) {
            listModel.addElement(track);
        }

        list.setModel(listModel);
        repaint();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (this.model != null) {
            if (e.getFirstIndex() != index) {
                index = e.getFirstIndex();
                if (this.model.getStage() > GameModel.EDIT_RELEASE) {
                    ConfirmWindow conf = new ConfirmWindow(this.model);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    conf.setLocation(dim.width / 2 - conf.getWidth() / 2, dim.height / 2 - conf.getHeight() / 2);
                    conf.setVisible(true);
                } else {
                    loadSelectedTrack();
                }
            } else {
                index = -1;
            }
        }
    }

    private void loadSelectedTrack() {
        String name = list.getSelectedValue();
        if (name != null) {
            Track track = TrackIO.trackFromJSON(name, model);
            if (track != null) {
                model.resetGame();
                track.setReady(true);
                model.getBuilder().setTrack(track);
                model.loadTrackActions();
            } else {
                model.fireHint(HintLabels.WRONG_TRACK);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("newTrack")) {
            updateTracks();
            revalidate();
            repaint();
        } else if (evt.getPropertyName().equals("loadTrack")) {
            loadSelectedTrack();
        }
    }

}
