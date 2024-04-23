package skbadani.usingguice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppModule extends AbstractModule {
    
    @Override
    protected void configure()
    {
        bind(DrawShape.class).to(DrawSquare.class).in(Scopes.SINGLETON);
        bind(SquareRequest.class).to(SquareRequestSubType.class);

        bind(String.class).annotatedWith(ColorValue.class).toInstance("Red");
        bind(Integer.class).annotatedWith(EdgeValue.class).toInstance(40);
    }
}
