package com.semars.dao;

import com.semars.AppOutput;
import com.semars.MetrolinkDao;
import com.semars.models.Stop;
import com.semars.models.Time;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SqliteJDBCDao implements MetrolinkDao {

    @Autowired
    private AppOutput appOutput;
    @Autowired
    private SessionFactory sessionFactoryBean;

    public List<Stop> getStopsMatchedStops(String stationName) {
        appOutput.print("Fetching stations...");
        stationName = "%" +stationName + "%";
        sessionFactoryBean.getCurrentSession().beginTransaction();
        Criteria criteria = sessionFactoryBean.getCurrentSession().createCriteria(Stop.class);
        criteria.add(Restrictions.like("stopName", stationName));
        List list = criteria.list();
        sessionFactoryBean.getCurrentSession().getTransaction().commit();
        return list;
    }

    public List<Time> getArrivalTimes(int stopID) {
        sessionFactoryBean.getCurrentSession().beginTransaction();
        Criteria criteria = sessionFactoryBean.getCurrentSession().createCriteria(Time.class);
        criteria.add(Restrictions.eq("stopID", stopID));
        criteria.addOrder(Order.asc("arrivalTime"));
        List list = criteria.list();
        sessionFactoryBean.getCurrentSession().getTransaction().commit();
        return list;
    }

    public void setAppOutput(AppOutput appOutput) {
        this.appOutput = appOutput;
    }
}
