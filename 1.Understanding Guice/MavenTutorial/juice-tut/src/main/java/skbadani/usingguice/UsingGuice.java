package skbadani.usingguice;

import com.google.inject.Guice;
import com.google.inject.Injector;

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
            SquareRequest req = injector.getInstance(SquareRequest.class);
            req.makeRequest();
        }

    }
}
