package edu.miu.backend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
    // @Autowired
    // private HttpServletRequest httpServletRequest;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* edu.miu.backend.service.impl..*(..))\"")
    public Object logging(ProceedingJoinPoint jp) throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = jp.proceed();
        stopWatch.stop();

        logger.info("Method " + jp.getSignature() + " executed in " + stopWatch.getTotalTimeMillis() + " ms.");
        return proceed;
    }

    @AfterThrowing(pointcut = "execution(* edu.miu.backend.service.impl..*(..))\"", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }
}
