package skbadani.usingguice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class SubsequentModule extends AbstractModule {

    public static final String SQUARE_KEY = "square";

    @Override
    protected void configure()
    {
        MapBinder<String, DrawShape> shapeBinder = MapBinder.newMapBinder(binder(), String.class, DrawShape.class);
        shapeBinder.addBinding(SQUARE_KEY).to(DrawSquare.class);
    }
}
