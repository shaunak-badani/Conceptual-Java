package skbadani.usingguice;

import com.google.inject.Inject;

public class SquareRequest {
    DrawShape d;

    public DrawShape getDrawShape()
    {
        return d;
    }

    @Inject
    public void setDrawShape(DrawShape d)
    {
        System.out.println("Set draw shape called");
        this.d = d;
    }

    public void makeRequest()
    {
        d.draw();
    }
}
