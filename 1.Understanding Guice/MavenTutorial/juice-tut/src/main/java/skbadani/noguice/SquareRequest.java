package skbadani.noguice;

public class SquareRequest {
    DrawShape d;

    SquareRequest(DrawShape d)
    {
        this.d = d;
    }

    public void makeRequest()
    {
        d.draw();
    }
}
