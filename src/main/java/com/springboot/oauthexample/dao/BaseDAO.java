package com.springboot.oauthexample.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BaseDAO {
	
//	@Autowired
//	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	protected Session session;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getCurrentSession() {
		session = sessionFactory.getCurrentSession();
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	public void closeCurrentSession() {

		if (session != null)
			session.close();
	}

	public int save(Object obj) {
		session = getCurrentSession();
		return (Integer) session.save(obj);
	}

	public void update(Object obj) {
		session = getCurrentSession();
		session.update(obj);
	}

	public void merge(Object obj) {
		session = getCurrentSession();
		session.merge(obj);

	}

	public void delete(Object obj) {
		session = getCurrentSession();
		session.delete(obj);
	}

	public Object findById(Class<?> className, int id) {
		session = getCurrentSession();
		return session.get(className, id);
	}
}