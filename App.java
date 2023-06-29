public class App {
    static  public void main(String args[]){
        int nbprocess=10;
        GUI appInter=new GUI(nbprocess);
        for(int i=0;i<nbprocess;i++){
            appInter.addProcess(i+1, i+50001,"Attente");
            Process process=new Process(i+50001, nbprocess,appInter);
        }
    }
}
