package ir.co.sadad.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import ir.co.sadad.domain.Course_Major;

public class Course_MajorRepository extends AbstractRepository<Course_Major, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Course_MajorRepository() {
        super(Course_Major.class);
    }

}
