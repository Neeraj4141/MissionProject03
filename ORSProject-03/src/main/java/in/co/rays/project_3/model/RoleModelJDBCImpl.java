package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.util.JDBCDataSource;

public class RoleModelJDBCImpl implements RoleModelInt {

	public long nextPk() throws DatabaseException {
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(Id) from st_role");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);

			}
		} catch (JDBCConnectionException e) {
			throw e;

		} catch (Exception ex) {
			throw new DatabaseException("Exception in getting pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	@Override
	public long add(RoleDTO dto) throws SQLException, ApplicationException {

		Connection conn = null;
		long pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getDescription());
			pstmt.setString(4, dto.getCreatedBy());
			pstmt.setString(5, dto.getModifiedBy());
			pstmt.setTimestamp(6, dto.getCreatedDatetime());
			pstmt.setTimestamp(7, dto.getModifiedDatetime());
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());

			}
			throw new ApplicationException("Exception in add  Role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return 0;
	}

	@Override
	public void delete(RoleDTO dto) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Delete Roll");

			}
			throw new ApplicationException("Exception in roll Back");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	@Override
	public void update(RoleDTO dto) {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_role set NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?\"");
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setString(3, dto.getCreatedBy());
			pstmt.setString(4, dto.getModifiedBy());
			pstmt.setTimestamp(5, dto.getCreatedDatetime());
			pstmt.setTimestamp(6, dto.getModifiedDatetime());
			pstmt.setLong(7, dto.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();

		} catch (SQLException e) {

		}

	}

	@Override
	public List list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(RoleDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(RoleDTO dto, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDTO findByPk(long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleDTO findBbyName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
