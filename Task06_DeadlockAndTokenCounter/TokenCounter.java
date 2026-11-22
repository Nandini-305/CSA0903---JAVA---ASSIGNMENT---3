public class TokenCounter {
 private final int[] buffer=new int[5];private int count=0,in=0,out=0;
 public synchronized void put(int token)throws InterruptedException{
  while(count==buffer.length)wait();buffer[in]=token;in=(in+1)%buffer.length;count++;notifyAll();
 }
 public synchronized int take()throws InterruptedException{
  while(count==0)wait();int v=buffer[out];out=(out+1)%buffer.length;count--;notifyAll();return v;
 }
 static class Account{final int id;int balance;Account(int i,int b){id=i;balance=b;}}
 static void transfer(Account a,Account b,int amount){
  Account first=a.id<b.id?a:b,second=a.id<b.id?b:a;
  synchronized(first){synchronized(second){if(a.balance>=amount){a.balance-=amount;b.balance+=amount;}}}
 }
 public static void main(String[] args)throws InterruptedException{
  TokenCounter c=new TokenCounter();int[] sums=new int[2];
  Thread cook=new Thread(()->{try{for(int i=1;i<=20;i++)c.put(i);}catch(InterruptedException e){Thread.currentThread().interrupt();}});
  Thread[] students=new Thread[2];
  for(int i=0;i<2;i++){final int id=i;students[i]=new Thread(()->{try{for(int k=0;k<10;k++)sums[id]+=c.take();}catch(InterruptedException e){Thread.currentThread().interrupt();}});}
  cook.start();for(Thread t:students)t.start();cook.join();for(Thread t:students)t.join();
  System.out.println("Sum of tokens collected: "+(sums[0]+sums[1])+" (expected 210)");
  Account a=new Account(1,1000),b=new Account(2,1000);
  Thread x=new Thread(()->transfer(a,b,100)),y=new Thread(()->transfer(b,a,150));x.start();y.start();x.join();y.join();
  System.out.println("Total wallet balance: "+(a.balance+b.balance));
 }
}
