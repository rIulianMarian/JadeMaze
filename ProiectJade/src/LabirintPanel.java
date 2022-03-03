
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class LabirintPanel extends JPanel
{
    private boolean showPath = false;
    private boolean showRemainingSteps = false;
    private int[][] labirint;
    private int RenderDimension = 15;
    private ArrayList<MoveNode> SolveNodes = new ArrayList<MoveNode>();
    private int MoveC;
    public LabirintPanel(int[][] labirint, ArrayList<MoveNode> SolveNodes)
    {    
        this.labirint = labirint;
        this.SolveNodes = SolveNodes;
        MoveC = this.SolveNodes.size();
        //setSize(labirint[0].length*RenderDimension+50, labirint.length*RenderDimension+50);     
    }   
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        //g.translate(30, 30);
        for(int row = 0; row < labirint.length; row++)
        {
            for(int col = 0; col < labirint[0].length; col++)
            {
                Color color;
                switch(labirint[row][col])
                {
                    case 1: color = Color.BLACK; break;
                    case 2: color = Color.ORANGE; break;
                    case 3: color = Color.GREEN; break;
                    default: color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(RenderDimension*col, RenderDimension*row, RenderDimension, RenderDimension);
            }
        }
          
        if(MoveC>0) 
        {
            MoveC--;
        }
        if(SolveNodes.size()>0)
        {
            g.setColor(Color.red);
            g.fillOval(RenderDimension*SolveNodes.get(MoveC).getX(), RenderDimension*SolveNodes.get(MoveC).getY(), RenderDimension, RenderDimension);
            if(showRemainingSteps) 
            {
                g.drawString(Integer.toString(MoveC), RenderDimension*SolveNodes.get(MoveC).getX(), RenderDimension*SolveNodes.get(MoveC).getY());
            }
            if(showPath)
            {
                for(int i=MoveC+1;i<SolveNodes.size();i++)
                {
                    g.setColor(Color.red);
                    g.drawRect(RenderDimension*SolveNodes.get(i).getX(), RenderDimension*SolveNodes.get(i).getY(), RenderDimension, RenderDimension);   

                }
            }
        }
    }
    public int GetMoveC()
    {
        return MoveC;
    }    
    
}
