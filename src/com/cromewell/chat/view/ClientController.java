package com.cromewell.chat.view;

import com.cromewell.chat.InputHandler;
import com.cromewell.chat.Client;
import com.cromewell.chat.model.HostInformation;
import com.cromewell.chat.model.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import java.io.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ClientController {

    @FXML
    private TextArea chatHistory;
    @FXML
    private TextArea chatInput;
    @FXML
    private Button send;

    private Client client;

    //Reference to model
    private UserData user;

    public ClientController(){
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * Connect to the server and set user data.
     */
    @FXML
    private void initialize() {
        try {
            client = new Client(HostInformation.IP_ADRESS, HostInformation.PORT_NUM); //Host ip and port
            user = new UserData(LocalDateTime.now(), InetAddress.getLocalHost().toString(), client.getIp()); //sets user data
            chatHistory.appendText(user.getLastTimeConnected().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))+" Connected to "+client.getHost()+" at "+client.getPort()+"\n");//Prints
            //the date, when the client did connect
        } catch (IOException e) {
            chatHistory.appendText("#Failure: Couldn't connect to host "+client.getHost()+" at port "+client.getPort()+".");
        }
        chatInput.setOnKeyPressed(e-> handleEnter(e.getCode())); //Set up key listener
        try {
            Thread inputHandler = new Thread(new InputHandler(new DataInputStream(client.getInputStream()), this)); //Waits for server messages...
            inputHandler.setDaemon(true);
            inputHandler.start();
        } catch (IOException e) {
            chatHistory.appendText("#Couldn't create input handler...exit.");
            System.exit(1);
        }
    }

    /**
     * Format the message (remove \n if it was send with enter, to check if the message is > 0)
     * and if its length is > then 0 send it to the server.
     */

    public void handleSend(){
        String msg = chatInput.getText().replace("\n", "");
        if(msg.length() > 0) {
            try {
                DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
                writer.writeUTF(user.getName()+": "+msg); //Write message to server
                writer.flush();
                chatInput.setText("");
            } catch (IOException e) {
                chatHistory.appendText("#Failure: Couldn't send message.");
            }
        }
        chatInput.setText("");
    }

    /**
     * Checks if a given key is enter, if yes, send the message.
     *
     * @param code      Key to check if it's enter.
     *
     */
    private void handleEnter(KeyCode code){
        if(code == KeyCode.ENTER){
            handleSend();
        }
    }

    public TextArea getChatHistory() {
        return chatHistory;
    }
}
