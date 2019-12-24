import java.awt.*;

public final class Light  {
    public static final int R = 24;
    public static final int R2 = R / 2;

    public final Animator animator;
    public final int x;
    public final int y;
    public final int index;

    public Light(final Animator animator, final int x, final int y, final int index) {
        this.animator = animator;
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public void draw(final Graphics g) {
        g.setColor(new Color(this.animator.getCurrentFrame().get(this.index)));
        g.fillOval(this.x - R2, this.y - R2, R, R);

        g.setColor(Color.WHITE);
        g.drawOval(this.x - R2, this.y - R2, R, R);
    }

    public boolean contains(final int x, final int y) {
        final int dx = this.x - x;
        final int dy = this.y - y;
        return Math.sqrt((dx * dx) + (dy * dy)) < R2;
    }
}