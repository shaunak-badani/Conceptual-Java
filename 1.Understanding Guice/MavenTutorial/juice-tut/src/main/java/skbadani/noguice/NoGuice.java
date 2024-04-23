package skbadani.noguice;

/**
 * Hello world!
 *
 */
public class NoGuice 
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
            DrawShape d = new DrawSquare();
            SquareRequest req = new SquareRequest(d);
            req.makeRequest();
        }

    }
}
