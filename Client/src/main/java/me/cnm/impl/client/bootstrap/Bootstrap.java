package me.cnm.impl.client.bootstrap;

import me.cnm.impl.client.Client;

public class Bootstrap {

    public static void main(String[] args) {
        Client client = new Client();

        Runtime.getRuntime().addShutdownHook(new Thread(client::stop));

        client.start();
    }

}
