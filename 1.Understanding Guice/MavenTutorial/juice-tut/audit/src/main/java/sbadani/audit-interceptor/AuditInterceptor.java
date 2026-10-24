package skbadani.audit;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);

    public Logger getLogger()
    {
        return logger;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        AuditGuard.setAudited(true);
        try
        {
            logger.info("Auditing {}", invocation.getMethod().getName());
            return invocation.proceed();
        }
        finally
        {
            AuditGuard.setAudited(false);
        }
    }
}
