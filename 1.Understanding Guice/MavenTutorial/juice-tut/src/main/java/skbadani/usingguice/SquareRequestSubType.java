package skbadani.usingguice;

import com.google.inject.Inject;

public class SquareRequestSubType extends SquareRequest{

    @Inject
    public SquareRequestSubType(DrawShape d)
    {
        super(d);
    }
    
    @Override
    public void makeRequest()
    {
        System.out.println("Sub type make request called");
        d.draw();
    }
}
