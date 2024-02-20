package com.example.webstart.service;

import com.example.webstart.exception.TeacherAlreadyExistsException;
import com.example.webstart.exception.TeacherDataIsTheSame;
import com.example.webstart.exception.TeacherNotFoundException;
import com.example.webstart.model.Teacher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private final List<Teacher> teachers = new ArrayList<>();
    @Value("${soft.delete}")
    boolean softDelete;

    @PostConstruct
    public void initTeachers() {
        teachers.add(new Teacher(
                1L,
                "Mark",
                "Smith",
                List.of("Maths", "English", "Polish")));
        teachers.add(new Teacher(
                2L,
                "Tom",
                "Johnon",
                List.of("Religion", "History")));
    }

    public List<Teacher> getTeachers(List<String> subjects) {
        if (subjects != null) {
            List<Teacher> filteredTeachers = new ArrayList<>();
            for (int i = 0; i < teachers.size(); i++) {
                for (int j = 0; j < subjects.size(); j++) {
                    if (teachers.get(i).getSubjects().contains(subjects.get(j))
                            && !filteredTeachers.contains(teachers.get(i))
                            && teachers.get(i).isActive()) {
                        filteredTeachers.add(teachers.get(i));
                    }
                }
            }
            return filteredTeachers;
        }
        return teachers;
    }

    public Teacher getTeacher(Long id) {
        return teachers.stream()
                .filter(teacher -> teacher.getId().equals(id))
                .findFirst()
                .orElse(new Teacher());
    }

    public void createTeacher(Teacher teacher) {

        for (int i = 0; i < teachers.size(); i++) {
            if (teacher.getName().equals(teachers.get(i).getName())
                    && teacher.getSurname().equals(teachers.get(i).getSurname())) {
                throw new TeacherAlreadyExistsException(teacher.getName(), teacher.getSurname());
            }
        }
        teachers.add(teacher);
    }

    public void updateTeacher(Teacher teacherToUpdate) {
        Long id = teacherToUpdate.getId();

        teachers.stream()
                .filter(teacher -> teacher.getId().equals(id))
                .findAny()
                .filter(teacher -> !teacher.getName().equals(teacherToUpdate.getName())
                        || !teacher.getSurname().equals(teacherToUpdate.getSurname())
                        || !teacher.getSubjects().equals(teacherToUpdate.getSubjects()))
                .ifPresentOrElse(
                        c -> {
                            c.setName(teacherToUpdate.getName());
                            c.setSurname(teacherToUpdate.getSurname());
                            c.setSubjects(teacherToUpdate.getSubjects());
                        },
                        () -> {
                            throw new TeacherDataIsTheSame();
                        }
                );
    }

    public void deleteTeacher(Long id) {
        teachers.stream()
                .filter(teacher -> teacher.getId().equals(id))
                .findAny()
                .ifPresentOrElse(
                        (teacher) -> {
                            if (!softDelete) teachers.remove(teacher);
                            teacher.setIsActive(false);
                        },
                        () -> {
                            throw new TeacherNotFoundException(id);
                        }
                );
    }

}








