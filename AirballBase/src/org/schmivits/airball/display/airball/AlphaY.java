package org.schmivits.airball.display.airball;

import org.schmivits.airball.display.DisplayConfiguration;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class AlphaY extends TotemPoleDecoration {

    private final Path2D.Float[] mPaths = {null, null};

    public AlphaY(DisplayConfiguration config) {
        super(config);
    }

    @Override
    protected void computeSize() {
        sizeTo(mUnitSize * 20f, mUnitSize);
        float center = getWidth() / 2f;

        mPaths[0] = new Path2D.Float();
        mPaths[0].moveTo(center - 8f * mUnitSize, mUnitSize);
        mPaths[0].lineTo(center - 9f * mUnitSize, mUnitSize);
        mPaths[0].lineTo(center - 10f * mUnitSize, 0f);

        mPaths[1] = new Path2D.Float();
        mPaths[1].moveTo(center + 8f * mUnitSize, mUnitSize);
        mPaths[1].lineTo(center + 9f * mUnitSize, mUnitSize);
        mPaths[1].lineTo(center + 10f * mUnitSize, 0f);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mConfig.mPointerColor);
        g.setStroke(new BasicStroke(mConfig.mThinLineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(mPaths[0]);
        g.draw(mPaths[1]);
    }
}
