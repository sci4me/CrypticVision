import java.io.File;
import java.io.IOException;

public final class ReCoder {
    public static void main(final String[] args) {
        final File dir = new File(args[0]);
        if(!dir.isDirectory()) throw new IllegalArgumentException(args[0] + " is not a directory!");

        final File[] files = dir.listFiles();
        if(files == null) return;

        for(final File file : files) {
            if(!file.getName().endsWith(".animpak")) {
                continue;
            }

            try {
                final Animation anim = Animation.load(file);
                anim.export(file);
                System.out.println("Transcoded " + file.getName());
            } catch(final IOException e) {
                e.printStackTrace();
            }
        }
    }
}