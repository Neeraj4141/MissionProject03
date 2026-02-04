package in.co.rays.project_3.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.ORSView;

public class ServletUtility {

	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.sendRedirect(page);
	}

	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lastCtl = (String) request.getAttribute("ORIGINAL_CTL");
		request.setAttribute("lastCtl", lastCtl);
		request.setAttribute("javax.servlet.error.exception", e);
		RequestDispatcher rd = request.getRequestDispatcher(ORSView.ERROR_CTL);
		rd.forward(request, response);
	}

	public static String getMessage(String property, HttpServletRequest request, HttpServletResponse response) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";

		} else {
			return val;
		}
	}

	public static void setErrorMessage(String msg, HttpServletRequest request, HttpServletResponse respose) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	public static String getErrorMessage(HttpServletRequest request, HttpServletResponse response) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setDto(BaseDTO dto, HttpServletRequest request) {
		request.setAttribute("dto", dto);
	}

	public static BaseDTO getDto(HttpServletRequest request) {
		return (BaseDTO) request.getAttribute("dto");
	}

	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	public static int getPageNo(HttpServletRequest request) {
		Integer a = (Integer) request.getAttribute("pageNo");
		return (a == null) ? 1 : a; // default 1
	}

	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	public static int getPageSize(HttpServletRequest request) {
		Integer a = (Integer) request.getAttribute("pageSize");
		return (a == null) ? 10 : a; // default 10
	}
}
