import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class Animation {
    public static Animation load(final File file) throws IOException {
        final DataInputStream din = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));

        final Animation result = new Animation(false);

        final int frameCount = din.readInt();
        for(int i = 0; i < frameCount; i++) {
            final Frame frame = new Frame();

            final int activeCount = din.readInt();
            for(int j = 0; j < activeCount; j++) {
                final int index = din.readInt();
                final int value = din.readInt();
                frame.set(index, value);
            }

            result.frames.add(frame);
        }

        din.close();

        return result;
    }

    private List<Frame> frames;
    private int currentFrame;

    public Animation() {
        this(true);
    }

    public Animation(final boolean addFirstFrame) {
        this.frames = new ArrayList<>();
        if(addFirstFrame) this.frames.add(new Frame());
        this.currentFrame = 0;
    }

    public void addFrame() {
        this.frames.add(this.frames.get(this.currentFrame).copy());
        this.currentFrame++;
    }

    public void removeFrame() {
        this.frames.remove(this.currentFrame);
        if(this.currentFrame >= this.frames.size()) this.currentFrame = this.frames.size() - 1;
    }

    public void prevFrame() {
        this.currentFrame--;
    }

    public void nextFrame() {
        this.currentFrame++;
    }

    public int frameCount() {
        return this.frames.size();
    }

    public int currentFrameIndex() {
        return this.currentFrame;
    }

    public Frame currentFrame() {
        return this.frames.get(this.currentFrame);
    }

    public void export(final File file) throws IOException {
        final DataOutputStream dout = new DataOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file))));

        dout.writeInt(this.frames.size());
        for(final Frame frame : this.frames) {
            final List<Pair<Integer, Integer>> activeLights = new ArrayList<>();
            for(int j = 0; j < Frame.LIGHTS; j++) {
                final int l = frame.get(j);
                activeLights.add(new Pair<>(j, l));
            }

            dout.writeInt(activeLights.size());
            for(final Pair<Integer, Integer> light : activeLights) {
                dout.writeInt(light.left);
                dout.writeInt(light.right);
            }
        }

        dout.flush();
        dout.close();
    }
}