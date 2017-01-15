package org.schmivits.airball.display.altitude;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.FilledPolygon;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.Widget;

public class AltitudeTape extends Container {

    public interface Model {
        float getAltitude();
    }

    private final AltitudeTapeCore mTape;

    /**
     * Create an AltitudeTape.
     */
    public AltitudeTape(
            final Model model,
            DisplayConfiguration config,
            float x, float y, float w, float h) {
        moveTo(x, y);
        sizeTo(w, h);

        mTape = new AltitudeTapeCore(
                new AltitudeTapeCore.Model() {
                    @Override
                    public float getAltitude() {
                        return model.getAltitude();
                    }
                },
                config,
                config.mPointerToScaleOffset,
                0f,
                w - config.mPointerToScaleOffset,
                h);

        Widget pointerSymbol = new FilledPolygon(
                new float[][]{
                        {0.0f, 0.0f},
                        {1.0f, 0.5f},
                        {0.0f, 1.0f},
                },
                config.mPointerColor);
        pointerSymbol.sizeTo(config.mPointerShapeSize, config.mPointerShapeSize);

        Widget pointerLine = new Rectangle(config.mPointerColor);
        pointerLine.sizeTo(w, config.mThickLineThickness);

        pointerSymbol.moveTo(
                0, (getHeight() - pointerSymbol.getHeight()) / 2f);
        pointerLine.moveTo(
                0, (getHeight() - pointerLine.getHeight()) / 2f);

        mChildren.add(mTape);
        mChildren.add(pointerSymbol);
        mChildren.add(pointerLine);
    }
}
