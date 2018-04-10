package com.sixframe.core.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.sixframe.core.entity.ResultMessage;

@RestController
public class AuthDeniedController implements ErrorController {
	
	private static final Log logger = LogFactory.getLog(AuthDeniedController.class);
	
	private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Value("${isDev}")
	private boolean isDev=false;
    
    @RequestMapping(value = PATH,  produces = {MediaType.APPLICATION_JSON_VALUE})
    ResultMessage error(HttpServletRequest request, HttpServletResponse response) {
    	return buildBody(request,response,isDev);
    }
    
    private ResultMessage buildBody(HttpServletRequest request,HttpServletResponse response,Boolean includeStackTrace){
        Map<String,Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
        Integer status = (Integer)errorAttributes.get("status");//http状态码
        String path = (String)errorAttributes.get("path");//请求路径
        String messageFound = (String)errorAttributes.get("message");//错误信息
        String exception = errorAttributes.get("exception")==null?"未知异常":errorAttributes.get("exception").toString();//异常类型
        String message="";//异常描述
        String trace ="";//异常栈信息
        String exceptionClass = exception.substring(exception.lastIndexOf(".")+1,exception.lastIndexOf(":")>0?exception.lastIndexOf(":"):exception.length());
        logger.debug("===========" + exceptionClass + "===============");
        if(!StringUtils.isEmpty(path)){
            message=String.format(messageFound);
        }
        switch (exceptionClass) {
		case "AccessDeniedException"://无权访问
			message = "无访问权限";
			break;
		case "ExpiredJwtException"://token失效
			message = "token已失效，请重新登录";
			break;
		case "LockedException"://账户已锁定
			break;
		case "BusinessException"://业务异常
			break;
		case "HttpRequestMethodNotSupportedException":
			message = "请求方式错误";
		default:
			response.setStatus(status);
			break;
		}
        if(includeStackTrace) {
             trace = (String) errorAttributes.get("trace");
             if(!StringUtils.isEmpty(trace)) {
                 message += String.format(" and trace %s", trace);
             }
        }
        logger.debug((String) errorAttributes.get("trace"));
        response.setStatus(Integer.valueOf(HttpStatus.OK.toString()));//只要请求无错，就把httpstatus设置为200，然后由返回数据的status值来判断异常
        return new ResultMessage(status.toString(),exception,message,path);
    }
    
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
    	ServletWebRequest requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
