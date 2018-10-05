package com.ambroz.formula.gui.swing.utils;

import java.awt.event.MouseEvent;

import javax.swing.JViewport;

import com.ambroz.formula.gamemodel.datamodel.Point;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public final class Conversions {

    /**
     * It converts panel coordinates to coordinates in grid.
     * <br>E.g. when grid size is 10, Point[145;53] is converted into Point[15;5]
     *
     * @param gridSize is the size of the grid
     * @param evt is MouseEvent of mouse adapter
     * @return Point with grid coordinates
     */
    public static Point clickToGamePoint(int gridSize, MouseEvent evt) {
        java.awt.Point dif = ((JViewport) evt.getSource()).getViewPosition();
        double roundX = Math.round((evt.getPoint().getX() + dif.getX()) / gridSize) * gridSize;
        double roundY = Math.round((evt.getPoint().getY() + dif.getY()) / gridSize) * gridSize;
        double squareX = (roundX / gridSize);
        double squareY = (roundY / gridSize);

        return new Point(squareX, squareY);
    }

}
