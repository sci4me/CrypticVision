import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class LightsPanel extends JComponent {
    private final Animator animator;
    private final Light[] lights;

    public LightsPanel(final Animator animator) {
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.animator = animator;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                LightsPanel.this.mouseClicked(e);
            }
        });

        this.lights = new Light[Frame.LIGHTS];

        final int cx = 800 / 2;
        final int cy = 400 / 2;

        this.addLight(cx - 140 - 20, cy - 20, 2);
        this.addLight(cx - 140 - 20, cy + 20, 0);
        this.addLight(cx - 140 + 20, cy - 20, 6);
        this.addLight(cx - 140 + 20, cy + 20, 4);

        this.addLight(cx + 140 - 20, cy - 20, 7);
        this.addLight(cx + 140 - 20, cy + 20, 5);
        this.addLight(cx + 140 + 20, cy - 20, 3);
        this.addLight(cx + 140 + 20, cy + 20, 1);

        int light = 8;

        {
            final double r = 75;
            final double o = Math.toRadians(12);
            for(int i = 0; i < Frame.INNER_LIGHTS; i++) {
                final double s = Math.sin(i * Math.PI * 2 / Frame.INNER_LIGHTS - o);
                final double c = Math.cos(i * Math.PI * 2 / Frame.INNER_LIGHTS - o);

                this.addLight(cx + (int) (r * c) - 140, cy + (int) (r * s), light++);
                this.addLight(cx + (int) (r * c) + 140, cy + (int) (r * s), light++);
            }
        }

        {
            final double r = 120;
            final double o = Math.toRadians(8);
            for(int i = 0; i < Frame.OUTER_LIGHTS; i++) {
                final double s = Math.sin(i * Math.PI * 2 / Frame.OUTER_LIGHTS - o);
                final double c = Math.cos(i * Math.PI * 2 / Frame.OUTER_LIGHTS - o);

                this.addLight(cx + (int) (r * c) - 140, cy + (int) (r * s), light++);
                this.addLight(cx + (int) (r * c) + 140, cy + (int) (r * s), light++);
            }
        }
    }

    private void mouseClicked(final MouseEvent e) {
        final int x = e.getX();
        final int y = e.getY();

        for(final Light l : this.lights) {
            if(l.contains(x, y)) {
                switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        this.animator.getCurrentFrame().set(l.index, this.animator.getSelectedColor());
                        this.repaint();
                        break;
                    case MouseEvent.BUTTON3:
                        this.animator.getCurrentFrame().set(l.index, 0);
                        this.repaint();
                        break;
                }
                break;
            }
        }
    }

    private void addLight(final int x, final int y, final int index) {
        final Light l = new Light(this.animator, x, y, index);
        this.lights[index] = l;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 400);

        if(!this.animator.hasActiveAnimation()) return;

        g.setColor(Color.WHITE);
        g.drawString(String.format("Frame: %d / %d", this.animator.getCurrentFrameIndex() + 1, this.animator.frameCount()), 10, 15);

        for(final Light l : this.lights) l.draw(g);
    }
}