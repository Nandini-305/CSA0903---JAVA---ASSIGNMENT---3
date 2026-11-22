public class ScannerStations {
 static class Station extends Thread {
  int scripts; Station(String name,int n){super(name);scripts=n;}
  public void run(){try{Thread.sleep(100+(long)(Math.random()*300));System.out.println(getName()+" scanned "+scripts);}
   catch(InterruptedException e){Thread.currentThread().interrupt();}}
 }
 public static void main(String[] args)throws InterruptedException{
  int[] n={25,30,20,35};Thread[] ts=new Thread[4];
  for(int i=0;i<4;i++){ts[i]=new Station("Station-"+(i+1),n[i]);ts[i].start();}
  for(Thread t:ts)System.out.println(t.getName()+" alive="+t.isAlive());
  for(Thread t:ts)t.join();
  System.out.println("All stations completed. Total scripts scanned: 110");
 }
}
