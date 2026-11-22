import java.nio.file.*;import java.util.*;import java.util.concurrent.*;
public class ResultProcessor {
 static class Result {boolean ok;String name,msg;Result(boolean o,String n,String m){ok=o;name=n;msg=m;}}
 static Callable<Result> job(Path p){return ()->{try{
  String v=Files.readString(p).trim();if(!v.matches("\\\\d{1,3}"))throw new IllegalArgumentException("Corrupt score");
  int n=Integer.parseInt(v);if(n>100)throw new IllegalArgumentException("Score out of range");
  return new Result(true,p.getFileName().toString(),"score="+n);
 }catch(Exception e){return new Result(false,p.getFileName().toString(),e.getMessage());}};}
 static void run(Path dir,int size)throws Exception{
  ExecutorService pool=Executors.newFixedThreadPool(size);List<Path> paths=new ArrayList<>();
  try(DirectoryStream<Path> ds=Files.newDirectoryStream(dir,"*.txt")){for(Path p:ds)paths.add(p);}
  paths.add(dir.resolve("missing.txt"));List<Future<Result>> fs=new ArrayList<>();long start=System.nanoTime();
  for(Path p:paths)fs.add(pool.submit(job(p)));int ok=0,bad=0;
  for(Future<Result> f:fs){Result r=f.get();if(r.ok)ok++;else bad++;}
  long elapsed=System.nanoTime()-start;pool.shutdown();
  System.out.printf("Pool=%d success=%d failures=%d elapsed=%d ns%n",size,ok,bad,elapsed);
 }
 public static void main(String[] args)throws Exception{
  Path dir=Path.of("scores");Files.createDirectories(dir);
  for(int i=1;i<=10;i++)Files.writeString(dir.resolve("student"+i+".txt"),i==4?"corrupt":""+(50+i));
  for(int n:new int[]{1,2,4,8})run(dir,n);
 }
}
