/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teachersystem.client;

import com.teachersystem.controllers.PrincipalController;
import com.teachersystem.persists.Students;
import com.teachersystem.bussines.Student;
import com.teachersystem.interpret.TeacherDataInterpreter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class HandleClient implements Runnable {

    private Client client;
    private static HandleClient handleClient;
    private final String HOST = "localhost";
    private final int PORT = 9000;
    private Students students;

    private HandleClient() {

        this.initSocket();
        this.students = new Students();

    }

    public static HandleClient getInstance() {
        if (handleClient != null) {
            return handleClient;
        } else {
            return handleClient = new HandleClient();
        }
    }

    public void initSocket() {
        try {
            Socket socket = new Socket(HOST, PORT);
            this.client = new Client();
            this.client.setSocket(socket);
            this.client.setIn(new DataInputStream(socket.getInputStream()));
            this.client.setOut(new DataOutputStream(socket.getOutputStream()));
            connect();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void connect() {
        try {
            this.client.getOut().writeUTF("CONNECT:TEACHERSYSTEM"); // Envía un mensaje de conexión al servidor

        } catch (IOException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage());
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (this.client.getIn().available() > 0) {
                    String data = this.client.getIn().readUTF();
                    
                    receiveData(data);

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void receiveData(String msg) {

        //TODO transform data and send to Oberservers
        if (msg.startsWith("TOKEN:")) {
            String token = msg.substring("TOKEN:".length());
          
            this.client.setIdClient(token);
        } else if (!msg.isEmpty()) {
            Map<String, Object> data = new TeacherDataInterpreter().decode(msg);
            manipulateData(data);
        } else {
            throw new RuntimeException("Error de conexión: " + msg);
        }

    }

    public void sendData(String action, Student data) {
//        
        try {
            String msg = new TeacherDataInterpreter().encode(action, data, "REGSYSTEM", "TEACHERSYSTEM");
          
            
            this.client.getOut().writeUTF(msg);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manipulateData(Map<String, Object> data) {
        if (data.get("Action").equals("SAVESTUDENT")) {
            this.getStudents().addStudent((Student) data.get("Student"));

        }
        if (data.get("Action").equals("UPDATEQUALIFICATION")) {
            Student aux = (Student) data.get("Student");
            this.getStudents().updateStudent(aux);

        }
    }

    public void init() {
        new Thread(this).start();
        new PrincipalController(this);
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

}
