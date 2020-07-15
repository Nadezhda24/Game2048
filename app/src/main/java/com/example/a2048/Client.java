package com.example.a2048;

import java.io.*;
import java.net.Socket;

public class Client {
    final Socket s;
    final BufferedReader socketReader;
    final BufferedWriter socketWriter;
    final BufferedReader userInput;
    String host = "192.168.0.104";
    int port = 45000;
    static String ansServer;

    public Client() throws IOException {
        s = new Socket(host, port);
        socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();
    }

        public void run(  String userString ) {

            if (userString == null || userString.length() == 0 || s.isClosed()) {
                close();

            } else {
                try {
                    socketWriter.write(userString);
                    socketWriter.write("\n");
                    socketWriter.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }

        public synchronized void close() {
            if (!s.isClosed()) {
                try {
                    s.close();
                    System.exit(0);
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            }
        }


        private class Receiver implements Runnable{

            public void run() {
                while (!s.isClosed()) {
                    String line = null;
                    try {
                        line = socketReader.readLine();
                    } catch (IOException e) {
                        if ("Socket closed".equals(e.getMessage())) {
                            break;
                        }
                        System.out.println("Connection lost");
                        close();
                    }
                    if (line == null) {
                        System.out.println("Server has closed connection");
                        close();
                    } else {
                        ansServer = line;
                    }
                }
            }
        }
    }

