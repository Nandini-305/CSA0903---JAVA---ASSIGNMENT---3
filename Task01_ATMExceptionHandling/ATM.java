import java.io.*;
import java.util.*;
public class ATM {
 static final double BALANCE=5000;
 static void withdraw(String account,double amount) throws IOException {
  if(account.isBlank()) throw new IllegalArgumentException("Account required");
  if(amount<=0) throw new IllegalArgumentException("Amount must be positive");
  if(amount>BALANCE) throw new IllegalStateException("Insufficient balance");
  try(FileReader r=new FileReader("card-reader.txt")){r.read();}
  System.out.println("Withdrawal successful: "+account+" amount="+amount);
 }
 public static void main(String[] args){
  Scanner s=new Scanner(System.in);
  try { System.out.print("Account: "); String a=s.nextLine(); System.out.print("Amount: ");
   double v=Double.parseDouble(s.nextLine()); withdraw(a,v);
  } catch(NumberFormatException e){System.out.println("Invalid numeric input");}
    catch(IllegalArgumentException|IllegalStateException e){System.out.println("Rejected: "+e.getMessage());}
    catch(FileNotFoundException e){System.out.println("Card reader file missing");}
    catch(IOException e){System.out.println("I/O error: "+e.getMessage());}
    finally{System.out.println("ATM session closed (finally executed).");}
 }
}
