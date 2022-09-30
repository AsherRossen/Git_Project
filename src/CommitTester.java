import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Date;

public class CommitTester {
	
	public static void main(String[] args) throws IOException
	{
		Index i = new Index();
		i.initialize();
		i.add("textfile.txt");
		i.add("textfile2.txt");
		Commit c = new Commit("Commit1","1st A", null);
		System.out.println(c.getTree().getName());
		i.add("textfile3.txt");
		Commit commy = new Commit ("Commit2", "2nd A", c);
		commy.writeFile();
		System.out.println(commy.getTree().getName());
//		System.out.println(c.commitSHA1());
//		c.writeFile();
		
		
	}

}
