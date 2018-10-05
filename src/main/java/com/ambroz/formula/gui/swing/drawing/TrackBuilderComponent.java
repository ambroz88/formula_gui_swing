package com.ambroz.formula.gui.swing.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.border.LineBorder;

import com.ambroz.formula.gamemodel.datamodel.Polyline;
import com.ambroz.formula.gamemodel.track.TrackBuilder;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class TrackBuilderComponent extends CoreDrawComponent implements PropertyChangeListener {

    private final TrackBuilder builder;

    public TrackBuilderComponent(TrackBuilder trackBuilder) {
        super(trackBuilder);
        this.builder = trackBuilder;
        this.builder.addPropertyChangeListener(this);
        this.builder.getPaper().addPropertyChangeListener(this);
        setBorder(new LineBorder(Color.black));
        updateSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        drawTrackPolygon(g2);

        drawGrid(g2);

        drawTrack(g2);

        g2.setColor(Color.BLUE);
        drawPoints(g2);
        drawTrackPoints(g2);
    }

    private void drawPoints(Graphics g) {
        Polyline data = builder.getPoints();
        for (int i = 0; i < data.getLength(); i++) {
            drawPoint(g, data.getPoint(i));
        }
    }

    /**
     * It draws crosses in all points of the track - just in modification stage of the game.
     *
     * @param g2 graphics
     */
    private void drawTrackPoints(Graphics2D g2) {
        if (builder.getStage() == TrackBuilder.EDIT_PRESS || builder.getStage() == TrackBuilder.EDIT_RELEASE) {
            drawLinePoints(g2, builder.getTrack().getLeft());
            drawLinePoints(g2, builder.getTrack().getRight());
        }
    }

    private void drawLinePoints(Graphics2D g2, Polyline line) {
        for (int i = 1; i < line.getLength() - 1; i++) {
            drawCross(g2, line.getPoint(i));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("repaint")) {
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
