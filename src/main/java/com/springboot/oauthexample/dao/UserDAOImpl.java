package com.springboot.oauthexample.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.oauthexample.entities.User;

@Transactional
@Repository("UserDAO")
public class UserDAOImpl extends BaseDAO implements UserDAO {

	public void saveObject(Object entity) throws Exception {
//		persist(entity);
	}

	public void deleteObject(Object entity) {
		delete(entity);
	}

	public void updateObject(Object entity) {
		update(entity);
	}

	@Override
	public <T> T getObjectById(Class<T> entity, Serializable id) {
		return (T) getCurrentSession().get(entity, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T listObject(Class<T> entity, String id) {
		Criteria criteria = getCurrentSession().createCriteria(entity).addOrder(Order.asc(id));
		return (T) criteria.list();
	}

	@Override
	public User logIn(User user) {
		Criteria criteria = getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("username", user.getUsername()))
				.add(Restrictions.eq("password", user.getPassword()));
		return (User) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T> T getObjectByParam(Class<T> entity, String param, Object paramValue) {
		Criteria criteria = getCurrentSession().createCriteria(entity).add(Restrictions.eq(param, paramValue));
		return (T) criteria.uniqueResult();
	}

}
