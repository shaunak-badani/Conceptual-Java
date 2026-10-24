package skbadani.usingguice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import skbadani.audit.AuditInterceptor;
import skbadani.audit.Audited;

public class AppModule extends AbstractModule {

    @Override
    protected void configure()
    {
        install(new SubsequentModule());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Audited.class), new AuditInterceptor());
    }
}
