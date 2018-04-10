package com.sixframe.utils.exception;

import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);
	protected String logInfo = null;
	protected String errorCode = "UNKNOW_ERROR";
	protected String[] errorArgs = null;
	protected String errorMessage = null;
	// private static ResourceBundle rb =
	// ResourceBundle.getBundle("resource/i18n/biz_errors");

	public BusinessException(String errorCode, String[] errorArgs) {
		String log_code = "";
		String[] errorArgsTemp = new String[errorArgs.length + 1];
		for (int i = 0; i < errorArgs.length; i++) {
			errorArgsTemp[i] = errorArgs[i];
		}
		errorArgsTemp[errorArgs.length] = log_code;
		this.errorCode = errorCode;
		this.errorArgs = errorArgsTemp;
		if (logger.isInfoEnabled()) {
			logger.info(errorCode + log_code);
		}
	}

	public BusinessException(String errorCode, String[] errorArgs, Throwable cause) {
		super(cause);
		String log_code = "";
		String[] errorArgsTemp = new String[errorArgs.length + 1];
		for (int i = 0; i < errorArgs.length; i++) {
			errorArgsTemp[i] = errorArgs[i];
		}
		errorArgsTemp[errorArgs.length] = log_code;
		this.errorCode = errorCode;
		this.errorArgs = errorArgsTemp;
		if (logger.isInfoEnabled()) {
			logger.info(errorCode + log_code);
		}
	}

	public BusinessException(String errorCode, String errorArg, Throwable cause) {
		super(cause);
		String log_code = "";
		this.errorCode = errorCode;
		this.errorArgs = new String[] { errorArg, log_code };
		if (logger.isInfoEnabled()) {
			logger.info(errorCode + log_code);
		}
	}

	public BusinessException(String errorMessage) {
		String message = errorMessage;
		if ((errorMessage == null) || (errorMessage.trim().length() < 2)) {
			message = "构造或抛出BusinessException时，参数必须有内容。原输入参数：[" + errorMessage + "]不合法！";
		}
		this.errorMessage = message;
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public BusinessException(String errorMessage, boolean hasPageCode) {
		String message = null;
		if ((errorMessage == null) || (errorMessage.trim().length() < 2)) {
			message = "构造或抛出BusinessException时，参数必须有内容。原输入参数：[" + errorMessage + "]不合法！";
		} else if (hasPageCode) {
			message = errorMessage + " [code=" + System.currentTimeMillis() + "]";
			if (logger.isInfoEnabled()) {
				logger.info(message);
			}
		} else {
			message = errorMessage;
		}
		this.errorMessage = message;
	}

	public BusinessException(String errorMessage, String logInfo) {
		String message = null;

		String code = "";
		message = errorMessage + code;
		if (logger.isInfoEnabled()) {
			logger.info(message + ". [logInfo=]" + logInfo);
		}
		this.errorMessage = message;
	}

	public BusinessException(String errorMessage, String logInfo, boolean hasPageCode) {
		String message = null;
		if (hasPageCode) {
			String code = " [code=" + System.currentTimeMillis() + "]";
			message = errorMessage + code;
			if (logger.isInfoEnabled()) {
				logger.info(message + ". [logInfo=]" + logInfo);
			}
		} else {
			String code = " [code=" + System.currentTimeMillis() + "]";
			message = errorMessage;
			if (logger.isInfoEnabled()) {
				logger.info(message + code + ". [logInfo=]" + logInfo);
			}
		}
		this.errorMessage = message;
	}

	public String getMessage() {
		if (this.errorMessage != null) {
			return this.errorMessage;
		}
		String message;
		message = "未知系统异常，请联系管理员！";
		if (this.errorArgs != null) {
			message = MessageFormat.format(message, (Object[]) this.errorArgs);
		}
		return message + "\nError Code is: " + this.errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public BusinessException(String msg, Throwable cause) {
		super(msg, cause);
		String message = msg;
		this.errorMessage = message;
		logger.error(msg, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
		String message = cause.getMessage();
		if ((message == null) || (message.trim().length() < 2)) {
			message = cause.toString();
		}
		this.errorMessage = message;
		logger.error(message, cause);
	}

	public String getLogInfo() {
		return this.logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
}
