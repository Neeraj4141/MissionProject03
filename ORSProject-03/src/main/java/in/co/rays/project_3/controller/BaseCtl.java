package in.co.rays.project_3.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;

public abstract class BaseCtl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_RESET = "Reset";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_UPDATE = "Update";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_CHANGE_MY_PROFILE = "MyProfile";

	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";

	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	protected boolean preload(HttpServletRequest request) {
		return true;
	}

	protected BaseDTO populateBean(BaseDTO dto, HttpServletRequest request) {

		System.out.println("populateDTO method in BaseCtl");

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		// UserDTO userDto=(UserDTO)request.getSession().getAttribute("user");

		HttpSession session = request.getSession();

		UserDTO userDto = (UserDTO) session.getAttribute("user");

		if (userDto == null) {
			createdBy = "root";
			modifiedBy = "root";

		} else {
			modifiedBy = userDto.getLogin();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDateTime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimeStamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimeStamp());
		}
		dto.setModifiedDatetime(DataUtility.getCurrentTimeStamp());
		return dto;
	}

}
