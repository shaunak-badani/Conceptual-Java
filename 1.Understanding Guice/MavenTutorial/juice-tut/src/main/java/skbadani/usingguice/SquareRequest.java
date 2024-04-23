package skbadani.usingguice;

import com.google.inject.Inject;

public class SquareRequest {
    DrawShape d;

    public DrawShape getDrawShape()
    {
        return d;
    }

    @Inject
    SquareRequest(DrawShape d)
    {
        System.out.println("Square Request constructor called");
        this.d = d;
    }

    public void makeRequest()
    {
        d.draw();
    }
}
