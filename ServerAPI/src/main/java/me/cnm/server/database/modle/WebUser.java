package me.cnm.server.database.modle;

public record WebUser(int id, String email, String passwordHash, String rememberKey) { }
