package net.moonlightflower.reforgedmappreviewreplacer;

import net.moonlightflower.wc3libs.port.JMpqPort;

import java.io.*;
import java.nio.file.Files;

public class ReforgedMapPreviewReplacer {
    private final static File CLASSIC_PREVIEW_IN_FILE = new File("war3mapPreview.blp");
    private final static File REFORGED_EFFECTIVE_PREVIEW_IN_FILE = new File("war3mapMap.blp");
    private final static File CUSTOM_INGAME_IN_FILE = new File("war3mapMap_ingame.blp");

    public static void main(String[] args) throws Exception {
        final String inFilePath = args[0];
        final String outFilePath = args[1];

        if (inFilePath == null) {
            throw new IllegalArgumentException("no input file");
        }
        if (outFilePath == null) {
            throw new IllegalArgumentException("no output file");
        }

        final File inFile = new File(inFilePath);
        final File outFile = new File(outFilePath);

        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            Files.copy(inFile.toPath(), fos);
        }

        final JMpqPort jMpqPort = new JMpqPort();

        Files.deleteIfExists(REFORGED_EFFECTIVE_PREVIEW_IN_FILE.toPath());
        Files.deleteIfExists(CLASSIC_PREVIEW_IN_FILE.toPath());

        final var out = jMpqPort.createOut();

        final File ingameMinimapOutFile = CUSTOM_INGAME_IN_FILE;
        final File previewOutFile = CLASSIC_PREVIEW_IN_FILE;

        final var ingameMinimap = new FileOutputStream(ingameMinimapOutFile);
        final var preview = new FileOutputStream(previewOutFile);

        out.add(REFORGED_EFFECTIVE_PREVIEW_IN_FILE, ingameMinimap);
        out.add(CLASSIC_PREVIEW_IN_FILE, preview);

        out.commit(inFile);

        final var inDel = jMpqPort.createIn();

        inDel.addDel(CUSTOM_INGAME_IN_FILE);
        inDel.addDel(REFORGED_EFFECTIVE_PREVIEW_IN_FILE);
        //inDel.addDel(CLASSIC_PREVIEW_IN_FILE);

        inDel.commit(outFile);

        final var in = jMpqPort.createIn();

        in.add(ingameMinimapOutFile, CUSTOM_INGAME_IN_FILE);
        in.add(previewOutFile, REFORGED_EFFECTIVE_PREVIEW_IN_FILE);

        in.commit(outFile);
    }
}
