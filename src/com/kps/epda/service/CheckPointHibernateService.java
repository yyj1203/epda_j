package com.kps.epda.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.epda.dao.CheckPointHibernateDao;
import com.kps.epda.domain.CheckPoint;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class CheckPointHibernateService {
    private CheckPointHibernateDao checkPointDao;

    
    /**
     * @return the checkPointDao
     */
    public CheckPointHibernateDao getCheckPointDao(){
        return checkPointDao;
    }

    
    /**
     * @param checkPointDao the checkPointDao to set
     */
    public void setCheckPointDao(CheckPointHibernateDao checkPointDao){
        this.checkPointDao = checkPointDao;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<CheckPoint> getCheckPoints() {
        return checkPointDao.getCheckPoints();
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public CheckPoint insertCheckPoint(CheckPoint checkPoint) {
        return checkPointDao.insertCheckPoint(checkPoint);
    }
}
