import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

public class Process {
    public GUI interfce;
    public  long osn;
    public   long hsn=0;
    public  boolean[] repDiffer;
    public  ServerSocket pSocket;
    public  int nbrepattendues;
    public  boolean Scdemandee=false;

    public Semaphore osnSemaphore;
    public Semaphore repDiffSemaphore;
    public Semaphore scSemaphore;
    public Semaphore hsnSemaphore;
    public Semaphore nbrepattenduesSemaphore;
    
    public Process(int port,int nbprocess,GUI interfGui){
        try{
            pSocket=new ServerSocket(port);
            repDiffer = new boolean[nbprocess];
            for(int i=0;i<nbprocess;i++){
                repDiffer[i]=false;
            }
            this.interfce=interfGui;
            osnSemaphore=new Semaphore(1);
            repDiffSemaphore= new Semaphore(1);
            scSemaphore= new Semaphore(1);
            hsnSemaphore = new Semaphore(1);
            nbrepattenduesSemaphore=new Semaphore(1);

            ThreadSc SC=new ThreadSc(this);
            ThreadEc EC=new ThreadEc(this);
            
            SC.start();
            EC.start();
        }catch(IOException e){System.out.println("Processus : "+port+" n'est pas cree");e.printStackTrace();}
        
    }
     
    public int getNbrepAtt(){
        return nbrepattendues;
    }
    public void decNbrepAtt(){
        nbrepattendues--;
    }
    public void setRepdifTrue(int i){
        repDiffer[i]=true;
    }
    public int getID(){
        return pSocket.getLocalPort()-50000;
    }
    public boolean getSCdem(){
        return Scdemandee;
    }
    public long getHSN(){
        return hsn;
    }
    public void setHSN(long hsn){
        this.hsn=hsn;
    }
    public long getOSN(){
        return osn;
    }
}
