package skbadani.usingguice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuditInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        AuditGuard.setAudited(true);
        try
        {
            return invocation.proceed();
        }
        finally
        {
            AuditGuard.setAudited(false);
        }
    }
}
