package skbadani.usingguice;

import java.util.Map;

import com.google.inject.Inject;

public class SquareRequest {

    private DrawShape d;

    @Inject
    public SquareRequest(Map<String, DrawShape> shapes)
    {
        if(!shapes.containsKey("square"))
        {
            throw new IllegalArgumentException("square is not in the list of bindings!");
        }
        this.d = shapes.get("square");
    }

    public DrawShape getDrawShape()
    {
        return d;
    }
 

    public void makeRequest()
    {
        d.draw();
    }
}
