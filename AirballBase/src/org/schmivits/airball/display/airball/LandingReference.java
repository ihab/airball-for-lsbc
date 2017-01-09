package org.schmivits.airball.display.airball;

import org.schmivits.airball.display.DisplayConfiguration;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

public class LandingReference extends TotemPoleDecoration {

    private static final float ANGLE_OFFSET = 20f;

    public LandingReference(DisplayConfiguration config) {
        super(config);
        setClip(false);
    }

    @Override
    protected void computeSize() {
        sizeTo(mUnitSize * 2f, mUnitSize * 2f);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mConfig.mPointerColor);
        g.setStroke(new BasicStroke(mConfig.mThinLineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        float sweep = 180f - 2 * ANGLE_OFFSET;
        g.draw(new Arc2D.Float(
                0f,
                0f,
                getWidth(),
                getHeight(),
                ANGLE_OFFSET,
                sweep,
                Arc2D.OPEN));
        g.draw(new Arc2D.Float(
                0f,
                0f,
                getWidth(),
                getHeight(),
                ANGLE_OFFSET - 180f,
                sweep,
                Arc2D.OPEN));
    }
}
