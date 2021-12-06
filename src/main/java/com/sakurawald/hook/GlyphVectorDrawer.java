package com.sakurawald.hook;

import java.awt.*;

public class GlyphVectorDrawer {
    /* Properties */
    public String message;
    public Font font;
    public Color color;
    public float xOffset;
    public float yOffset;
    public final Window window = new Window(null) {

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = ((Graphics2D) g);
            FontMetrics metrics = g.getFontMetrics(font);
            Shape shape = font.createGlyphVector(g2d.getFontRenderContext(), message)
                    .getOutline(
                            (xOffset),
                            (yOffset +
                                    metrics.getHeight() / 2));
            g.setColor(color);
            setShape(shape);
            g2d.fill(shape.getBounds());
        }

        @Override
        public void update(Graphics g) {
            paint(g);
        }
    };

    public void update() {
        window.setAlwaysOnTop(true);
        window.setBounds(window.getGraphicsConfiguration().getBounds());
        window.setVisible(true);
        window.update(window.getGraphics());
    }

}
