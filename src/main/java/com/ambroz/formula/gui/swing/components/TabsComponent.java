package com.ambroz.formula.gui.swing.components;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.labels.GeneralLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.drawing.RaceComponent;
import com.ambroz.formula.gui.swing.drawing.TrackBuilderComponent;
import com.ambroz.formula.gui.swing.menu.BuilderMenuBar;
import com.ambroz.formula.gui.swing.menu.RaceMenuBar;
import com.ambroz.formula.gui.swing.tools.BuilderMouseController;
import com.ambroz.formula.gui.swing.tools.RaceMouseController;
import com.ambroz.formula.gui.swing.utils.Fonts;

/**
 *
 * @author Jiri Ambroz
 */
public class TabsComponent extends JTabbedPane implements PropertyChangeListener {

    private final RaceModel raceModel;
    private final TrackBuilder builder;
    private GeneralLabels generalLabels;

    public TabsComponent(RaceModel gameModel, TrackBuilder trackBuilder) {
        super(JTabbedPane.LEFT);
        this.raceModel = gameModel;
        this.builder = trackBuilder;

        generalLabels = new GeneralLabels(raceModel.getLanguage().toString());
        raceModel.addPropertyChangeListener(this);

        initComponents();
    }

    private void initComponents() {
        addTab(null, createDrawPanel());
        addTab(null, createTrackBuilderPanel());

        setTabComponentAt(0, createVerticalLabel(" " + generalLabels.getValue(GeneralLabels.PLAY_GAME) + " "));
        setTabComponentAt(1, createVerticalLabel(" " + generalLabels.getValue(GeneralLabels.BUILD_TRACK) + " "));
    }

    private JPanel createDrawPanel() {
        RaceMenuBar raceBar = new RaceMenuBar(raceModel);
        RaceComponent drawPanel = new RaceComponent(raceModel);

        JScrollPane scroll = new JScrollPane(drawPanel);
        RaceMouseController scrollListener = new RaceMouseController(drawPanel, raceModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        JPanel racePanel = new JPanel(new BorderLayout());
        racePanel.add(raceBar, BorderLayout.NORTH);
        racePanel.add(scroll, BorderLayout.CENTER);
        return racePanel;
    }

    private JPanel createTrackBuilderPanel() {
        BuilderMenuBar builderBar = new BuilderMenuBar(builder);
        TrackBuilderComponent drawPanel = new TrackBuilderComponent(builder);

        JScrollPane scroll = new JScrollPane(drawPanel);
        BuilderMouseController scrollListener = new BuilderMouseController(drawPanel, builder);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        JPanel builderPanel = new JPanel(new BorderLayout());
        builderPanel.add(builderBar, BorderLayout.NORTH);
        builderPanel.add(scroll, BorderLayout.CENTER);
        return builderPanel;
    }

    private JLabel createVerticalLabel(String title) {
        JLabel labelGame = new JLabel(title);
        labelGame.setUI(new VerticalLabelUI(false));
        labelGame.setFont(Fonts.MENU_FONT);
        return labelGame;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.LANGUAGE)) {
            generalLabels = new GeneralLabels(raceModel.getLanguage().toString());

            JLabel raceLabel = (JLabel) getTabComponentAt(0);
            raceLabel.setText(" " + generalLabels.getValue(GeneralLabels.PLAY_GAME) + " ");
            setTabComponentAt(0, raceLabel);

            JLabel buildLabel = (JLabel) getTabComponentAt(1);
            buildLabel.setText(" " + generalLabels.getValue(GeneralLabels.BUILD_TRACK) + " ");
            setTabComponentAt(1, buildLabel);
        }
    }

}
