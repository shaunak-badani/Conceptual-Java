package skbadani.usingguice;

import com.google.inject.Inject;

public class SquareRequest {
    DrawShape d;

    @Inject
    SquareRequest(DrawShape d)
    {
        this.d = d;
    }

    public void makeRequest()
    {
        d.draw();
    }
}
