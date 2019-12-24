import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public final class Animator {
    public static void main(final String[] args) {
        new Animator().start();
    }

    private final JFrame frame;
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem fileNew;
    private final JMenuItem fileOpen;
    private final JPanel controlsPanel;
    private final LightsPanel lightsPanel;
    private final JButton addFrame;
    private final JButton removeFrame;
    private final JButton prevFrame;
    private final JButton nextFrame;
    private final JButton setAll;
    private final JButton clearAll;
    private final JButton export;
    private final JColorChooser colorChooser;
    private final JFileChooser fileChooser;

    private Animation currentAnimation;

    private Animator() {
        this.frame = new JFrame("Lè Animatôr");
        this.frame.setLayout(null);
        this.frame.setSize(800, 830);

        this.menuBar = new JMenuBar();
        this.frame.setJMenuBar(this.menuBar);

        this.fileMenu = new JMenu("File");
        this.menuBar.add(this.fileMenu);

        this.fileNew = new JMenuItem("New");
        this.fileNew.addActionListener(e -> Animator.this.fileNew());
        this.fileMenu.add(this.fileNew);

        this.fileOpen = new JMenuItem("Open");
        this.fileOpen.addActionListener(e -> Animator.this.fileOpen());
        this.fileMenu.add(this.fileOpen);

        this.controlsPanel = new JPanel();
        this.controlsPanel.setBounds(0, 400, 800, 400);
        this.controlsPanel.setLayout(null);
        this.frame.add(this.controlsPanel);

        this.lightsPanel = new LightsPanel(this);
        this.lightsPanel.setBounds(0, 0, 800, 400);
        this.frame.add(this.lightsPanel);

        this.addFrame = new JButton("Add Frame");
        this.addFrame.setBounds(20, 20, 160, 24);
        this.addFrame.addActionListener(e -> Animator.this.addFrame());
        this.controlsPanel.add(this.addFrame);

        this.removeFrame = new JButton("Remove Frame");
        this.removeFrame.setBounds(20, 50, 160, 24);
        this.removeFrame.addActionListener(e -> Animator.this.removeFrame());
        this.controlsPanel.add(this.removeFrame);

        this.prevFrame = new JButton("<");
        this.prevFrame.setBounds(200, 20, 50, 50);
        this.prevFrame.addActionListener(e -> Animator.this.prevFrame());
        this.controlsPanel.add(this.prevFrame);

        this.nextFrame = new JButton(">");
        this.nextFrame.setBounds(270, 20, 50, 50);
        this.nextFrame.addActionListener(e -> Animator.this.nextFrame());
        this.controlsPanel.add(this.nextFrame);

        this.setAll = new JButton("Set All");
        this.setAll.setBounds(340, 20, 100, 50);
        this.setAll.addActionListener(e -> Animator.this.setAll());
        this.controlsPanel.add(this.setAll);

        this.clearAll = new JButton("Clear All");
        this.clearAll.setBounds(460, 20, 100, 50);
        this.clearAll.addActionListener(e -> Animator.this.clearAll());
        this.controlsPanel.add(this.clearAll);

        this.export = new JButton("Export");
        this.export.setBounds(580, 20, 160, 50);
        this.export.addActionListener(e -> Animator.this.export());
        this.controlsPanel.add(this.export);

        this.colorChooser = new JColorChooser();
        this.colorChooser.setBounds(20, 90, 760, 280);
        this.controlsPanel.add(this.colorChooser);

        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                Animator.this.stop();
            }
        });
        this.frame.setLocationRelativeTo(null);

        this.fileChooser = new JFileChooser();

        this.updateButtons();
    }

    private void start() {
        this.frame.setVisible(true);
    }

    private void stop() {
        System.exit(0);
    }

    private void fileNew() {
        this.currentAnimation = new Animation(); // TODO: if current exists and is dirty, ask if they want to save it, etc.
        this.updateAll();
    }

    private void fileOpen() {
        if(this.fileChooser.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
            try {
                this.currentAnimation = Animation.load(this.fileChooser.getSelectedFile());
                this.updateAll();
            } catch(final IOException e) {
                JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Loading Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void addFrame() {
        this.currentAnimation.addFrame();
        this.updateAll();
    }

    public void removeFrame() {
        this.currentAnimation.removeFrame();
        this.updateAll();
    }

    public void prevFrame() {
        this.currentAnimation.prevFrame();
        this.updateAll();
    }

    public void nextFrame() {
        this.currentAnimation.nextFrame();
        this.updateAll();
    }

    private void setAll() {
        for(int i = 0; i < Frame.LIGHTS; i++) this.currentAnimation.currentFrame().set(i, this.getSelectedColor());
        this.updateAll();
    }

    private void clearAll() {
        for(int i = 0; i < Frame.LIGHTS; i++) this.currentAnimation.currentFrame().set(i, 0);
        this.updateAll();
    }

    private void export() {
        if(this.fileChooser.showSaveDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = this.fileChooser.getSelectedFile();
            try {
                // TODO: if the file already exists, ask permission to overwrite!
                this.currentAnimation.export(selectedFile);
            } catch(final IOException e) {
                JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Export Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateAll() {
        this.updateButtons();
        this.lightsPanel.repaint();
    }

    private void updateButtons() {
        this.addFrame.setEnabled(this.hasActiveAnimation());
        this.removeFrame.setEnabled(this.hasActiveAnimation() && this.currentAnimation.frameCount() > 1);
        this.setAll.setEnabled(this.hasActiveAnimation());
        this.clearAll.setEnabled(this.hasActiveAnimation());
        this.prevFrame.setEnabled(this.hasActiveAnimation() && this.currentAnimation.frameCount() > 1 && this.currentAnimation.currentFrameIndex() > 0);
        this.nextFrame.setEnabled(this.hasActiveAnimation() && this.currentAnimation.frameCount() > 1 && this.currentAnimation.currentFrameIndex() < this.currentAnimation.frameCount() - 1);
        this.export.setEnabled(this.hasActiveAnimation());
    }

    public Frame getCurrentFrame() {
        return this.currentAnimation.currentFrame();
    }

    public int getCurrentFrameIndex() {
        return this.currentAnimation.currentFrameIndex();
    }

    public int frameCount() {
        return this.currentAnimation.frameCount();
    }

    public int getSelectedColor() {
        return this.colorChooser.getColor().getRGB();
    }

    public boolean hasActiveAnimation() {
        return this.currentAnimation != null;
    }
}