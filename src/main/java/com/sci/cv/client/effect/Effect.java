package com.sci.cv.client.effect;

public abstract class Effect {
    public static final int CENTER_LIGHTS = 4;
    public static final int INNER_LIGHTS = 16;
    public static final int OUTER_LIGHTS = 24;
    public static final int LIGHTS = (2 * CENTER_LIGHTS) + (2 * INNER_LIGHTS) + (2 * OUTER_LIGHTS);

    public abstract String name();

    public abstract void update();

    public abstract int lightColor(final int lightIndex);
}