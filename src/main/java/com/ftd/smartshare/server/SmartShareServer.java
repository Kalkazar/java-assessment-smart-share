package com.ftd.smartshare.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SmartShareServer {
    private static final int    PORT    = 3000;
    
    public static void main(String[] args) {
    	try (ServerSocket serverSocket = new ServerSocket(PORT)) {
    		while (true) {
    			new Thread(new SmartShareClientHandler(serverSocket.accept())).start();
    		}
    	} catch (IOException io) {
    		io.getStackTrace();
    	}
    }
}