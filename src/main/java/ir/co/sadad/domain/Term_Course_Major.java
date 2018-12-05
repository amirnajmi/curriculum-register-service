package ir.co.sadad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Null;

/**
 * @author ammac
 */
@Entity
public class Term_Course_Major {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Null
    private Integer capacity;

    @OneToMany
    private List<Student_Course_Assignment> student_Course_Assignments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Student_Course_Assignment> getStudent_Course_Assignments() {
        if (student_Course_Assignments == null) {
            student_Course_Assignments = new ArrayList<>();
        }
        return student_Course_Assignments;
    }

    public void setStudent_Course_Assignments(List<Student_Course_Assignment> student_Course_Assignments) {
        this.student_Course_Assignments = student_Course_Assignments;
    }

    public void addStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment) {
        getStudent_Course_Assignments().add(student_Course_Assignment);
    }

    public void removeStudent_Course_Assignment(Student_Course_Assignment student_Course_Assignment) {
        getStudent_Course_Assignments().remove(student_Course_Assignment);
    }

}