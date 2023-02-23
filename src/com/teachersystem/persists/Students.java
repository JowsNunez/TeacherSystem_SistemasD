/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teachersystem.persists;

import com.teachersystem.controllers.Observer;
import com.teachersystem.bussines.Student;
import com.teachersystem.controllers.ManagementController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author el_fr
 */
public class Students implements Observable {

    private HashSet<Student> students;

    private List<Observer> observers;

    public Students() {
        this.students = new HashSet<>();
        this.observers = new ArrayList<>();
    }

    public HashSet<Student> getStudents() {
        return students;
    }

    public void setStudents(HashSet<Student> students) {
        this.students = students;
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public String toString() {
        return "Students{" + "students=" + students + ", observers=" + observers + '}';
    }

    public void addStudent(Student student) {
        this.students.add(student);
        setUpdate(); // Notificar a los observadores del cambio
    }

    public void updateStudent(Student student) {
        for (Student s : students) {
            if (s.getIdStudent().equalsIgnoreCase(student.getIdStudent())) {
                s = student;
            }
        }
        setUpdate(); // Notificar a los observadores del cambio
    }

    @Override
    public void setUpdate() {
        for (Observer o : observers) {
            if (o != null) {
                o.update();

            }
        }
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
}
