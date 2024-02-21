package com.example.webstart.service;

import com.example.webstart.exception.CourseNotFoundException;
import com.example.webstart.model.Course;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final List<Course> courses = new ArrayList<>();
    private final boolean someBool = false;

    @PostConstruct
    public void initCourses() {
        courses.add(new Course(
                1L,
                "Git basics",
                "John",
                "Tools",
                "Basics of Git version control")
        );
        courses.add(new Course(
                2L,
                "Java concurrency",
                "Marc",
                "Programming",
                "Java multithreading and concurrency")
        );
        courses.add(new Course(
                3L,
                "SQL Procedures",
                "Steven",
                "Programming",
                "Writing performant procedures in SQL")
        );
        courses.add(new Course(
                4L,
                "Algorithms",
                "Nick",
                "Alg",
                "basics algorithms")
        );
    }

    public Course getCourse(Long id) {
        return courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new CourseNotFoundException(id));
    }

    public List<Course> getCourses(List<String> arguments) {
        if (arguments != null) {
            List<Course> filteredCourses = new ArrayList<>();

            for (Course course : courses) {
                for (String argument : arguments) {
                    if (course.getCategory().equals(argument)) {
                        filteredCourses.add(course);
                    }
                }
            }
            return filteredCourses;
        } else return courses;
    }
    public void createCourse(Course course) {
        courses.add(course);
    }

    public void updateCourse(Course courseToUpdate) {
        Long id = courseToUpdate.getId();

        courses.stream()
                .filter(course -> course.getId().equals(id))
                .findAny()
                .ifPresentOrElse(
                        c -> {
                            c.setAuthor(courseToUpdate.getAuthor());
                            c.setTitle(courseToUpdate.getTitle());
                            c.setCategory(courseToUpdate.getCategory());
                            c.setDescription(courseToUpdate.getDescription());
                        },
                        () -> {throw new CourseNotFoundException(id);}
                );
    }
    public void deleteCourse(Long id) {
        courses.stream()
                .filter(course -> course.getId().equals(id))
                .findAny()
                .ifPresentOrElse(
                        courses::remove,
                        () -> {throw new CourseNotFoundException(id);}
                );
    }




}


