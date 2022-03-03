import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.ACLMessage;

public class AMSAgent extends Agent
{
    private boolean DFSRunning=true,BFSRunning=true,AStarRunning=true;
    private boolean DFSFound=false,BFSFound=false,AStarFound=false;
    Agent AMS;
    protected void setup()
    {
        addBehaviour(new VerificaAgenti());
        AMS=this;
    } 
    private class VerificaAgenti extends CyclicBehaviour
    {
        public void action()
        {
            AMSAgentDescription [] agents = null;
            ACLMessage message = new ACLMessage();
            AID receiverID = new AID("Labirint", AID.ISLOCALNAME);
            message.addReceiver(receiverID);
            try 
            {
                SearchConstraints c = new SearchConstraints();
                c.setMaxResults (new Long(-1));
                agents = AMSService.search( AMS, new AMSAgentDescription (), c );
            }
            catch (Exception e) 
            {
                System.out.println( "Problem searching AMS: " + e );
                e.printStackTrace();
            }
            AID myID = getAID();
            for (int i=0; i<agents.length;i++)
            {
                AID agentID = agents[i].getName();    
                if(agents[i].getName().getLocalName().equals("DFS"))
                {
                    DFSFound = true;
                }
                if(agents[i].getName().getLocalName().equals("BFS"))
                {
                    BFSFound = true; 
                }
                if(agents[i].getName().getLocalName().equals("A*"))
                {
                    AStarFound = true; 
                }                
            }
            
            if(DFSFound == true && DFSRunning == false)
            {
                DFSRunning = true;
                message.setContent("DFS Started");
                myAgent.send(message); 
            }
            if(BFSFound == true && BFSRunning == false)
            {
                BFSRunning = true;
                message.setContent("BFS Started");
                myAgent.send(message); 
            }
            if(AStarFound == true && AStarRunning == false)
            {
                AStarRunning = true;
                message.setContent("A* Started");
                myAgent.send(message); 
            }             

            
            if(DFSFound == false && DFSRunning == true)
            {
                DFSRunning = false;
                message.setContent("DFS Stopped");
                myAgent.send(message);                
            }
            if(BFSFound == false && BFSRunning == true)
            {
                BFSRunning = false;
                message.setContent("BFS Stopped");
                myAgent.send(message); 
            }
            if(AStarFound == false && AStarRunning == true)
            {
                AStarRunning = false;
                message.setContent("A* Stopped");
                myAgent.send(message); 
                
            }            
            DFSFound=false;BFSFound=false;AStarFound=false;
        }
    }
}
