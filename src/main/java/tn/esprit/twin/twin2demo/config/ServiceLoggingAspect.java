package tn.esprit.twin.twin2demo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Pointcut("execution(* tn.esprit.twin.twin2demo.service.*.*(..))")
    public void serviceMethods() {
        // juste un alias
    }

   /* @Before("serviceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().toShortString();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("[AOP @Before] ➜ Entrée dans {}", name);
        log.info("[AOP @Before]    Arguments : {}", args);
    }*/


    @After("serviceMethods()")
    public void logMethodExit(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().toShortString();
        log.info("[AOP @After] ⇦ Sortie de la méthode {}", name);


    }



    @Before("execution( * tn.esprit.twin.twin2demo.service.*.addCommande(..))")
    public void logMethodEnter(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
                log.info("in methode before " +name +":");
    }






    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AOP @AfterReturning] Méthode {} terminée avec succès. Résultat = {}",
                methodName, result);
    }

}
