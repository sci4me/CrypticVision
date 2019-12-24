package com.sci.cv.client.effect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Effects {
    private static final Map<String, Effect> effects = new HashMap<>();

    static {
        try {
            Effects.effects.put("Genesis", new GenesisEffect());
            loadAnimPak("red_burst", "Red Burst", 150);
            loadAnimPak("red_spin", "Red Spin", 100);
            loadAnimPak("galaxy", "Galaxy", 100);
            loadAnimPak("sun_vision", "Sun Vision", 100);
            loadAnimPak("hypnotic", "Hypnotic", 100);
            loadAnimPak("jump", "Jump", 100);
            loadAnimPak("filler", "Filler", 100);
            loadAnimPak("epilepsy", "Epilepsy", 80);
            loadAnimPak("flick", "Flick", 100);
            loadAnimPak("flash", "Flash", 100);
            loadAnimPak("gusher", "Gusher", 100);
            loadAnimPak("wave", "Wave", 100);
            loadAnimPak("swirl", "Swirl", 80);
        } catch(final Throwable t) {
            t.printStackTrace();
        }
    }

    private static void loadAnimPak(final String name, final String displayName, final int msPerFrame) throws IOException {
        Effects.effects.put(displayName, new AnimPakEffect(name, displayName, msPerFrame));
    }

    public static Effect byName(final String name) {
        return Effects.effects.get(name);
    }

    public static Iterable<Effect> all() {
        return Effects.effects.values();
    }

    private Effects() {
    }
}