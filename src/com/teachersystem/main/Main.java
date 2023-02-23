/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.teachersystem.main;

import com.teachersystem.client.HandleClient;

/**
 *
 * @author el_fr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HandleClient instanceHandle = HandleClient.getInstance();
        instanceHandle.init();
    }

}
