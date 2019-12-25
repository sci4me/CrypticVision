package com.sci.cv.client.effect;

public final class GenesisEffect extends Effect {
    private double theta;

    @Override
    public String name() {
        return "Genesis";
    }

    @Override
    public void update() {
        this.theta += 0.1;
    }

    @Override
    public int lightColor(final int lightIndex) {
        final double f = lightIndex / (double) LIGHTS;
        final int r = (int) (Math.sin(this.theta * f) * 127) + 128;
        final int g = (int) (Math.sin(this.theta * f + 2) * 127) + 128;
        final int b = (int) (Math.sin(this.theta * f + 4) * 127) + 128;
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}