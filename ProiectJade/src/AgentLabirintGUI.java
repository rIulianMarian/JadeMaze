
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AgentLabirintGUI extends JFrame
{
    LabirintPanel Panel;
    JPanel TextPanel = new JPanel();
    JTextArea Text = new JTextArea("Detalii Agent");
    private int RenderDimension = 15;
    public AgentLabirintGUI(int[][] labirint, ArrayList<MoveNode> SolveNodes)
    {    
        Panel = new LabirintPanel(labirint, SolveNodes); 
        setTitle("Labirint");
        setSize(labirint[0].length*RenderDimension*2+20, labirint.length*RenderDimension+30);
        setLocationRelativeTo(null);
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,2));
        
        container.add(Panel);
        
        Text.setEditable(false);
        Text.setLineWrap(false);
        Text.setOpaque(false);
        Text.setBorder(BorderFactory.createEmptyBorder());        
        
        TextPanel.add(Text);
        container.add(TextPanel);
        this.add(container);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    public int GetMoveC()
    {
        return Panel.GetMoveC();
    }
    public void setText(String text)
    {
        Text.setText(text);
    }
    public String getText()
    {
        return Text.getText();
    }
    public void addText(String text)
    {
        Text.setText(getText()+"\n"+text);
    }
}
