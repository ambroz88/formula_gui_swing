package com.ambroz.formula.gui.swing.components;

import com.ambroz.formula.gamemodel.GameModel;
import com.ambroz.formula.gamemodel.datamodel.Formula;
import com.ambroz.formula.gamemodel.datamodel.Point;
import com.ambroz.formula.gamemodel.datamodel.Polyline;
import com.ambroz.formula.gamemodel.datamodel.Segment;
import com.ambroz.formula.gamemodel.datamodel.Track;
import com.ambroz.formula.gamemodel.utils.Calc;
import com.ambroz.formula.gui.swing.utils.Colors;
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

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RaceComponent extends JPanel implements PropertyChangeListener {

    private final GameModel gameModel;

    public RaceComponent(GameModel gModel) {
        this.gameModel = gModel;
        gameModel.addPropertyChangeListener(this);
        gameModel.getPaper().addPropertyChangeListener(this);
        setBorder(new LineBorder(Color.black));
        updateSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color back, colorPoints;
        if (gameModel.getStage() < GameModel.FIRST_TURN) {
            back = Color.white;
        } else {
            back = Colors.GAME_SAND;
        }

        g2.setBackground(back);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        Track track = gameModel.getTrack();
        if (track.isReadyForDraw()) {
            //draw complete track
            g2.setColor(Colors.ROAD_SNOW);
            int[][] trackPoints = track.getCoordinates(gameModel.getPaper().getGridSize());
            g2.fillPolygon(trackPoints[0], trackPoints[1], track.getLeft().getLength() + track.getRight().getLength());
        }

        //draw grid:
        g2.setColor(new Color(150, 150, 150));
        for (Segment horizontalLine : gameModel.getPaper().getHorizontalLines()) {
            drawSegment(g2, horizontalLine);
        }
        for (Segment verticalLine : gameModel.getPaper().getVerticalLines()) {
            drawSegment(g2, verticalLine);
        }

        //draw barriers of track
        drawTrack(g2);

        //draw formulas:
        if (gameModel.getTurnMaker().getFormulaCount() > 0) {
            g2.setStroke(new BasicStroke(2));
            drawFormule(g2, gameModel.getTurnMaker().getFormula(1));

            //draw points:
            colorPoints = new Color(gameModel.getTurnMaker().getFormula(1).getColor());
            g2.setColor(colorPoints);
            if (gameModel.getStage() >= GameModel.FIRST_TURN) {
                drawTurns(g2);
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
            g.fillOval(center.getX() * gameModel.getPaper().getGridSize() - dim / 2,
                    center.getY() * gameModel.getPaper().getGridSize() - dim / 2, dim, dim);
        }
    }

    /**
     * It draws crosses which are 1/3 grid size big.
     *
     * @param g
     * @param data Polyline of Points where crosses will be draw
     */
    private void drawCross(Graphics g, Point center) {
        int gridSize = gameModel.getPaper().getGridSize();
        center = new Point(center.x * gridSize, center.y * gridSize);
        int crossSize = (int) (0.33 * gridSize);
        Point vert1 = new Point(center.x - crossSize, center.y);
        Point vert2 = new Point(center.x + crossSize, center.y);
        Point hor1 = new Point(center.x, center.y - crossSize);
        Point hor2 = new Point(center.x, center.y + crossSize);

        g.drawLine(vert1.getX(), vert1.getY(), vert2.getX(), vert2.getY());
        g.drawLine(hor1.getX(), hor1.getY(), hor2.getX(), hor2.getY());
    }

    private void drawTurns(Graphics g) {
        List<Point> possibleTurns = gameModel.getTurnMaker().getTurns().getFreePoints();
        for (Point point : possibleTurns) {
            drawPoint(g, point);
        }

        List<Point> crashes = gameModel.getTurnMaker().getTurns().getCollisionPoints();
        for (Point point : crashes) {
            drawCross(g, point);
        }
    }

    /**
     * It draws parts of track (just barriers, start and finish).
     *
     * @param g2
     */
    private void drawTrack(Graphics2D g2) {
        Track track = gameModel.getTrack();
        //left barrier:
        if (track.getLeft().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getLeftWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, track.getLeft().choose(track.getIndex(Track.LEFT), track.getLeft().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, track.getLeft().choose(0, track.getIndex(Track.LEFT)));
        }
        //right barrier:
        if (track.getRight().getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getRightWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, track.getRight().choose(track.getIndex(Track.RIGHT), track.getRight().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, track.getRight().choose(0, track.getIndex(Track.RIGHT)));
        }

        //draw start and finish (when track is ready):
        if (track.getStart() != null) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.PINK);
            drawSegment(g2, track.getStart());
            if (track.isReady()) {
                drawSegment(g2, track.getFinish());
            }
        }
    }

    private void drawSegment(Graphics g, Segment line) {
        int gridSize = gameModel.getPaper().getGridSize();
        g.drawLine(line.getFirst().getX() * gridSize, line.getFirst().getY() * gridSize,
                line.getLast().getX() * gridSize, line.getLast().getY() * gridSize);
    }

    private void drawPolyline(Graphics g, Polyline line) {
        int gridSize = gameModel.getPaper().getGridSize();
        for (int i = 0; i < line.getLength() - 1; i++) {
            g.drawLine(line.getPoint(i).getX() * gridSize, line.getPoint(i).getY() * gridSize,
                    line.getPoint(i + 1).getX() * gridSize, line.getPoint(i + 1).getY() * gridSize);
        }
    }

    private void drawFormule(Graphics2D g, Formula form) {
        if (!form.isEmpty()) {
            int gridSize = gameModel.getPaper().getGridSize();
            g.setColor(new Color(form.getColor()));
            double arrowAngle = 0.5;//0.5235; //in radians ~ 30°
            double arrowLength = 0.6 * gridSize;
//            int formulaLength = form.getLength();
//            if (formulaLength > gameModel.getTurnMaker().getLengthHist()) {
//                formulaLength = gameModel.getTurnMaker().getLengthHist() + 1;
//            }
            for (int i = 0; i < form.getLength() - 1; i++) {
                //start of formula turn
                Point start = new Point(form.getPoint(i).x * gridSize,
                        form.getPoint(i).y * gridSize);
                //end of formula turn
                Point end = new Point(form.getPoint(i + 1).x * gridSize,
                        form.getPoint(i + 1).y * gridSize);

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
        this.setPreferredSize(new Dimension(gameModel.getPaper().getWidth() * gameModel.getPaper().getGridSize(), gameModel.getPaper().getHeight() * gameModel.getPaper().getGridSize()));
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("repaint")) {
            repaint();
        } else if (evt.getPropertyName().contains("startDraw")) {
            repaint();
        } else if (evt.getPropertyName().equals("startGame")) {
            repaint();
        } else if (evt.getPropertyName().equals("grid")) {
            updateSize();
        } else if (evt.getPropertyName().equals("paperWidth")) {
            updateSize();
        } else if (evt.getPropertyName().equals("paperHeight")) {
            updateSize();
        }
    }

}
