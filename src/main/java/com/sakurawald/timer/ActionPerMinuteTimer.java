package com.sakurawald.timer;

import com.sakurawald.bean.ColorCondition;
import com.sakurawald.config.ConfigManager;
import com.sakurawald.hook.GlyphVectorDrawer;
import lombok.Getter;

import java.awt.*;

public class ActionPerMinuteTimer implements Runnable {

    @Getter
    public static final ActionPerMinuteTimer instance = new ActionPerMinuteTimer();
    private final GlyphVectorDrawer glyphVectorDrawer = new GlyphVectorDrawer();
    private double accumulate_seconds = 0;
    private double accumulate_actions = 0;

    private ActionPerMinuteTimer() {
        // do nothing.
    }

    @Override
    public void run() {
        /* Update APM */
        double APM = 0;
        if (accumulate_seconds >= 60) {
            accumulate_actions = 0;
            accumulate_seconds = 0;
        } else {
            accumulate_seconds++;
            // WARNING: divide by zero exception will be eaten.
            APM = (accumulate_actions / accumulate_seconds) * 60.0;
        }

        /* Update GlyphVectorDrawer */
        var config = ConfigManager.getSingleton().applicationConfigFile.getConfigDataInstance().Drawer;
        glyphVectorDrawer.message = String.format("APM: %.0f", APM);
        glyphVectorDrawer.xOffset = config.xOffset;
        glyphVectorDrawer.yOffset = config.yOffset;
        glyphVectorDrawer.font = new Font(config.name, config.style, config.size);
        glyphVectorDrawer.color = ColorCondition.getHierarchicalColor((int) APM);
        glyphVectorDrawer.update();
    }

    public void increaseAccumulateActions() {
        accumulate_actions++;
    }
}
