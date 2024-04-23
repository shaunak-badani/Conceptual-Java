package skbadani.noguice;

public class DrawSquare implements DrawShape 
{
    @Override
    public void draw()
    {
        System.out.println("Square has been drawn!");
    }
}
