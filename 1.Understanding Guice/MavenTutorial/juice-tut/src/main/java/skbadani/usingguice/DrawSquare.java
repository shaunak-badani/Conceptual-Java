package skbadani.usingguice;

import com.google.inject.Inject;

public class DrawSquare implements DrawShape 
{
    private String color;

    private Integer edge;

    @Inject
    public DrawSquare(String color, Integer edge)
    {
        super();
        this.color = color;
        this.edge = edge;
    }

    @Override
    public void draw()
    {
        System.out.println("Drawing square of color : " + color + " and egde: " + edge);
    }
}
