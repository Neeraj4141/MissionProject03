package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.util.JDBCDataSource;

public class UserModelJDBCImpl implements UserModelInt {
	public long nextPk() throws DatabaseException {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);

			}
		} catch (Exception e) {
			throw new DatabaseException("Database Exception" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	@Override
	public long add(UserDTO dto) throws ApplicationException {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk = nextPk();
			PreparedStatement pstmt = conn
					.prepareStatement("insert into ST_USERdto values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getFirstName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getGender());
			pstmt.setLong(5, dto.getRoleId());
			pstmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(7, dto.getMobileNo());
			pstmt.setString(8, dto.getLogin());
			pstmt.setString(9, dto.getPassword());
			pstmt.setString(10, dto.getConfirmPassword());
			pstmt.setTimestamp(11, dto.getLastLogin());
			pstmt.setInt(12, dto.getUnSuccessfullLogin());
			pstmt.setString(13, dto.getLoginIp());
			pstmt.setString(14, dto.getRegisteredIp());
			pstmt.setString(15, dto.getCreatedBy());
			pstmt.setString(16, dto.getModifiedBy());
			pstmt.setTimestamp(17, dto.getCreatedDatetime());
			pstmt.setTimestamp(18, dto.getModifiedDatetime());
			pstmt.executeUpdate();
			System.out.println("data updatetd successfully" + pk);
			conn.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Exception in add User");
			}
			throw new ApplicationException("Exception : Exception in Add User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	@Override
	public void delete(UserDTO dto) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from ST_USER where id=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Delete roll");
			}
			throw new ApplicationException("Exception in delete user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	@Override
	public void update(UserDTO dto) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"update ST_USER set FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,CONFIRMPASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, dto.getFirstName());
			pstmt.setString(2, dto.getLastName());
			pstmt.setString(3, dto.getLogin());
			pstmt.setString(4, dto.getPassword());
			pstmt.setString(5, dto.getConfirmPassword());
			pstmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(7, dto.getMobileNo());
			pstmt.setLong(8, dto.getRoleId());
			pstmt.setInt(9, dto.getUnSuccessfullLogin());
			pstmt.setString(10, dto.getGender());
			pstmt.setTimestamp(11, dto.getLastLogin());
			pstmt.setString(12, dto.getRegisteredIp());
			pstmt.setString(13, dto.getLoginIp());
			pstmt.setString(14, dto.getCreatedBy());
			pstmt.setString(15, dto.getModifiedBy());
			pstmt.setTimestamp(16, dto.getCreatedDatetime());
			pstmt.setTimestamp(17, dto.getModifiedDatetime());
			pstmt.setLong(18, dto.getId());
			pstmt.executeUpdate();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in uodate rollback");

			}
			throw new ApplicationException("Exception in updating user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	@Override
	public UserDTO findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		UserDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from ST_USER where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleId(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setLogin(rs.getString(8));
				dto.setPassword(rs.getString(9));
				dto.setConfirmPassword(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setUnSuccessdullLogin(rs.getInt(12));
				dto.setLoginIp(rs.getString(13));
				dto.setRegisteredIp(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in findbypk ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public UserDTO findByLogin(String login) throws ApplicationException {
		Connection conn = null;
		UserDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from ST_USER where LOGIN=?");
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleId(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setLogin(rs.getString(8));
				dto.setPassword(rs.getString(9));
				dto.setConfirmPassword(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setUnSuccessdullLogin(rs.getInt(12));
				dto.setLoginIp(rs.getString(13));
				dto.setRegisteredIp(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findbylogin ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		ArrayList array = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where 1=1");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleId(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setLogin(rs.getString(8));
				dto.setPassword(rs.getString(9));
				dto.setConfirmPassword(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setUnSuccessdullLogin(rs.getInt(12));
				dto.setLoginIp(rs.getString(13));
				dto.setRegisteredIp(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				array.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in User List");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return array;
	}

	@Override
	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		ArrayList array = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" AND LASTNAME like '" + dto.getLastName() + "%'");
			}
			if (dto.getLogin() != null && dto.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + dto.getLogin() + "%'");
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + dto.getPassword() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getGender());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" AND MOBILENO = " + dto.getMobileNo());
			}
			if (dto.getRoleId() > 0) {
				sql.append(" AND ROLEID = " + dto.getRoleId());
			}
			if (dto.getUnSuccessfullLogin() > 0) {
				sql.append(" AND UNSUCCESSFULLOGIN = " + dto.getUnSuccessfullLogin());
			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + dto.getGender() + "%'");
			}
			if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
				sql.append(" AND LASTLOGIN = " + dto.getLastLogin());
			}
			if (dto.getRegisteredIp() != null && dto.getRegisteredIp().length() > 0) {
				sql.append(" AND REGISTEREDIP like '" + dto.getRegisteredIp() + "%'");
			}
			if (dto.getLoginIp() != null && dto.getLoginIp().length() > 0) {
				sql.append(" AND LOGINIP like '" + dto.getLoginIp() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleId(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setLogin(rs.getString(8));
				dto.setPassword(rs.getString(9));
				dto.setConfirmPassword(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setUnSuccessdullLogin(rs.getInt(12));
				dto.setLoginIp(rs.getString(13));
				dto.setRegisteredIp(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				array.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in User Search");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return array;

	}

	@Override
	public List search(UserDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	@Override
	public boolean changePassword(long id, String newPassword, String oldPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserDTO authenticate(String login, String password) throws ApplicationException {
		Connection conn = null;
		UserDTO dto = null;
		StringBuffer sql = new StringBuffer("select * from ST_USER where login=?, and Password=?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setRoleId(rs.getLong(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setLogin(rs.getString(8));
				dto.setPassword(rs.getString(9));
				dto.setConfirmPassword(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setUnSuccessdullLogin(rs.getInt(12));
				dto.setLoginIp(rs.getString(13));
				dto.setRegisteredIp(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in Authenticate User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	@Override
	public boolean forgetPassword(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(UserDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long registerUser(UserDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getRoles(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
