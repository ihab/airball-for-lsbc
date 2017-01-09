package org.schmivits.airball.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class StrokedPolygon extends AbstractPolygon {

    private float mStrokeWidth;

    public StrokedPolygon(float[][] points, Color color, float strokeWidth) {
        super(points, color);
        setClip(false);
        mStrokeWidth = strokeWidth;
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.setStroke(new BasicStroke(mStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(mShape);
    }
}





