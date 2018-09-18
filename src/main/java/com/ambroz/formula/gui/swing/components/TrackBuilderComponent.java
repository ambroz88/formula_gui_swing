package com.ambroz.formula.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.ambroz.formula.gamemodel.datamodel.Paper;
import com.ambroz.formula.gamemodel.datamodel.Point;
import com.ambroz.formula.gamemodel.datamodel.Polyline;
import com.ambroz.formula.gamemodel.datamodel.Segment;
import com.ambroz.formula.gamemodel.track.Track;
import com.ambroz.formula.gamemodel.track.TrackBuilder;
import com.ambroz.formula.gui.swing.utils.Colors;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TrackBuilderComponent extends JPanel implements PropertyChangeListener {

    private final TrackBuilder builder;

    public TrackBuilderComponent(TrackBuilder trackBuilder) {
        this.builder = trackBuilder;
        this.builder.addPropertyChangeListener(this);
        setBorder(new LineBorder(Color.black));
        updateSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        if (builder.isReadyForDraw()) {
            //draw complete track
            g2.setColor(Colors.ROAD_SNOW);
            int[][] trackPoints = builder.getCoordinates(builder.getPaper().getGridSize());
            g2.fillPolygon(trackPoints[0], trackPoints[1], builder.getLeft().getLength() + builder.getRight().getLength());
        }

        drawGrid(g2);

        drawTrack(g2);

        g2.setColor(Color.BLUE);
        drawPoints(g2);
        drawTrackPoints(g2);
    }

    private void updateSize() {
        Paper paper = builder.getPaper();
        this.setPreferredSize(new Dimension(TrackBuilder.DIMENSION * paper.getGridSize(), TrackBuilder.DIMENSION * paper.getGridSize()));
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(150, 150, 150));

        for (Segment horizontalLine : builder.getPaper().getHorizontalLines()) {
            drawSegment(g2, horizontalLine);
        }
        for (Segment verticalLine : builder.getPaper().getVerticalLines()) {
            drawSegment(g2, verticalLine);
        }
    }

    /**
     * It draws crosses which are 1/3 grid size big.
     *
     * @param g
     * @param data Polyline of Points where crosses will be draw
     */
    private void drawCross(Graphics g, Point center) {
        int gridSize = builder.getPaper().getGridSize();
        center = new Point(center.x * gridSize, center.y * gridSize);
        int crossSize = (int) (0.33 * gridSize);
        Point vert1 = new Point(center.x - crossSize, center.y);
        Point vert2 = new Point(center.x + crossSize, center.y);
        Point hor1 = new Point(center.x, center.y - crossSize);
        Point hor2 = new Point(center.x, center.y + crossSize);

        g.drawLine(vert1.getX(), vert1.getY(), vert2.getX(), vert2.getY());
        g.drawLine(hor1.getX(), hor1.getY(), hor2.getX(), hor2.getY());
    }

    /**
     * It draws parts of track (just barriers, start and finish).
     *
     * @param g2
     */
    private void drawTrack(Graphics2D g2) {
        //left barrier:
        if (builder.getLeft().getLength() > 1) {
            g2.setStroke(new BasicStroke(builder.getLeftWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, builder.getLeft().choose(builder.getIndex(Track.LEFT), builder.getLeft().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, builder.getLeft().choose(0, builder.getIndex(Track.LEFT)));
        }
        //right barrier:
        if (builder.getRight().getLength() > 1) {
            g2.setStroke(new BasicStroke(builder.getRightWidth()));
            g2.setColor(Color.red);
            drawPolyline(g2, builder.getRight().choose(builder.getIndex(Track.RIGHT), builder.getRight().getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, builder.getRight().choose(0, builder.getIndex(Track.RIGHT)));
        }

        //draw start and finish (when track is ready):
        if (builder.getStart() != null) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.PINK);
            drawSegment(g2, builder.getStart());
            if (builder.isReady()) {
                drawSegment(g2, builder.getFinish());
            }
        }
    }

    private void drawSegment(Graphics g, Segment line) {
        int gridSize = builder.getPaper().getGridSize();
        g.drawLine(line.getFirst().getX() * gridSize, line.getFirst().getY() * gridSize,
                line.getLast().getX() * gridSize, line.getLast().getY() * gridSize);
    }

    private void drawPolyline(Graphics g, Polyline line) {
        int gridSize = builder.getPaper().getGridSize();
        for (int i = 0; i < line.getLength() - 1; i++) {
            g.drawLine(line.getPoint(i).getX() * gridSize, line.getPoint(i).getY() * gridSize,
                    line.getPoint(i + 1).getX() * gridSize, line.getPoint(i + 1).getY() * gridSize);
        }
    }

    private void drawPoints(Graphics g) {
        Polyline data = builder.getPoints();
        for (int i = 0; i < data.getLength(); i++) {
            drawPoint(g, data.getPoint(i));
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
            g.fillOval(center.getX() * builder.getPaper().getGridSize() - dim / 2,
                    center.getY() * builder.getPaper().getGridSize() - dim / 2, dim, dim);
        }
    }

    /**
     * It draws crosses in all points of the track - just in modification stage of the game.
     *
     * @param g2 graphics
     */
    private void drawTrackPoints(Graphics2D g2) {
        if (builder.getStage() == TrackBuilder.EDIT_PRESS || builder.getStage() == TrackBuilder.EDIT_RELEASE) {
            for (int i = 1; i < builder.getLeft().getLength() - 1; i++) {
                drawCross(g2, builder.getLeft().getPoint(i));
            }
            for (int i = 1; i < builder.getRight().getLength() - 1; i++) {
                drawCross(g2, builder.getRight().getPoint(i));
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("repaint")) {
            repaint();
        } else if (evt.getPropertyName().equals("grid")) {
            repaint();
        }
    }

}
