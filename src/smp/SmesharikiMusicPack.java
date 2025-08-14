package smp;

import arc.*;
import arc.audio.Music;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.core.GameState;
import mindustry.mod.Mod;

import static smp.content.SMPMusic.*;
import static mindustry.Vars.ui;
import static mindustry.game.EventType.*;

public class SmesharikiMusicPack extends Mod {
    /** List of <i>vanilla</i> ambient music. */
    public Seq<Music> vAmbient;
    /** List of <i>vanilla</i> dark music. */
    public Seq<Music> vDark;
    /** List of <i>vanilla</i> boss music. */
    public Seq<Music> vBoss;

    /** A reference to SoundControl instance for easier access. */
    public SoundControl control;

    public SmesharikiMusicPack() {
        Log.info("Loaded SmesharikiMusicPack constructor.");
    }

    @Override
    public void init() {
        Events.on(ClientLoadEvent.class, e -> {
            constructSettings();
        });

        // First and foremost, load the music.
        load();

        control = Vars.control.sound;

        Events.on(MusicRegisterEvent.class, e -> {
            // Save copies of vanilla music lists.
            vAmbient = control.ambientMusic.copy();
            vDark = control.darkMusic.copy();
            vBoss = control.bossMusic.copy();
        });

        Events.on(WorldLoadEvent.class, e -> {
            // Inject custom music for all planets.
            control.ambientMusic = modAmbient;
            control.darkMusic = modDark;
            control.bossMusic = modBoss;
        });

        Events.on(StateChangeEvent.class, e -> {
            if (e.from != GameState.State.menu && e.to == GameState.State.menu) {
                // Reset music upon going to main menu.
                control.ambientMusic = vAmbient;
                control.darkMusic = vDark;
                control.bossMusic = vBoss;
            }
        });
    }

    void constructSettings() {
        ui.settings.addCategory("@setting.smp-title", t -> {
            t.checkPref("smp-enabled", true);
        });
    }
} 