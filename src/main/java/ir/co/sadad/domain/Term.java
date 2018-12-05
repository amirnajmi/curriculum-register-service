package ir.co.sadad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author ammac
 */
@Entity
public class Term {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Term_Course_Major> term_Course_Majors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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