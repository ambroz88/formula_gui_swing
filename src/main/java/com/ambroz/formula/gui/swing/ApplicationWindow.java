package com.ambroz.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ambroz.formula.gamemodel.datamodel.Paper;
import com.ambroz.formula.gamemodel.labels.GeneralLabels;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.track.Track;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.components.BuilderMenuBar;
import com.ambroz.formula.gui.swing.components.RaceComponent;
import com.ambroz.formula.gui.swing.components.TopMenuBar;
import com.ambroz.formula.gui.swing.components.TrackBuilderComponent;
import com.ambroz.formula.gui.swing.components.TrackListComponent;
import com.ambroz.formula.gui.swing.components.VerticalLabelUI;
import com.ambroz.formula.gui.swing.tools.BuilderMouseController;
import com.ambroz.formula.gui.swing.tools.RaceMouseController;

/**
 * Component that shows main window with track and formulas and toolbar.
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class ApplicationWindow extends JFrame {

    private RaceModel raceModel;
    private TrackBuilder builder;
    private GeneralLabels generalLabels;
    private TrackListComponent trackList;
    private JTabbedPane tabs;

    public ApplicationWindow() {
        initWindow();
        initGameLogic();

        JPanel leftPanel = initLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        initTabs();
        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ApplicationWindow gameWindow = new ApplicationWindow();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        gameWindow.setSize(new Dimension(width, height));
    }

    private void initWindow() {
        setTitle("Formula Race 1.1");
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("Helmet 32x32.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        requestFocusInWindow(true);
        setMinimumSize(new Dimension(800, 600));
    }

    private void initGameLogic() {
        Paper paper = new Paper();
        String initLanguage = "EN";
        raceModel = new RaceModel(paper);
        raceModel.setLanguage(initLanguage);
        builder = new TrackBuilder(paper);
        builder.setLanguage(initLanguage);
        generalLabels = new GeneralLabels(initLanguage);
    }

    private JPanel initLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());

        TopMenuBar topMenu = new TopMenuBar(raceModel, builder);
        trackList = new TrackListComponent(raceModel, builder);
        JScrollPane trackSelectorPanel = new JScrollPane(trackList);
        trackSelectorPanel.setPreferredSize(new Dimension(150, 0));

        leftPanel.add(topMenu, BorderLayout.NORTH);
        leftPanel.add(trackSelectorPanel, BorderLayout.CENTER);
        return leftPanel;
    }

    private void initTabs() {
        tabs = new JTabbedPane(JTabbedPane.LEFT);

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int tabIndex = tabs.getSelectedIndex();
                if (tabIndex == 0) {
                    trackList.setActiveTab(TrackListComponent.RACE);
                    raceModel.setStage(RaceModel.FIRST_TURN);
                } else if (tabIndex == 1) {
                    trackList.setActiveTab(TrackListComponent.BUILD);
                    builder.startBuild(Track.LEFT);
                }
            }
        });
        JScrollPane scrollDrawPanel = createDrawPanel();
        JPanel trackBuilderPanel = createTrackBuilderPanel();
        tabs.addTab(null, scrollDrawPanel);
        tabs.addTab(null, trackBuilderPanel);

        tabs.setTabComponentAt(0, createVerticalLabel(" " + generalLabels.getValue(GeneralLabels.PLAY_GAME) + " "));
        tabs.setTabComponentAt(1, createVerticalLabel(" " + generalLabels.getValue(GeneralLabels.BUILD_TRACK) + " "));
    }

    private JScrollPane createDrawPanel() {
        RaceComponent drawPanel = new RaceComponent(raceModel);

        JScrollPane scroll = new JScrollPane(drawPanel);
        RaceMouseController scrollListener = new RaceMouseController(drawPanel, raceModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        return scroll;
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
        return labelGame;
    }

}
