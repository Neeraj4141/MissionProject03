package in.co.rays.project_3.model;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.util.HibDataSource;

public class RoleModelHibImpl implements RoleModelInt {

	@Override
	public long add(RoleDTO dto) throws SQLException, ApplicationException {
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Role Add " + e.getMessage());

		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(RoleDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete role" + e.getMessage());

		} finally {
			session.close();
		}

	}

	@Override
	public void update(RoleDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Update Roll");
		} finally {
			session.close();
		}

	}

	@Override
	public List list() throws ApplicationException {

		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in role List " + e.getMessage());

		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List search(RoleDTO dto) {
		return null;
	}

	@Override
	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in search Role " + e.getMessage());

		} finally {
			session.close();
		}
		return list;

	}

	@Override
	public RoleDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		try {
			RoleDTO dto = (RoleDTO) (session = HibDataSource.getSession());
			session.get(RoleDTO.class, pk);
			return dto;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting role " + e.getMessage());

		} finally {
			session.close();
		}

	}

	@Override
	public RoleDTO findBbyName(String name) throws ApplicationException {
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (RoleDTO) list.get(0);

			}
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting role by Name " + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

}
