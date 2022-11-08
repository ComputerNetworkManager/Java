package me.cnm.server.database.modle;

public record WebGivenPermission(Type type, int to, int permission) {

    public enum Type {
        GROUP,
        USER
    }

}
