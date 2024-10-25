package com.web_client.logiquemetier;

import com.web_client.Model.CD;
import com.web_client.Model.Loan;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateful
public class AdminOperationsBean {

    @PersistenceContext
    private EntityManager em;

    public void addCd(CD cd) {
        em.persist(cd);
    }

    public void updateCd(CD cd) {
        em.merge(cd);
    }

    public void deleteCd(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null) {
            em.remove(cd);
        }
    }

    public List<Loan> getCurrentloans() {
        return em.createQuery("SELECT l FROM Loan l WHERE l.returnDate IS NULL", Loan.class)
                .getResultList();
    }
    public CD getCdById(Long id) {
        return em.find(CD.class, id); // Utilise EntityManager pour trouver le CD par son ID
    }
}
