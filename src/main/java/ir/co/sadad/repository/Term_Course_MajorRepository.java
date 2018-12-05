package ir.co.sadad.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import ir.co.sadad.domain.Term_Course_Major;

public class Term_Course_MajorRepository extends AbstractRepository<Term_Course_Major, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Term_Course_MajorRepository() {
        super(Term_Course_Major.class);
    }

}
