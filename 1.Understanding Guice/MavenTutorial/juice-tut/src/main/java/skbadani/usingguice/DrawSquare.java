package skbadani.usingguice;

import com.google.inject.Inject;

public class DrawSquare implements DrawShape
{
    private final SquareRequest request;

    @Inject
    public DrawSquare(SquareRequest request)
    {
        this.request = request;
    }

    @Override
    public void draw()
    {
        System.out.println("Drawing square");
    }
}
