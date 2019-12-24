public final class Frame {
    public static final int CENTER_LIGHTS = 4;
    public static final int INNER_LIGHTS = 16;
    public static final int OUTER_LIGHTS = 24;
    public static final int LIGHTS = (2 * CENTER_LIGHTS) + (2 * INNER_LIGHTS) + (2 * OUTER_LIGHTS);

    private int[] lights;

    public Frame() {
        this.lights = new int[LIGHTS];
    }

    public int get(final int n) {
        return this.lights[n];
    }

    public void set(final int n, final int x) {
        this.lights[n] = x;
    }

    public Frame copy() {
        final Frame c = new Frame();
        System.arraycopy(this.lights, 0, c.lights, 0, LIGHTS);
        return c;
    }
}