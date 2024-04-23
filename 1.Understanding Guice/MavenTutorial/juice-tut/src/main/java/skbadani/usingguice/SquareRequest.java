package skbadani.usingguice;

import com.google.inject.Inject;

public class SquareRequest {
    @Inject
    DrawShape d;

    public DrawShape getDrawShape()
    {
        return d;
    }
 

    public void makeRequest()
    {
        d.draw();
    }
}
