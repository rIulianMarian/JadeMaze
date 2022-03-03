
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentLabirint extends Agent
{
    public AgentLabirintGUI gui;
    public boolean CanMove = false;
    public int[][] labirint;
    public boolean VreauPasiPutini;
    public int WaitALiL = 0;
    private int ExitStatus = 2; //0 = nimic, 1 = inchide, 2 = repeta
    private ArrayList<MoveNode> DFSNodes = new ArrayList<MoveNode>();
    private ArrayList<MoveNode> BFSNodes = new ArrayList<MoveNode>();
    private ArrayList<MoveNode> AStarNodes = new ArrayList<MoveNode>();
    private ArrayList<MoveNode> SolveNodes = new ArrayList<MoveNode>();
    private boolean DFSRunning=true,BFSRunning=true,AStarRunning=true;
    private boolean GuiRunning=false;
    public void setup()
    {
        VreauPasiPutini = (Math.random() < 0.5);
        addBehaviour(new ListSendBehaviour());
        addBehaviour(new AsteaptaRaspuns());
        addBehaviour(new Move(this, 100));
    }
    public void takeDown() 
    {
        if(gui!=null) gui.dispose();
    }    
    private class Move extends TickerBehaviour
    {

        public Move(Agent a, long period) 
        {
            super(a, period);
        }
        public void onTick()
        {
            if(CanMove == true)
            {
                gui.repaint();
                if(gui.GetMoveC() <= 0)
                {
                    WaitALiL++;
                    if(WaitALiL == 10)
                    {
                        if(ExitStatus == 2)
                        {
                            CanMove = false;
                            GuiRunning = false;
                            gui.dispose();
                            WaitALiL = 0;
                            DFSNodes.removeAll(DFSNodes);
                            BFSNodes.removeAll(BFSNodes);
                            AStarNodes.removeAll(AStarNodes);
                            SolveNodes.removeAll(SolveNodes);
                            VreauPasiPutini = (Math.random() < 0.5);
                            addBehaviour(new ListSendBehaviour());
                        }
                        else if(ExitStatus == 1)
                        {
                            System.exit(0);
                        }
                    }
                }
            }
        }        
    }
    private class ListSendBehaviour extends OneShotBehaviour
    {
        public ListSendBehaviour()
        {

        }    
        public void action()
        {
            ACLMessage message = new ACLMessage();
            LabirintSendMessage lsa = new LabirintSendMessage();
            labirint = GenerateLabirint.GetLab();
            lsa.setLabirint(labirint);
            lsa.setStartX(getStartX());
            lsa.setStartY(getStartY());
            AID receiverID = new AID("DFS", AID.ISLOCALNAME);
            message.addReceiver(receiverID);
            receiverID = new AID("BFS", AID.ISLOCALNAME);
            message.addReceiver(receiverID);    
            receiverID = new AID("A*", AID.ISLOCALNAME);
            message.addReceiver(receiverID);              
            try 
            {
                message.setContentObject(lsa);
                myAgent.send(message);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(AgentLabirint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public int getStartX()
        {
            int x = 0;
            for(int row = 0; row < labirint.length; row++)
            {
                for(int col = 0; col < labirint[0].length; col++)
                {
                    if(labirint[row][col] == 2)
                    {
                        return col;
                    }
                }
            }
            return x;
        }
        public int getStartY()
        {
            int x = 0;
            for(int row = 0; row < labirint.length; row++)
            {
                for(int col = 0; col < labirint[0].length; col++)
                {
                    if(labirint[row][col] == 2)
                    {
                        return row;
                    }
                }
            }
            return x;
        }             
    }
    
    
    
    private class AsteaptaRaspuns extends CyclicBehaviour
    {
        public AsteaptaRaspuns()
        {
            
        }    
        public void action()
        {
            ACLMessage message = myAgent.receive();
            String SolutieAleasa ="";
            if(message != null)
            {
                try 
                {
                    if(message.getSender().getLocalName().equals("AMSAgent"))
                    {
                        if(message.getContent().equals("DFS Stopped"))
                        {
                            DFSRunning = false;
                        }
                        else if(message.getContent().equals("BFS Stopped"))
                        {
                            BFSRunning = false;
                        }
                        else if(message.getContent().equals("DFS Started"))
                        {
                            DFSRunning = true;
                        }
                        else if(message.getContent().equals("BFS Started"))
                        {
                            BFSRunning = true;
                        } 
                        else if(message.getContent().equals("A* Started"))
                        {
                            AStarRunning = true;
                        }
                        else if(message.getContent().equals("A* Stopped"))
                        {
                            AStarRunning = false;
                        }   
                        
                    }
                    else
                    {
                        if(message.getSender().getLocalName().equals("DFS"))
                        {
                            DFSNodes = (ArrayList<MoveNode>) message.getContentObject();
                        }
                        if(message.getSender().getLocalName().equals("BFS"))
                        {
                            BFSNodes = (ArrayList<MoveNode>) message.getContentObject();
                        }  
                        if(message.getSender().getLocalName().equals("A*"))
                        {
                            AStarNodes = (ArrayList<MoveNode>) message.getContentObject();
                        }                        

                        if((DFSNodes.size() > 0 || DFSRunning==false) && (BFSNodes.size() > 0 || BFSRunning==false) && (AStarNodes.size() > 0 || AStarRunning==false))
                        {
                            if(VreauPasiPutini)
                            {
                                if((DFSNodes.size() > BFSNodes.size()) && BFSRunning == true)
                                {
                                    SolveNodes = BFSNodes;
                                    SolutieAleasa = "BFS";
                                }
                                else if((DFSNodes.size() < BFSNodes.size()) && DFSRunning==true)
                                {
                                    SolveNodes = DFSNodes;
                                    SolutieAleasa = "DFS";
                                }
                                else
                                {
                                    if(BFSRunning==true)
                                    {
                                        SolveNodes = BFSNodes;
                                        SolutieAleasa = "BFS";
                                    }
                                    else if(BFSRunning == false)
                                    {
                                        SolveNodes = DFSNodes;
                                        SolutieAleasa = "DFS";                                        
                                    }
                                }
                                
                                if(((AStarNodes.size() < SolveNodes.size()) || SolveNodes.size()==0) && AStarRunning==true)
                                {
                                    SolveNodes = AStarNodes;
                                    SolutieAleasa = "A*";
                                }
                                else if((AStarNodes.size() == SolveNodes.size()) && AStarRunning==true)
                                {
                                    boolean Random;
                                    Random = Math.random() < 0.5;
                                    if(Random)
                                    {
                                        SolveNodes = AStarNodes;
                                        SolutieAleasa = "A*";                                        
                                    }
                                }
                            }
                            else
                            {
                                if(DFSRunning == false && BFSRunning == true && AStarRunning==false)
                                {
                                    SolveNodes = BFSNodes;
                                    SolutieAleasa = "BFS";                                
                                }
                                else if(DFSRunning == true && BFSRunning == false && AStarRunning==false)
                                {
                                    SolveNodes = DFSNodes;
                                    SolutieAleasa = "DFS";                                
                                }
                                else if(DFSRunning == false && BFSRunning == false && AStarRunning==true)
                                {
                                    SolveNodes = AStarNodes;
                                    SolutieAleasa = "A*";                                      
                                }
                                else if(DFSRunning == false && BFSRunning == true && AStarRunning==true)
                                {
                                    boolean Random = Math.random() < 0.5;
                                    if(Random)
                                    {
                                        SolveNodes = AStarNodes;
                                        SolutieAleasa = "A*";                                        
                                    }
                                    else
                                    {
                                        SolveNodes = BFSNodes;
                                        SolutieAleasa = "BFS";                                         
                                    }
                                }
                                else if(DFSRunning == true && BFSRunning == false && AStarRunning==true)
                                {
                                    boolean Random = Math.random() < 0.5;
                                    if(Random)
                                    {
                                        SolveNodes = AStarNodes;
                                        SolutieAleasa = "A*";                                        
                                    }
                                    else
                                    {
                                        SolveNodes = DFSNodes;
                                        SolutieAleasa = "DFS";                                         
                                    }                                    
                                }
                                else if(DFSRunning == true && BFSRunning == true && AStarRunning==false)
                                {
                                    boolean Random = Math.random() < 0.5;
                                    if(Random)
                                    {
                                        SolveNodes = BFSNodes;
                                        SolutieAleasa = "BFS";                                      
                                    }
                                    else
                                    {
                                        SolveNodes = DFSNodes;
                                        SolutieAleasa = "DFS";                                         
                                    }                                    
                                }                                 
                                else if(DFSRunning == true && BFSRunning == true && AStarRunning==true)
                                {
                                    int Random;
                                    Random = (int)Math.floor(Math.random()*2);
                                    if(Random==0)
                                    {
                                        SolveNodes = DFSNodes;
                                        SolutieAleasa = "DFS";
                                    }
                                    else if(Random==1)
                                    {
                                        SolveNodes = BFSNodes;
                                        SolutieAleasa = "BFS";
                                    }
                                    else if(Random==2)
                                    {
                                        SolveNodes = AStarNodes;
                                        SolutieAleasa = "A*";                                        
                                    }
                                }
                            }
                            if(GuiRunning==false)
                            {
                                gui = new AgentLabirintGUI(labirint, SolveNodes);
                                gui.setVisible(true);
                                CanMove = true;
                                GuiRunning = true;
                                gui.addText("\nLabirint folosit: "+GenerateLabirint.getGeneratedLabNumber());
                                if(VreauPasiPutini)
                                {
                                    gui.addText("Doreste sa iasa din labirint cat mai repede.\n");
                                }
                                else
                                {
                                    gui.addText("Nu il intereseaza cat de repede iese din labirint.\n");
                                }                               
                                if(BFSRunning == true)
                                gui.addText("BFS a raspuns: "+BFSNodes.size()+" pasi.");
                                else
                                gui.addText("BFS nu a raspuns.");
                                if(DFSRunning == true)
                                gui.addText("DFS a raspuns: "+DFSNodes.size()+" pasi.");
                                else
                                gui.addText("DFS nu a raspuns.");
                                if(AStarRunning == true)
                                gui.addText("A* a raspuns: "+AStarNodes.size()+" pasi.");
                                else
                                gui.addText("A* nu a raspuns.");    
                                gui.addText("Solutie aleasa: "+SolutieAleasa+" ("+SolveNodes.size()+" pasi)");
                            }
                        }
                    }
                } 
                catch (UnreadableException ex) 
                {
                    Logger.getLogger(AgentLabirint.class.getName()).log(Level.SEVERE, null, ex);
                }       
            }
            else
            {
                block();
            }
        }
    }    
    
}
