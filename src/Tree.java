import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tree {
	private String contents = ""; 
	private String hashedContents;
	public Tree (ArrayList <String> list) throws IOException {
		for (int i = 0; i < list.size() - 1; i++) {
			contents += list.get(i); 
			contents += "\n";
		}
		contents += list.get(list.size()-1);
		
		hashedContents = genHash(contents);
		
		File f = new File ("./objects/" + hashedContents);
		
		FileWriter fw = new FileWriter(f);
		for (int i = 0; i < list.size(); i++) {
			fw.write(list.get(i)); 
			fw.write("\n");
		}
		fw.close();
	}
	
	public String getName () {
		return "./objects/"+hashedContents;
	}
	
	public String genHash (String input) {
		try {
	           
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] messageDigest = md.digest(input.getBytes());
 

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
 
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
 
            return hashtext;
	     
	     }
		catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
}
