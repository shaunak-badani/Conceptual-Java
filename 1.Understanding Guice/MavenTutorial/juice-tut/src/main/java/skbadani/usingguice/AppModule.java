package skbadani.usingguice;

import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    
    @Override
    protected void configure()
    {
        bind(DrawShape.class).to(DrawSquare.class);
        bind(SquareRequest.class).to(SquareRequestSubType.class);

        bind(String.class).toInstance("Red");
        bind(Integer.class).toInstance(40);
    }
}
