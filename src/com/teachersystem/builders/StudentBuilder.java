/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teachersystem.builders;

import com.teachersystem.bussines.Student;

/**
 *
 * @author el_fr 
 */
public class StudentBuilder implements Builder<Student> {

    private final Student student;

    public StudentBuilder() {
        this.student = new Student();
    }

    public StudentBuilder setName(String name) {

        this.student.setName(name);
        return this;

    }

    public StudentBuilder setLastName(String lastName) {

        this.student.setLastName(lastName);
        return this;

    }

    public StudentBuilder setAge(int age) {
        this.student.setAge(age);
        return this;

    }

    public StudentBuilder setResidence(String residence) {
        this.student.setResidence(residence);
        return this;
    }

    public StudentBuilder setQualification(int qualification) {
        this.student.setQualification(qualification);
        return this;
    }

    public StudentBuilder setIdStudent(String IdStudent) {
        this.student.setIdStudent(IdStudent);
        return this;
    }

    @Override
    public Student build() {
        return this.student;
    }

}
