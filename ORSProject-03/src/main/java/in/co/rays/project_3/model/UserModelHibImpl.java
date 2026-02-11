package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class UserModelHibImpl implements UserModelInt {

	@Override
	public long add(UserDTO dto) throws ApplicationException {
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {
			int pk = 0;
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());

		} finally {
			session.close();
		}
		return dto.getId();
	}

	@Override
	public void delete(UserDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete User " + e.getMessage());

		} finally {
			session.close();
		}

	}

	@Override
	public void update(UserDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Upadte " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public UserDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in User FindByPk " + e.getMessage());

		} finally {
			session.close();
		}
		return dto;

	}

	@Override
	public UserDTO findByLogin(String login) throws ApplicationException {
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);

			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User By Find by login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
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
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in User List ");

		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}

				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getLogin() != null && dto.getLogin().length() > 0) {
					criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
					criteria.add(Restrictions.eq("lastLogin", dto.getLastLogin()));
				}
				if (dto.getRoleId() > 0) {
					criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
				}
				if (dto.getUnSuccessfullLogin() > 0) {
					criteria.add(Restrictions.eq("unSuccessfulLogin", dto.getUnSuccessfullLogin()));
				}
			}
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public List search(UserDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException, DuplicateRecordException {
		boolean flag = false;
		UserDTO dtoExist = null;
		dtoExist = findByPk(id);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (ApplicationException e) {
				throw new DuplicateRecordException("Login is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getLogin());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFirstName());
		map.put("lastName", dtoExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);
		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getLogin());
		msg.setSubject("Password has beaan changed Successfully");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;
	}

	@Override
	public UserDTO authenticate(String login, String password) throws ApplicationException {
		Session session = null;
		UserDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from UserDTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;
		}
		return dto;
	}

	@Override
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserDTO userData = findByLogin(login);
		boolean flag = false;
		if (userData == null) {
			throw new RecordNotFoundException("Email id Does not match");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", login);
		map.putIfAbsent("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastame", userData.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNRAYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;
		return flag;
	}

	@Override
	public boolean resetPassword(UserDTO dto)
			throws ApplicationException, RecordNotFoundException, DuplicateRecordException {
		// TODO Auto-generated method stub
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPk(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (ApplicationException e) {
			throw new DuplicateRecordException("login already exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	@Override
	public long registerUser(UserDTO dto) throws ApplicationException {
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is Successfull for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		return 0;
	}

	@Override
	public List getRoles(UserDTO dto) {
		
		return null;
	}

}
