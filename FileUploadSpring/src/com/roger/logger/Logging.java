package com.roger.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logging {
	
	private Logger log = Logger.getLogger(getClass());
	
	@Pointcut("execution(* com.roger.controller.*.*(..))") // expression 
	private void controllerService() {}  // signature
	
	@Pointcut("execution(* com.roger.processer.*.*(..))") // expression 
	private void processerService() {}  // signature
	
	
	@Pointcut("execution(* com.roger.DAO.*.*(..))")
	private void DAOService() {}
	
	@Before("DAOService()")
	public void beforeDAOMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " called!");
	}
	
	@After("DAOService()")
	public void afterDAOMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " returned!");
	}
	
	@AfterThrowing(pointcut="DAOService()",throwing = "e")
	public void afterThrowingDAOMethod(JoinPoint point,Throwable e)
	{
		log.info(point.getSignature().getName() + " had a problem: " + e.getMessage(), e);
	}
	
	@Before("controllerService()")
	public void beforeControllerMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " called!");
	}
	
	@After("controllerService()")
	public void afterControllerMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " returned!");
	}
	
	@Before("processerService()")
	public void beforeProcesserMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " called!");
	}
	
	@After("processerService()")
	public void afterProcesserMethod(JoinPoint point)
	{
		log.info(point.getSignature().getName() + " returned!");
	}
}
