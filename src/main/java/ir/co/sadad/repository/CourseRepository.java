package ir.co.sadad.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import ir.co.sadad.domain.Course;

public class CourseRepository extends AbstractRepository<Course, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseRepository() {
        super(Course.class);
    }

}
