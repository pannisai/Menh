package mfs.util;

import javax.servlet.http.HttpServletRequest;

public class ServletParamUtil {
	private static ServletParamUtil instance;

	public static ServletParamUtil get() {
		if (instance == null) {
			instance = new ServletParamUtil();
		}
		return instance;
	}

	public boolean isValidParam(HttpServletRequest request, String paramName) {
		boolean isValidParam = false;

		if (request.getParameter(paramName) != null
				&& !request.getParameter(paramName).isEmpty()) {
			isValidParam = true;
		}

		return isValidParam;
	}

	/**
	 * Check the value of the specified param name is the matched.
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public boolean checkParamValue(HttpServletRequest request,
			String paramName, String paramValue) {
		boolean isMatched = false;

		if (request.getParameter(paramName) != null
				&& paramValue.equalsIgnoreCase(request.getParameter(paramName))) {
			isMatched = true;
		}

		return isMatched;
	}

	public int getIntParam(HttpServletRequest request, String paramName) {
		int intValue = 0;
		try {
			intValue = isValidParam(request, paramName) ? Integer
					.parseInt(request.getParameter(paramName)) : 1;
		} catch (NumberFormatException Ne) {
			intValue = 0;
		}
		return intValue;
	}

	public String getStringParam(HttpServletRequest request, String paramName) {
		String stringValue = "";
		stringValue = isValidParam(request, paramName) ? request
				.getParameter(paramName) : "";
		return stringValue;
	}

	public String[] getStringArrayParam(HttpServletRequest request,
			String paramName) {
		String[] stringArrayValue = new String[0];
		if (request.getParameter(paramName) != null) {
			stringArrayValue = request.getParameterValues(paramName);
		}
		return stringArrayValue;
	}
}
