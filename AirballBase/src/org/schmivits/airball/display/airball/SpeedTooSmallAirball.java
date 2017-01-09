package org.schmivits.airball.display.airball;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Widget;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class SpeedTooSmallAirball extends Widget {

    private final DisplayConfiguration mConfig;

    public SpeedTooSmallAirball(DisplayConfiguration config) {
        mConfig = config;
        setClip(false);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mConfig.mLineColor);
        g.setStroke(new BasicStroke(mConfig.mThickLineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Ellipse2D.Float(
                0,
                0,
                getWidth(),
                getHeight()));
    }
}
