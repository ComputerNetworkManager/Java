package me.cnm.impl.shared.cli.log;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.jline.reader.LineReader;

import java.io.Serializable;

@Plugin(name = "PrintAbove", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class PrintAboveAppender extends AbstractAppender {

    private static LineReader lineReader;

    @PluginFactory
    public static PrintAboveAppender createAppender(@PluginAttribute("name") String name,
                                                    @PluginElement("Filter") final Filter filter,
                                                    @PluginElement("Layout") final Layout<?> layout,
                                                    @PluginAttribute("IgnoreExceptions") final boolean ignoreExceptions,
                                                    @PluginElement("Properties") final Property[] properties) {
        return new PrintAboveAppender(name, filter, layout, ignoreExceptions, properties);
    }

    public static void setLineReader(LineReader lineReader) {
        PrintAboveAppender.lineReader = lineReader;
    }

    protected PrintAboveAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent event) {
        if (lineReader != null) lineReader.printAbove(new String(getLayout().toByteArray(event), Charset.defaultCharset()));
    }

}
