package com.sakurawald.bean;

import com.sakurawald.config.ConfigManager;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class ColorCondition {
    int greaterThan;
    int R;
    int G;
    int B;

    public static Color getHierarchicalColor(int APM) {
        for (ColorCondition colorCondition : ConfigManager.getSingleton().applicationConfigFile.getConfigDataInstance().Drawer.colorConditions) {
            if (APM >= colorCondition.greaterThan) return new Color(colorCondition.R, colorCondition.G, colorCondition.B);
        }
        return new Color(0, 0, 0);
    }
}
