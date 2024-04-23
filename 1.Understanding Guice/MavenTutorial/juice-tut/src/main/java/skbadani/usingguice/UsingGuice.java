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
            SquareRequest req1 = injector.getInstance(SquareRequest.class);
            req1.makeRequest();

            SquareRequest req2 = injector.getInstance(SquareRequest.class);
            req2.makeRequest();

            boolean areObjectsEqual = req2.getDrawShape() == req1.getDrawShape();
            System.out.println("Are draw shapes equal? " + areObjectsEqual);
        }

    }
}
