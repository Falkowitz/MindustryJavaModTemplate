package smp.content;

import arc.audio.Music;
import arc.files.Fi;
import arc.struct.Seq;
import mindustry.Vars;

public class SMPMusic {
    /** List of calm ambient music. */
    public static Seq<Music> modAmbient = new Seq<>();
    /** List of "dark" music, usually played during conflicts and dire situations. */
    public static Seq<Music> modDark = new Seq<>();
    /** List of boss music. */
    public static Seq<Music> modBoss = new Seq<>();

    public static void load() {
        // Music categories: ambient, dark, boss.
        Fi musicRoot = Vars.mods.locateMod("smeshariki-music-pack").root.child("music");
        for (var cat : musicRoot.list()) {
            for (var mFile : cat.findAll(f -> f.extEquals("ogg") || f.extEquals("mp3"))) {
                var music = loadMusic(cat.name() + "/" + mFile.nameWithoutExtension());
                switch (cat.name()) {
                    case "ambient" -> modAmbient.add(music);
                    case "dark" -> modDark.add(music);
                    case "boss" -> modBoss.add(music);
                }
            }
        }
    }

    private static Music loadMusic(String name) {
        return Vars.tree.loadMusic(name);
    }
} 