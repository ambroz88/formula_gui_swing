package com.ambroz.formula.gui.swing.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.border.LineBorder;

import com.ambroz.formula.gamemodel.datamodel.Point;
import com.ambroz.formula.gamemodel.datamodel.PropertyChanger;
import com.ambroz.formula.gamemodel.race.Formula;
import com.ambroz.formula.gamemodel.race.RaceModel;
import com.ambroz.formula.gamemodel.race.Turn;
import com.ambroz.formula.gamemodel.utils.Calc;
import com.ambroz.formula.gui.swing.utils.Colors;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class RaceComponent extends CoreDrawComponent implements PropertyChangeListener {

    private final RaceModel gameModel;

    public RaceComponent(RaceModel gModel) {
        super(gModel);
        this.gameModel = gModel;
        gameModel.addPropertyChangeListener(this);
        gameModel.getPaper().addPropertyChangeListener(this);
        setBorder(new LineBorder(Color.black));
        updateSize();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Colors.GAME_GRASS);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        drawTrackPolygon(g2);

        drawGrid(g2);

        drawTrack(g2);

        //draw formulas:
        if (gameModel.getTurnMaker().getFormulaCount() > 0) {
            drawFormulaTurn(g2);
        }

    }

    private void drawFormulaTurn(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        drawFormula(g2, gameModel.getTurnMaker().getActiveFormula());

        //draw points:
        Color colorPoints = new Color(gameModel.getTurnMaker().getActiveFormula().getColor());
        g2.setColor(colorPoints);
        if (gameModel.getStage() >= RaceModel.FIRST_TURN) {
            drawTurns(g2);
        }
    }

    private void drawFormula(Graphics2D g, Formula form) {
        if (!form.isEmpty()) {
            int gridSize = gameModel.getPaper().getGridSize();
            g.setColor(new Color(form.getColor()));
            double arrowAngle = 0.5;//0.5235; //in radians ~ 30°
            double arrowLength = 0.6 * gridSize;

            int formulaLength = form.getLength();
            if (formulaLength > gameModel.getTurnMaker().getLengthHist()) {
                formulaLength = gameModel.getTurnMaker().getLengthHist() + 1;
            }

            for (int i = form.getLength() - formulaLength; i < form.getLength() - 1; i++) {
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

    private void drawTurns(Graphics g) {
        List<Turn> possibleTurns = gameModel.getTurnMaker().getTurns().getFreeTurns();
        for (Point point : possibleTurns) {
            drawPoint(g, point);
        }

        List<Turn> crashes = gameModel.getTurnMaker().getTurns().getCollisionTurns();
        for (Point point : crashes) {
            drawCross(g, point);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChanger.REPAINT)) {
            repaint();
        } else if (evt.getPropertyName().equals(PropertyChanger.PAPER_GRID)) {
            updateSize();
        } else if (evt.getPropertyName().equals(PropertyChanger.PAPER_WIDTH)) {
            updateSize();
        } else if (evt.getPropertyName().equals(PropertyChanger.PAPER_HEIGHT)) {
            updateSize();
        }
    }

}
