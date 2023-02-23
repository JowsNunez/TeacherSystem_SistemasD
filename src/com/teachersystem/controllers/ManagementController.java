/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teachersystem.controllers;

import com.teachersystem.builders.StudentBuilder;
import com.teachersystem.bussines.Student;
import com.teachersystem.client.HandleClient;
import com.teachersystem.view.ManagementView;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author el_fr
 */
public class ManagementController implements Controller, Observer {

    private ManagementView managementView;
    private HandleClient handleClient;
    private JFrame afterFrame;
    private JTable tableStudents;

    public ManagementController(JFrame after, HandleClient handleClient) {
        this.afterFrame = after;
        after.dispose();
        this.handleClient = handleClient;
        this.managementView = new ManagementView(this);
        this.managementView.setVisible(true);
        this.createTable();

    }

    public void createTable() {
        HashSet<Student> students = this.handleClient.getStudents().getStudents();

        JScrollPane pnl = this.managementView.getPnlStudents();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Agrega las columnas al modelo
        model.addColumn("Id");
        model.addColumn("Name");
        model.addColumn("Last name");
        model.addColumn("Age");
        model.addColumn("Residence");
        model.addColumn("Qualification");
        // Agrega cada estudiante como una fila en el modelo
        for (Student student : students) {
            Object[] row = {student.getIdStudent(), student.getName(), student.getLastName(), student.getAge(), student.getResidence(), student.getQualification()};
            model.addRow(row);
        }

        tableStudents = new JTable(model);
        addListenersTable();
        pnl.setViewportView(tableStudents);

    }

    // cuando otro cliente modifique a un estudiante sera visible en la tabla
    @Override
    public void update() {

        this.createTable();
    }

    @Override
    public void openAfterView() {
        this.unSubscribe();
        new PrincipalController(handleClient);
    }

    @Override
    public void subscribe() {
        this.handleClient.getStudents().addObserver(this);
    }

    @Override
    public void unSubscribe() {
        this.handleClient.getStudents().removeObserver(this);
    }

    public void addListenersTable() {
        this.tableStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStudentsMouseClicked(evt);
            }
        });
    }

    public void tableStudentsMouseClicked(java.awt.event.MouseEvent evt) {

        String idStudent = (String) this.tableStudents.getModel().getValueAt(this.tableStudents.getSelectedRow(), 0);
        this.managementView.getTxtIdStudent().setText(idStudent);

    }

    public boolean updateQualification(String idStudent, int qualification) {

        if (idStudent.isEmpty()) {
            return false;
        }
        if (qualification <= 0) {
            return false;
        }
        for (Student s : handleClient.getStudents().getStudents()) {
            if (s.getIdStudent().equals(idStudent)) {
                s.setQualification(qualification);
                this.handleClient.getStudents().updateStudent(s);
                this.handleClient.sendData("UPDATEQUALIFICATION", s);
            }

        }
        return true;

    }

}
