package com.example.webstart.exception;

public class TeacherAlreadyExistsException extends RuntimeException{
    public TeacherAlreadyExistsException(String name, String surname) {
        super("teacher " + name + " " + surname + " is already registered !");
    }
}
