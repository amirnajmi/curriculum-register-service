package ir.co.sadad.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import ir.co.sadad.domain.Student_Course_Assignment;

public class Student_Course_AssignmentRepository extends AbstractRepository<Student_Course_Assignment, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Student_Course_AssignmentRepository() {
        super(Student_Course_Assignment.class);
    }

}
