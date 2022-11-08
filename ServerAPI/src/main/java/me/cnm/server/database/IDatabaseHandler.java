package me.cnm.server.database;

public interface IDatabaseHandler {

    IDatabaseProvider getProvider();

    void setProvider(IDatabaseProvider provider);

    boolean isConnected();

}
