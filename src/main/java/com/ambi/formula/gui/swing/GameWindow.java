package com.ambi.formula.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gui.swing.subcomponents.Draw;
import com.ambi.formula.gui.utils.MouseDragging;

/**
 * Component that shows main window with track and formulas and toolbar.
 *
 * @author Jiri Ambroz
 */
public final class GameWindow extends JFrame {

    private final GameModel gModel;
    private Draw drawPanel;

    public GameWindow() {
        setTitle("Formula Race 5.0");
        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("Helmet 32x32.png"));
        setIconImage(img.getImage());
        setLayout(new BorderLayout());
        this.requestFocusInWindow(true);
        setMinimumSize(new Dimension(700, 400));

        gModel = new GameModel();
        gModel.setLanguage("EN");

        TopMenuBar topMenu = new TopMenuBar(gModel);
        JScrollPane scrollTrackPanel = new JScrollPane(new TracksComponent(gModel));
        scrollTrackPanel.setPreferredSize(new Dimension(150, 0));

        StatisticsComponent statistics = new StatisticsComponent(gModel);
        JScrollPane scrollDrawPanel = createDrawPanel();

        add(topMenu, BorderLayout.NORTH);
        add(scrollTrackPanel, BorderLayout.WEST);
        add(statistics, BorderLayout.SOUTH);
        add(scrollDrawPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        gameWindow.setSize(new Dimension(width, height));

    }

    private JScrollPane createDrawPanel() {
        drawPanel = new Draw(gModel);

        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = getRootPane().getActionMap();

        String leftForward = "leftForward";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), leftForward);
        actionMap.put(leftForward, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gModel.keyPressed(0);
            }
        });

        String rightForward = "rightForward";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), rightForward);
        actionMap.put(rightForward, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gModel.keyPressed(2);
            }
        });

        String leftBackward = "leftBackward";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), leftBackward);
        actionMap.put(leftBackward, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gModel.keyPressed(6);
            }
        });

        String rightBackward = "rightBackward";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), rightBackward);
        actionMap.put(rightBackward, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gModel.keyPressed(8);
            }
        });

//        this.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent arg0) {
//            }
//
//            @Override
//            public void keyReleased(KeyEvent arg0) {
//                if (arg0.getKeyCode() == KeyEvent.VK_7 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD7) {
//                    gModel.keyPressed(0);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_8 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD8) {
//                    gModel.keyPressed(1);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_9 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD9) {
//                    gModel.keyPressed(2);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_4 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD4) {
//                    gModel.keyPressed(3);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_5 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD5) {
//                    gModel.keyPressed(4);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_6 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD6) {
//                    gModel.keyPressed(5);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_1 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD1) {
//                    gModel.keyPressed(6);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_2 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD2) {
//                    gModel.keyPressed(7);
//                } else if (arg0.getKeyCode() == KeyEvent.VK_3 || arg0.getKeyCode() == KeyEvent.VK_NUMPAD3) {
//                    gModel.keyPressed(8);
//                }
//            }
//
//            @Override
//            public void keyPressed(KeyEvent arg0) {
//                System.out.println("");
//            }
//
//        });
        JScrollPane scroll = new JScrollPane(drawPanel);
        MouseDragging scrollListener = new MouseDragging(drawPanel, gModel);
        scroll.getViewport().addMouseMotionListener(scrollListener);
        scroll.getViewport().addMouseListener(scrollListener);

        return scroll;
    }
}
