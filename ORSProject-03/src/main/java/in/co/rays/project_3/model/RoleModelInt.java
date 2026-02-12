package in.co.rays.project_3.model;

import java.sql.SQLException;
import java.util.List;

import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.exception.ApplicationException;

public interface RoleModelInt {
	public long add(RoleDTO dto) throws SQLException, ApplicationException;

	public void delete(RoleDTO dto) throws ApplicationException;

	public void update(RoleDTO dto) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(RoleDTO dto);

	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public RoleDTO findByPk(long pk) throws ApplicationException;

	public RoleDTO findBbyName(String name) throws ApplicationException;

}
