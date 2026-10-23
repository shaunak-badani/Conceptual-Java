package skbadani.usingguice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class UsingGuice 
{
    private static final String SQUARE_REQ = "SQUARE";

    public static void main( String[] args )
    {
        System.out.println( "Drawing Square!" );
        sendRequest(SQUARE_REQ);
    }

    private static void sendRequest(String squareReq)
    {
        if(squareReq.equals(SQUARE_REQ))
        {
            Injector injector = Guice.createInjector(new AppModule());

            // Raw lookup, no TypeLiteral: generics are erased at runtime, so Guice
            // sees this as a request for Map.class, not Map<String, DrawShape>, and
            // can't find a matching binding -> ConfigurationException.
            Map rawShapeMap = injector.getInstance(Key.get(new TypeLiteral<Map<String, DrawShape>>(){}));

            SquareRequest req = injector.getInstance(SquareRequest.class);
            req.makeRequest();
        }

    }
}
