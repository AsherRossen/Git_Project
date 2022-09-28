import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Date;

public class CommitTester {
	
	public static void main(String[] args) throws IOException
	{
		Commit c = new Commit("Testing Commit","Author", null);
		System.out.println(c.createArrayListForTree());
//		System.out.println(c.commitSHA1());
//		c.writeFile();
	}

}
