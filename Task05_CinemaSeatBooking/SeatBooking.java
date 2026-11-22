import java.util.concurrent.CountDownLatch;
public class SeatBooking {
 private final boolean[] booked; private int bookingsMade=0;
 SeatBooking(int n){booked=new boolean[n];}
 synchronized boolean book(int seat){
  if(!booked[seat]){booked[seat]=true;bookingsMade++;return true;}return false;
 }
 public static void main(String[] args)throws InterruptedException{
  SeatBooking screen=new SeatBooking(10);Thread[] users=new Thread[100];
  CountDownLatch ready=new CountDownLatch(100),go=new CountDownLatch(1);
  for(int i=0;i<100;i++){final int seat=i%10;users[i]=new Thread(()->{
   ready.countDown();try{go.await();}catch(InterruptedException e){Thread.currentThread().interrupt();return;}
   screen.book(seat);
  });users[i].start();}
  ready.await();go.countDown();for(Thread t:users)t.join();
  System.out.println("Seats sold: "+screen.bookingsMade);
 }
}
