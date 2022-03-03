
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

public class AgentLabirintGUI_1 extends JFrame
{
    private int[][] labirint;
    private int RenderDimension = 15;
    private ArrayList<MoveNode> SolveNodes = new ArrayList<MoveNode>();
    private int MoveC;
    public AgentLabirintGUI_1(int[][] labirint, ArrayList<MoveNode> SolveNodes)
    {    
        this.labirint = labirint;
        this.SolveNodes = SolveNodes;
        MoveC = this.SolveNodes.size();
        setTitle("Labirint");
        setSize(labirint[0].length*RenderDimension+50, labirint.length*RenderDimension+50);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    }
    
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        g.translate(30, 30);
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
                    //case 4: color = Color.PINK; break;
                    default: color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(RenderDimension*col, RenderDimension*row, RenderDimension, RenderDimension);
                //g.setColor(Color.BLACK);
                //g.drawRect(RenderDimension*col, RenderDimension*row, RenderDimension, RenderDimension);
            }
        }
          
        if(MoveC>0) 
        {
            MoveC--;
        }
        g.setColor(Color.red);
        g.fillOval(RenderDimension*SolveNodes.get(MoveC).getX(), RenderDimension*SolveNodes.get(MoveC).getY(), RenderDimension, RenderDimension);                      
    }
    public int GetMoveC()
    {
        return MoveC;
    }
}
