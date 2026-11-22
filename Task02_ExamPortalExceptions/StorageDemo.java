class ExamException extends Exception {
 ExamException(String m){super(m);} ExamException(String m,Throwable c){super(m,c);}
}
class SubmissionStorageException extends ExamException {
 SubmissionStorageException(String m,Throwable c){super(m,c);}
}
class Storage {
 void save(String file,long kb) throws SubmissionStorageException {
  try {if(kb>500) throw new java.io.IOException("Disk quota exceeded"); System.out.println("Saved "+file);}
  catch(java.io.IOException e){throw new SubmissionStorageException("Could not store "+file,e);}
 }
}
public class StorageDemo {
 public static void main(String[] args){
  Storage s=new Storage();
  try{s.save("answer01.pdf",120);s.save("answer02.pdf",800);}
  catch(ExamException e){System.out.println("Error: "+e.getMessage());System.out.println("Cause: "+e.getCause().getMessage());}
 }
}
