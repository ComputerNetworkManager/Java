package me.cnm.server.database.modle;

import java.util.Date;

public record PersistentDataChanges(int computer, String key, String before, String after, Date date) { }
