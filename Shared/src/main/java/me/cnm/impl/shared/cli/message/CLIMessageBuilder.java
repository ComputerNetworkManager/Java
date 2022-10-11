package me.cnm.impl.shared.cli.message;

import me.cnm.shared.cli.message.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLIMessageBuilder implements ICLIMessageBuilder {

    private final List<Integer> attributes = new ArrayList<>();
    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public ICLIMessageBuilder cursor(int amount, @NotNull CursorDirection direction) {
        this.appendEscapeSequence(direction.getCode(), amount);
        return this;
    }

    @Override
    public ICLIMessageBuilder cursorNextLine() {
        this.appendEscapeSequence('E');
        return this;
    }

    @Override
    public ICLIMessageBuilder cursorPreviousLine() {
        this.appendEscapeSequence('F');
        return this;
    }

    @Override
    public ICLIMessageBuilder setCursorHorizontally(int position) {
        this.appendEscapeSequence('G', position);
        return this;
    }

    @Override
    public ICLIMessageBuilder moveCursor(int x, int y) {
        this.appendEscapeSequence('H', x, y);
        return this;
    }

    @Override
    public ICLIMessageBuilder storeCursorPosition() {
        this.appendEscapeSequence('s');
        return this;
    }

    @Override
    public ICLIMessageBuilder restoreCursorPosition() {
        this.appendEscapeSequence('u');
        return this;
    }

    @Override
    public ICLIMessageBuilder eraseScreen() {
        return this.eraseScreen(EraseType.TO_END);
    }

    @Override
    public ICLIMessageBuilder eraseScreen(@NotNull EraseType eraseType) {
        this.appendEscapeSequence('j', eraseType.getCode());
        return this;
    }

    @Override
    public ICLIMessageBuilder eraseLine() {
        return this.eraseLine(EraseType.TO_END);
    }

    @Override
    public ICLIMessageBuilder eraseLine(@NotNull EraseType eraseType) {
        this.appendEscapeSequence('k', eraseType.getCode());
        return this;
    }

    @Override
    public ICLIMessageBuilder scrollUp() {
        this.appendEscapeSequence('S');
        return this;
    }

    @Override
    public ICLIMessageBuilder scrollDown() {
        this.appendEscapeSequence('T');
        return this;
    }

    @Override
    public ICLIMessageBuilder resetFormats() {
        this.attributes.add(0);
        return this;
    }

    @Override
    public ICLIMessageBuilder addFormats(@NotNull Format... formats) {
        for (Format format : formats) this.attributes.add(format.getSet());
        return this;
    }

    @Override
    public ICLIMessageBuilder removeFormats(@NotNull Format... formats) {
        for (Format format : formats) this.attributes.add(format.getReset());
        return this;
    }

    @Override
    public ICLIMessageBuilder fg(@NotNull Color color) {
        this.attributes.add(color.getFg());
        return this;
    }

    @Override
    public ICLIMessageBuilder fg(int r, int g, int b) {
        this.attributes.addAll(Arrays.asList(38, 2, r, g, b));
        return this;
    }

    @Override
    public ICLIMessageBuilder bg(@NotNull Color color) {
        this.attributes.add(color.getBg());
        return this;
    }

    @Override
    public ICLIMessageBuilder bg(int r, int g, int b) {
        this.attributes.addAll(Arrays.asList(48, 2, r, g, b));
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text, @NotNull Format... formats) {
        this.removeFormats(Format.values());
        this.addFormats(formats);
        this.text(text);
        this.removeFormats(Format.values());
        return this;
    }

    @Override
    public ICLIMessageBuilder textFg(@NotNull Object text, @NotNull Color fg) {
        this.fg(fg);
        this.text(text);
        this.fg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textBg(@NotNull Object text, @NotNull Color bg) {
        this.bg(bg);
        this.text(text);
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text, @NotNull Color fg, @NotNull Color bg) {
        this.fg(fg);
        this.bg(bg);
        this.text(text);
        this.fg(Color.RESET);
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textFg(@NotNull Object text, int r, int g, int b) {
        this.fg(r, g, b);
        this.text(text);
        this.fg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textBg(@NotNull Object text, int r, int g, int b) {
        this.bg(r, g, b);
        this.text(text);
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text, int fgr, int fgg, int fgb, int bgr, int bgg, int bgb) {
        this.fg(fgr, fgg, fgb);
        this.bg(bgr, bgg, bgb);
        this.text(text);
        this.fg(Color.RESET);
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textFg(@NotNull Object text, @NotNull Color fg, @NotNull Format... formats) {
        this.removeFormats(Format.values());
        this.addFormats(formats);
        this.fg(fg);
        this.text(text);
        this.removeFormats(Format.values());
        this.fg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textBg(@NotNull Object text, @NotNull Color bg, @NotNull Format... formats) {
        this.removeFormats(Format.values());
        this.addFormats(formats);
        this.bg(bg);
        this.text(text);
        this.removeFormats(Format.values());
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text, @NotNull Color fg, @NotNull Color bg, @NotNull Format... formats) {
        this.resetFormats();
        this.addFormats(formats);
        this.fg(fg);
        this.bg(bg);
        this.text(text);
        this.resetFormats();
        return this;
    }

    @Override
    public ICLIMessageBuilder textFg(@NotNull Object text, int r, int g, int b, @NotNull Format... formats) {
        this.removeFormats(Format.values());
        this.addFormats(formats);
        this.fg(r, g, b);
        this.text(text);
        this.removeFormats(Format.values());
        this.fg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder textBg(@NotNull Object text, int r, int g, int b, @NotNull Format... formats) {
        this.removeFormats(Format.values());
        this.addFormats(formats);
        this.bg(r, g, b);
        this.text(text);
        this.removeFormats(Format.values());
        this.bg(Color.RESET);
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text, int fgr, int fgg, int fgb, int bgr, int bgg, int bgb, @NotNull Format... formats) {
        this.resetFormats();
        this.addFormats(formats);
        this.fg(fgr, fgb, fgg);
        this.bg(bgr, bgb, bgg);
        this.text(text);
        this.resetFormats();
        return this;
    }

    @Override
    public ICLIMessageBuilder text(@NotNull Object text) {
        this.flushAttributes();
        this.stringBuilder.append(text);
        return this;
    }

    @Override
    public @NotNull String build() {
        this.flushAttributes();
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return this.build();
    }

    private void appendEscapeSequence(char command, Integer... attributes) {
        this.flushAttributes();
        this.appendFlushedEscapeSequence(command, attributes);
    }

    private void appendEscapeSequence(char command) {
        this.flushAttributes();
        this.appendFlushedEscapeSequence(command);
    }

    private void flushAttributes() {
        this.appendFlushedEscapeSequence('m', this.attributes.toArray(Integer[]::new));
        this.attributes.clear();
    }

    private void appendFlushedEscapeSequence(char command, Integer... attributes) {
        this.stringBuilder.append("\u001B")
                .append("[");

        for (int i = 0; i < attributes.length; i++) {
            if (i != 0) this.stringBuilder.append(";");
            this.stringBuilder.append(attributes[i]);
        }

        this.stringBuilder.append(command);
    }

    private void appendFlushedEscapeSequence(char command) {
        this.stringBuilder.append("\u001B")
                .append("[")
                .append(command);
    }

}
