package com.kps.epda.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.epda.domain.CheckPoint;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class CheckPointHibernateDao extends HibernateDaoSupport {
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<CheckPoint> getCheckPoints() {
        return getHibernateTemplate().find("from TB_POINT");
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public CheckPoint insertCheckPoint(CheckPoint checkPoint) {
        return (CheckPoint) getHibernateTemplate().merge(checkPoint);
    }
    
    
    
}
