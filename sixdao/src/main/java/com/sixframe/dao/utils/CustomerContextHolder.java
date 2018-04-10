package com.sixframe.dao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerContextHolder {
	public static final Logger log = LoggerFactory.getLogger(CustomerContextHolder.class);
	
	public final static String SESSION_FACTORY_DEFAULT = "base";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setContextType(String contextType) { 
    	log.info("切换到{}数据连接", contextType);
        contextHolder.set(contextType);  
    }  
      
    public static String getContextType() {
    	if(contextHolder.get() == null){
    		contextHolder.set(SESSION_FACTORY_DEFAULT);
    	}
        return contextHolder.get();  
    }  
      
    public static void clearContextType() {  
        contextHolder.remove();  
    }
}
