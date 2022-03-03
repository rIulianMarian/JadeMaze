
import java.io.Serializable;

public class LabirintSendMessage implements Serializable
{
    private int[][] labirint;
    private int StartX;
    private int StartY;
    
    public void setLabirint(int[][] labirint)
    {
        this.labirint = labirint;
    }
    public int[][] getLabirint()
    {
        return labirint;
    }
    public void setStartX(int StartX)
    {
        this.StartX = StartX;
    }
    public int getStartX()
    {
        return StartX;
    }
    public void setStartY(int StartY)
    {
        this.StartY = StartY;
    }
    public int getStartY()
    {
        return StartY;
    }
}
