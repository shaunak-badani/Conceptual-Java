package skbadani.usingguice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class AppModule extends AbstractModule {

    @Override
    protected void configure()
    {
        install(new SubsequentModule());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(Audited.class), new AuditInterceptor());
    }
}
