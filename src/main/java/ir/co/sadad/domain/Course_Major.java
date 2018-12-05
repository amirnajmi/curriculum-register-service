package ir.co.sadad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * @author ammac
 */
@Entity
public class Course_Major {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    @NotNull(message = "Must have factor!!")
    private Integer factor;

    @Basic
    private String teacherId;

    @OneToMany
    private List<Term_Course_Major> term_Course_Majors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public List<Term_Course_Major> getTerm_Course_Majors() {
        if (term_Course_Majors == null) {
            term_Course_Majors = new ArrayList<>();
        }
        return term_Course_Majors;
    }

    public void setTerm_Course_Majors(List<Term_Course_Major> term_Course_Majors) {
        this.term_Course_Majors = term_Course_Majors;
    }

    public void addTerm_Course_Major(Term_Course_Major term_Course_Major) {
        getTerm_Course_Majors().add(term_Course_Major);
    }

    public void removeTerm_Course_Major(Term_Course_Major term_Course_Major) {
        getTerm_Course_Majors().remove(term_Course_Major);
    }

}