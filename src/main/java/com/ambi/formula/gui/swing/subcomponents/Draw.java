package com.ambi.formula.gui.swing.subcomponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.ambi.formula.gamemodel.GameModel;
import com.ambi.formula.gamemodel.datamodel.Formula;
import com.ambi.formula.gamemodel.datamodel.Point;
import com.ambi.formula.gamemodel.datamodel.Polyline;
import com.ambi.formula.gamemodel.datamodel.Track;
import com.ambi.formula.gamemodel.datamodel.Turns;
import com.ambi.formula.gamemodel.utils.Calc;
import com.ambi.formula.gui.utils.Colors;

/**
 * This class represents panel with game.
 *
 * @author Jiri Ambroz
 */
public final class Draw extends JPanel implements PropertyChangeListener {

    private final GameModel gModel;
    private final float dash[] = {20, 10, 2, 10};

    public Draw(GameModel model) {
        gModel = model;
        gModel.addPropertyChangeListener(this);
        setBorder(new LineBorder(Color.black));
        updateSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color back, colorPoints;
        if (gModel.getStage() < GameModel.FIRST_TURN) {
            back = Color.white;
        } else {
            back = Colors.GAME_SAND;
        }
        g2.setBackground(back);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        Track track = gModel.getBuilder().getTrack();
        if (track.isReadyForDraw()) {
            //draw complete track
            g2.setColor(Colors.ROAD_SNOW);
            int[][] trackPoints = track.getTrack(gModel.gridSize());
            g2.fillPolygon(trackPoints[0], trackPoints[1], track.getIndex(Track.LEFT) + track.getIndex(Track.RIGHT) + 2);
        }
        //draw grid:
        g2.setColor(new Color(150, 150, 150));
        drawLine(g2, gModel.getPaper().getHorizontalLines());
        drawLine(g2, gModel.getPaper().getVerticalLines());
        //draw barriers of track
        drawTrack(g2);
        //draw formulas:
        g2.setStroke(new BasicStroke(2));
        drawFormule(g2, gModel.getTurn().getFormula(1));
        drawFormule(g2, gModel.getTurn().getFormula(2));
        //draw points:
        int player = gModel.getTurn().getActID();
        if (player == 0) {
            colorPoints = Color.BLUE;
        } else {
            colorPoints = new Color(gModel.getTurn().getFormula(player).getColor());
        }
        g2.setColor(colorPoints);
        if (gModel.getStage() > GameModel.FIRST_TURN) {
            drawTurns(g2);
        } else {
            drawPoints(g2);
        }

        drawTrackPoints(g2);
    }

    /**
     * It draws crosses in all points of the track - just in modification stage
     * of the game.
     *
     * @param g2 graphics
     */
    private void drawTrackPoints(Graphics2D g2) {
        if (gModel.getStage() == GameModel.EDIT_PRESS || gModel.getStage() == GameModel.EDIT_RELEASE) {
            for (int i = 1; i < gModel.getBuilder().left().getLength() - 1; i++) {
                drawCross(g2, gModel.getBuilder().left().getPoint(i));
            }
            for (int i = 1; i < gModel.getBuilder().right().getLength() - 1; i++) {
                drawCross(g2, gModel.getBuilder().right().getPoint(i));
            }
        }
    }

    /**
     * It draws points which are circles with radius 6.
     *
     * @param g graphics
     * @param data Polylinie of Points
     */
    private void drawPoint(Graphics g, Point center) {
        int dim = 6;
        if (center != null) {
            g.fillOval(center.getX() * gModel.gridSize() - dim / 2,
                    center.getY() * gModel.gridSize() - dim / 2, dim, dim);
        }
    }

    /**
     * It draws crosses which are 1/3 grid size big.
     *
     * @param g
     * @param data Polyline of Points where crosses will be draw
     */
    private void drawCross(Graphics g, Point center) {
        center = new Point(center.x * gModel.gridSize(), center.y * gModel.gridSize());
        int crossSize = (int) (0.33 * gModel.gridSize());
        Point vert1 = new Point(center.x - crossSize, center.y);
        Point vert2 = new Point(center.x + crossSize, center.y);
        Point hor1 = new Point(center.x, center.y - crossSize);
        Point hor2 = new Point(center.x, center.y + crossSize);

        g.drawLine(vert1.getX(), vert1.getY(), vert2.getX(), vert2.getY());
        g.drawLine(hor1.getX(), hor1.getY(), hor2.getX(), hor2.getY());
    }

    private void drawPoints(Graphics g) {
        Polyline data = gModel.getPoints();
        for (int i = 0; i < data.getLength(); i++) {
            drawPoint(g, data.getPoint(i));
        }

        data = gModel.getBadPoints();
        for (int i = 0; i < data.getLength(); i++) {
            drawCross(g, data.getPoint(i));
        }
    }

