package ir.co.sadad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author ammac
 */
@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;

    @OneToMany
    private List<Course_Major> course_Majors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course_Major> getCourse_Majors() {
        if (course_Majors == null) {
            course_Majors = new ArrayList<>();
        }
        return course_Majors;
    }

    public void setCourse_Majors(List<Course_Major> course_Majors) {
        this.course_Majors = course_Majors;
    }

    public void addCourse_Major(Course_Major course_Major) {
        getCourse_Majors().add(course_Major);
    }

    public void removeCourse_Major(Course_Major course_Major) {
        getCourse_Majors().remove(course_Major);
    }

}