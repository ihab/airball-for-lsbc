package org.schmivits.airball.display.climbrate;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Widget;

public class ClimbRateTape extends Container {

    public interface Model {
        float getClimbRate();
    }

    private final Widget mClimbRateTapeCore;

    public ClimbRateTape(final Model model, DisplayConfiguration config,
                         float x, float y, float w, float h) {
        moveTo(x, y);
        sizeTo(w, h);
        mClimbRateTapeCore = new ClimbRateTapeCore(
                new ClimbRateTapeCore.Model() {
                    @Override
                    public float getClimbRate() {
                        return model.getClimbRate();
                    }
                },
                config,
                0,
                0,
                getWidth(),
                getHeight());
        mChildren.add(mClimbRateTapeCore);
    }
}
