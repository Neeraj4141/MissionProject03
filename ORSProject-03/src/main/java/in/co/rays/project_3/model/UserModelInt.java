package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

public interface UserModelInt {
	public long add(UserDTO dto) throws ApplicationException;

	public void delete(UserDTO dto) throws ApplicationException;

	public void update(UserDTO dto) throws ApplicationException;

	public UserDTO findByPk(long pk) throws ApplicationException;

	public UserDTO findByLogin(String login) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(UserDTO dto) throws ApplicationException;

	public boolean changePassword(long id, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException, DuplicateRecordException;

	public UserDTO authenticate(String login, String password) throws ApplicationException;

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException;

	public boolean resetPassword(UserDTO dto) throws ApplicationException, DuplicateRecordException, RecordNotFoundException;

	public long registerUser(UserDTO dto) throws ApplicationException;

	public List getRoles(UserDTO dto);

}
