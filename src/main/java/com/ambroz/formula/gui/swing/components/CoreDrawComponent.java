package com.ambroz.formula.gui.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.ambroz.formula.gamemodel.datamodel.CoreModel;
import com.ambroz.formula.gamemodel.datamodel.Point;
import com.ambroz.formula.gamemodel.datamodel.Polyline;
import com.ambroz.formula.gamemodel.datamodel.Segment;
import com.ambroz.formula.gamemodel.enums.Side;
import com.ambroz.formula.gamemodel.track.Track;
import com.ambroz.formula.gui.swing.utils.Colors;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class CoreDrawComponent extends JPanel {

    protected final CoreModel model;

    public CoreDrawComponent(CoreModel model) {
        this.model = model;
    }

    protected void drawTrackPolygon(Graphics2D g2) {
        Track track = model.getTrack();
        if (track.isReadyForDraw()) {
            //draw complete track
            g2.setColor(Colors.ROAD_SNOW);
            int[][] trackPoints = track.getCoordinates(model.getPaper().getGridSize());
            g2.fillPolygon(trackPoints[0], trackPoints[1], track.getLeft().getLength() + track.getRight().getLength());
        }
    }

    protected void drawGrid(Graphics2D g2) {
        g2.setColor(new Color(150, 150, 150));

        for (Segment horizontalLine : model.getPaper().getHorizontalLines()) {
            drawSegment(g2, horizontalLine);
        }
        for (Segment verticalLine : model.getPaper().getVerticalLines()) {
            drawSegment(g2, verticalLine);
        }
    }

    /**
     * It draws points which are circles with radius 6.
     *
     * @param g graphics
     * @param center Polylinie of Points
     */
    protected void drawPoint(Graphics g, Point center) {
        int dim = 6;
        if (center != null) {
            g.fillOval(center.getX() * model.getPaper().getGridSize() - dim / 2,
                    center.getY() * model.getPaper().getGridSize() - dim / 2, dim, dim);
        }
    }

    /**
     * It draws crosses which are 1/3 grid size big.
     *
     * @param g
     * @param center Polyline of Points where crosses will be draw
     */
    protected void drawCross(Graphics g, Point center) {
        int gridSize = model.getPaper().getGridSize();
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
    protected void drawTrack(Graphics2D g2) {
        drawTrackLine(Side.Left, g2);
        drawTrackLine(Side.Right, g2);

        drawTrackEnds(g2);
    }

    private void drawTrackLine(Side side, Graphics2D g2) {
        Track track = model.getTrack();
        Polyline line = track.getLine(side);

        if (line.getLength() > 1) {
            g2.setStroke(new BasicStroke(track.getWidth(side)));
            g2.setColor(Color.red);
            drawPolyline(g2, line.choose(track.getIndex(side), line.getLength() - 1));
            g2.setColor(Color.black);
            drawPolyline(g2, line.choose(0, track.getIndex(side)));
        }
    }

    private void drawTrackEnds(Graphics2D g2) {
        Track track = model.getTrack();
        if (track.getStart() != null) {

            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.PINK);
            drawSegment(g2, track.getStart());

            if (track.isReady()) {
                drawSegment(g2, track.getFinish());
            }

        }
    }

    protected void drawSegment(Graphics g, Segment line) {
        int gridSize = model.getPaper().getGridSize();
        g.drawLine(line.getFirst().getX() * gridSize, line.getFirst().getY() * gridSize,
                line.getLast().getX() * gridSize, line.getLast().getY() * gridSize);
    }

    protected void drawPolyline(Graphics g, Polyline line) {
        int gridSize = model.getPaper().getGridSize();
        for (int i = 0; i < line.getLength() - 1; i++) {
            g.drawLine(line.getPoint(i).getX() * gridSize, line.getPoint(i).getY() * gridSize,
                    line.getPoint(i + 1).getX() * gridSize, line.getPoint(i + 1).getY() * gridSize);
        }
    }

    protected final void updateSize() {
        int gridSize = model.getPaper().getGridSize();
        this.setPreferredSize(new Dimension(model.getPaper().getWidth() * gridSize, model.getPaper().getHeight() * gridSize));
        revalidate();
        repaint();
    }
}
