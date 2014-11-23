
package org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Makes glyphs a solid color.
 * 
 * @author Nathan Sweet <misc@n4te.com>
 */
public class ColorEffect implements ConfigurableEffect {
    /** The colour that will be applied across the text */
    @Nullable
    private Color color = Color.white;

    /**
     * Default constructor for injection
     */
    public ColorEffect() {
    }

    /**
     * Create a new effect to colour the text
     *
     * @param color The colour to apply across the text
     */
    public ColorEffect(@Nullable Color color) {
        this.color = color;
    }

    /**
     * @see org.newdawn.slick.font.effects.Effect#draw(java.awt.image.BufferedImage, java.awt.Graphics2D, org.newdawn.slick.UnicodeFont, org.newdawn.slick.font.Glyph)
     */
    public void draw(BufferedImage image, @Nonnull Graphics2D g, UnicodeFont unicodeFont, @Nonnull Glyph glyph) {
        g.setColor(color);
        g.fill(glyph.getShape());
    }

    /**
     * Get the colour being applied by this effect
     *
     * @return The colour being applied by this effect
     */
    @Nullable
    public Color getColor() {
        return color;
    }

    /**
     * Set the colour being applied by this effect
     *
     * @param color The colour being applied by this effect
     */
    void setColor(@Nullable Color color) {
        if (color == null) throw new IllegalArgumentException("color cannot be null.");
        this.color = color;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Nonnull
    public String toString () {
        return "Color";
    }

    /**
     * @see org.newdawn.slick.font.effects.ConfigurableEffect#getValues()
     */
    @Nonnull
    public List<Value> getValues() {
        final List<Value> values = new ArrayList<>();
        values.add(EffectUtil.colorValue("Color", color));
        return values;
    }

    /**
     * @see org.newdawn.slick.font.effects.ConfigurableEffect#setValues(java.util.List)
     */
    public void setValues(@Nonnull List<Value> values) {
        values.stream().filter(value -> value.getName().equals("Color")).forEach(value -> setColor((Color) value.getObject()));
    }
}
