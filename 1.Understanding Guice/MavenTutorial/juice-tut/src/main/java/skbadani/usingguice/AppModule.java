package skbadani.usingguice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import org.slf4j.Logger;

import skbadani.audit.AuditInterceptor;
import skbadani.audit.Audited;

public class AppModule extends AbstractModule {

    @Override
    protected void configure()
    {
        install(new SubsequentModule());

        AuditInterceptor auditInterceptor = new AuditInterceptor();
        Logger auditLogger = auditInterceptor.getLogger();
        auditLogger.info("Wiring up AuditInterceptor");

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Audited.class), auditInterceptor);
    }
}
