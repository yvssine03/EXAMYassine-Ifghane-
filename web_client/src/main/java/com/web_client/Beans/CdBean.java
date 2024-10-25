package com.web_client.Beans;

import com.web_client.Model.CD;
import com.web_client.logiquemetier.UserOperationsBean;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean
public class CdBean {

    @EJB
    private UserOperationsBean userOps;

    private List<CD> availableCds;

    public List<CD> getAvailableCds() {
        if (availableCds == null) {
            availableCds = userOps.getAvailableCds();
        }
        return availableCds;
    }

    public void borrowCd(Long cdId, Long userId) {
        userOps.borrowCd(cdId, userId);
        availableCds = userOps.getAvailableCds();
    }
}
