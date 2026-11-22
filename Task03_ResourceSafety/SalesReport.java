import java.io.*;
import java.nio.file.*;
public class SalesReport {
 static void generate(String[] files,String output) throws IOException {
  try(BufferedWriter w=Files.newBufferedWriter(Path.of(output))){
   for(String f:files) try(BufferedReader r=Files.newBufferedReader(Path.of(f))){
    String line; while((line=r.readLine())!=null){w.write(line);w.newLine();}
   }
  }
 }
 public static void main(String[] args){
  String[] files={"store1.txt","store2.txt"};
  try{generate(files,"summary.txt");System.out.println(Files.readString(Path.of("summary.txt")));}
  catch(IOException e){System.err.println("Failed store file: "+e.getMessage());e.printStackTrace();}
 }
}