    private void drawTurns(Graphics g) {
        Turns turns = gModel.getTurns();
        for (int i = 0; i < turns.getSize(); i++) {
            if (turns.getTurn(i).isExist()) {
                Point actPoint = turns.getTurn(i).getPosition();
                if (turns.getTurn(i).getType() == 0) {
                    drawCross(g, actPoint);
                } else {
                    drawPoint(g, actPoint);
                }
            }
        }
    }

    /**
     * It draws parts of track (just barriers, start and finish).
     *
     * @param g2
     */
    private void drawTrack(Graphics2D g2) {
        Track track = gModel.getBuilder().getTrack();
        //left barrier:
        if (track.left().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getLeftWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, track.left().choose(track.getIndex(Track.LEFT), track.left().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, track.left().choose(0, track.getIndex(Track.LEFT)));
        }
        //right barrier:
        if (track.right().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getRightWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, track.right().choose(track.getIndex(Track.RIGHT), track.right().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, track.right().choose(0, track.getIndex(Track.RIGHT)));
        }

        //left parallel barrier:
        if (gModel.getStage() == 1 && track.getParallelLeft().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getLeftWidth() - 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, dash, 0));
            g2.setColor(Color.orange);
            drawPolyline(g2, track.getParallelLeft());
        }
        //right parallel barrier:
        if (gModel.getStage() == 2 && track.getParallelRight().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getRightWidth() - 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, dash, 0));
            g2.setColor(Color.orange);
            drawPolyline(g2, track.getParallelRight());
        }

        //draw start and finish (when track is ready):
        if (track.left().getLength() > 0 && track.right().getLength() > 0) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.PINK);
            drawLine(g2, track.getStart());
            if (track.getReady()) {
                drawLine(g2, track.getFinish());
                List<Polyline> lines = gModel.getBuilder().getCheckLines();
                g2.setStroke(new BasicStroke(1));
                for (Polyline line : lines) {
                    drawLine(g2, line);
                }
            }
        }
    }

    private void drawLine(Graphics g, Polyline line) {
        int gridSize = gModel.gridSize();
        for (int i = 0; i < line.getLength() - 1; i = i + 2) {
            g.drawLine(line.getPoint(i).getX() * gridSize, line.getPoint(i).getY() * gridSize,
                    line.getPoint(i + 1).getX() * gridSize, line.getPoint(i + 1).getY() * gridSize);
        }
    }

    private void drawPolyline(Graphics g, Polyline line) {
        int gridSize = gModel.gridSize();
        for (int i = 0; i < line.getLength() - 1; i++) {
            g.drawLine(line.getPoint(i).getX() * gridSize, line.getPoint(i).getY() * gridSize,
                    line.getPoint(i + 1).getX() * gridSize, line.getPoint(i + 1).getY() * gridSize);
        }
    }

    private void drawFormule(Graphics2D g, Formula form) {
        if (form.getLength() > 0) {
            g.setColor(new Color(form.getColor()));
            double arrowAngle = 0.5;//0.5235; //in radians ~ 30°
            double arrowLength = 0.6 * gModel.gridSize();
            int formulaLength = form.getLength();
            if (formulaLength > gModel.getTurn().getLengthHist()) {
                formulaLength = gModel.getTurn().getLengthHist() + 1;
            }
            for (int i = form.getLength() - formulaLength; i < form.getLength() - 1; i++) {
                //start of formula turn
                Point start = new Point(form.getPoint(i).x * gModel.gridSize(),
                        form.getPoint(i).y * gModel.gridSize());
                //end of formula turn
                Point end = new Point(form.getPoint(i + 1).x * gModel.gridSize(),
                        form.getPoint(i + 1).y * gModel.gridSize());

                g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());

                //------------------- DRAWING ARROW ---------------------
                //input points start and end must be already mulitplied by gridSize
                Point end1 = Calc.rotatePoint(start, end, arrowAngle, arrowLength);
                Point end2 = Calc.rotatePoint(start, end, -arrowAngle, arrowLength);
                g.drawLine(end.getX(), end.getY(), end1.getX(), end1.getY());
                g.drawLine(end.getX(), end.getY(), end2.getX(), end2.getY());
            }
        }
    }

    private void updateSize() {
        this.setPreferredSize(new Dimension(gModel.getPaper().getWidth() * gModel.gridSize(), gModel.getPaper().getHeight() * gModel.gridSize()));
        revalidate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("grid")) {
            updateSize();
        } else if (evt.getPropertyName().equals("repaint")) {
            repaint();
        } else if (evt.getPropertyName().contains("startDraw")) {
            repaint();
        } else if (evt.getPropertyName().equals("startGame")) {
            repaint();
        } else if (evt.getPropertyName().equals("paperWidth")) {
            updateSize();
        } else if (evt.getPropertyName().equals("paperHeight")) {
            updateSize();
        }
    }

}
