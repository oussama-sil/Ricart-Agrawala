import java.net.Socket;

public class ThreadEc extends Thread{
     Process parent;
    public ThreadEc(Process parent){
        this.parent=parent;
    }
    public void run(){
        try{
                while(true){
                    // Accepter une connection d'un client
                    Socket clientSocket = parent.pSocket.accept();
                    // Création du input stream pour reception des messages de la part du client
                    handler thandle=new handler(parent,clientSocket);
                    thandle.start();
                } 
        }catch(Exception e){e.printStackTrace();}
    }
}
