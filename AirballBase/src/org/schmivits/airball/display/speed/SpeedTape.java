package org.schmivits.airball.display.speed;

import org.schmivits.airball.airdata.Aircraft;
import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.FilledPolygon;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;

public class SpeedTape extends Container {

    public interface Model {
        float getSpeed();
        Aircraft getAircraft();
    }

    private final Widget mTape;

    /**
     * Create a SpeedTape.
     */
    public SpeedTape(final Model model, DisplayConfiguration config, float x, float y, float w, float h) {
        moveTo(x, y);
        sizeTo(w, h);

        mTape = new SpeedTapeCore(
                new SpeedTapeCore.Model() {
                    @Override
                    public float getSpeed() {
                        return model.getSpeed();
                    }
                    @Override
                    public float getVs0() {
                        return model.getAircraft().getVs0();
                    }
                    @Override
                    public float getVfe() {
                        return model.getAircraft().getVfe();
                    }
                    @Override
                    public float getVs1() {
                        return model.getAircraft().getVs1();
                    }
                    @Override
                    public float getVno() {
                        return model.getAircraft().getVno();
                    }
                    @Override
                    public float getVne() {
                        return model.getAircraft().getVne();
                    }
                },
                config,
                0,
                0,
                w - config.mPointerToScaleOffset,
                h);

        Widget pointerSymbol = new FilledPolygon(
                new float[][]{
                        {1.0f, 0.0f},
                        {0.0f, 0.5f},
                        {1.0f, 1.0f},
                },
                config.mPointerColor);
        pointerSymbol.moveTo(0f, 0f);
        pointerSymbol.sizeTo(config.mPointerShapeSize, config.mPointerShapeSize);

        Widget pointerLine = new Rectangle(config.mPointerColor);
        pointerLine.moveTo(0f, 0f);
        pointerLine.sizeTo(w, config.mThickLineThickness);

        pointerSymbol.moveTo(
                getWidth() - pointerSymbol.getWidth(),
                (getHeight() - pointerSymbol.getHeight()) / 2f);
        pointerLine.moveTo(
                getWidth() - pointerLine.getWidth(), (getHeight() - pointerLine.getHeight()) / 2f);

        mChildren.add(mTape);
        mChildren.add(pointerSymbol);
        mChildren.add(pointerLine);
    }
}
