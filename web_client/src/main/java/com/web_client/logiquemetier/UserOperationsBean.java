package com.web_client.logiquemetier;

import com.web_client.Model.CD;
import com.web_client.Model.Loan;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

@Stateless
public class UserOperationsBean {

    @PersistenceContext
    private EntityManager em;

    public List<CD> getAvailableCds() {
        return em.createQuery("SELECT c FROM CD c WHERE c NOT IN (SELECT l.cd FROM Loan l WHERE l.returnDate IS NULL)", CD.class)
                .getResultList();
    }

    public void borrowCd(Long cdId, Long userId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null) {
            Loan loan = new Loan();
            loan.setCd(cd);
            loan.setUserId(userId);
            loan.setLoanDate(new Date());
            em.persist(loan);
        }
    }

    public void returnCd(Long loanId) {
        Loan loan = em.find(Loan.class, loanId);
        if (loan != null) {
            loan.setReturnDate(new Date());
            em.merge(loan);
        }
    }

    public List<Loan> getUserLoans(Long userId) {
        return em.createQuery("SELECT l FROM Loan l WHERE l.userId = :userId AND l.returnDate IS NULL", Loan.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
