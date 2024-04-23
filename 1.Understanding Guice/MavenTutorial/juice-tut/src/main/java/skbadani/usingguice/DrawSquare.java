package skbadani.usingguice;

import com.google.inject.Inject;

public class DrawSquare implements DrawShape 
{
    private String color;

    private Integer edge;

    @Inject
    public DrawSquare(@ColorValue String color, @EdgeValue Integer edge)
    {
        super();
        System.out.println("DrawSquare constructor called");
        this.color = color;
        this.edge = edge;
    }

    @Override
    public void draw()
    {
        System.out.println("Drawing square of color : " + color + " and egde: " + edge);
    }
}
