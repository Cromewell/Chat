package com.cromewell.chat;

import com.cromewell.chat.view.ClientController;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Jo on 24.07.2016.
 *
 * Listen for server messages.
 */
public class InputHandler implements Runnable{

    private DataInputStream in;
    private ClientController c;

    public InputHandler(DataInputStream in, ClientController c) {
        this.in = in;
        this.c = c;
    }

    /**
     * Runs 'til the server is offline or the program is closed.
     * Display the message.
     */
    @Override
    public void run() {
        while(true){
            try {
                c.getChatHistory().appendText(in.readUTF()); //Display the message.
            } catch (IOException e) {
                //Server offline
                c.getChatHistory().appendText("#Server went offline...\n");
                break;
            }
        }
    }
}
