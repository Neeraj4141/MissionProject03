package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.Date;

import in.co.rays.project_3.dto.UserDTO;

import in.co.rays.project_3.model.UserModelHibImpl;
import in.co.rays.project_3.model.UserModelInt;

public class TestUserModel {
	public static UserModelInt model = new UserModelHibImpl();

	public static void main(String[] args) throws Exception {
		// addTest();
		updateTest();

	}

	public static void addTest() throws Exception {

		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		dto.setFirstName("Neeraj");
		dto.setLastName("Mewada");
		dto.setDob(sdf.parse("15-12-2003"));
		dto.setConfirmPassword("Neeraj@123");
		dto.setPassword("Neeraj@123");
		dto.setLogin("neeraj@gmail.com");
		dto.setGender("male");
		dto.setUnSuccessdullLogin(2);
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setRoleId(1);
		dto.setMobileNo("9406653787");
		dto.setRegisteredIp("neeraj@gmail.com");
		dto.setLogin("neeraj@gmail.com");
		dto.setLastLogin(new Timestamp(new Date().getTime()));
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		System.out.println("add");
		long pk = model.add(dto);
		System.out.println(pk + "data successfully insert");
	}

	public static void updateTest() throws Exception {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		dto.setId(1L);
		dto.setFirstName("Mayank");
		dto.setLastName("agrawalll");
		dto.setDob(sdf.parse("31-12-1995"));
		dto.setConfirmPassword("1234");
		dto.setPassword("1234");
		dto.setLogin("Mayank@gmail.com");
		dto.setGender("males");
		dto.setUnSuccessdullLogin(2);
		dto.setCreatedBy("admins");
		dto.setModifiedBy("admins");
		dto.setRoleId(1);
		dto.setMobileNo("9406653787");
		dto.setRegisteredIp("aa@gmail.com");
		dto.setLogin("aa@gmail.com");
		dto.setLastLogin(new Timestamp(new Date().getTime()));
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("data update successfully");
	}

}