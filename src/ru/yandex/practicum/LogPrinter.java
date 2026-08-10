package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LogPrinter implements AutoCloseable {
    private PrintWriter pw;

    public LogPrinter(String fileName) throws IOException {
        pw = new PrintWriter(
                Files.newBufferedWriter(
                        Paths.get(fileName),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                ), true);
    }

    public void println(String s) {
        pw.println(s);
    }

    @Override
    public void close() {
        pw.close();
    }
}
