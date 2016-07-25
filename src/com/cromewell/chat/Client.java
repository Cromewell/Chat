package com.cromewell.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Jo on 20.07.2016.
 * Creates a Socket.
 */
public class Client {

    private String host;
    private int port;
    private Socket client;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        client = new Socket(host, port);
    }

    public OutputStream getOutputStream() throws IOException {
        return client.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return client.getInputStream();
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getIp(){
        return client.getInetAddress().toString();
    }
}
