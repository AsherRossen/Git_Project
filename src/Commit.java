import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import java.util.ArrayList;
import java.util.Date;

public class Commit {
	
	public String summary;
	public String author;
	public String date;
	private Tree trunk;
	private File head;
	
	Commit previous;
	Commit next;
	
	public Commit(String summaryValue, String authorName, Commit parent) throws IOException
	{
		summary = summaryValue;
		author = authorName;
		date = getDate();
		previous = parent;
		next = null;
		trunk = new Tree (createArrayListForTree());
		
		head = new File ("HEAD");
		head.delete();
		head.createNewFile();
		PrintWriter pw = new PrintWriter ("HEAD");
		pw.print(this.commitSHA1());
		pw.close();
	}
	
	public void delete (Index ind, String fn) throws IOException {
		ind.delete(fn);
		File f = new File ("index");
		f.delete();
		f.createNewFile();
	}
	
	public Tree getTree() {
		return trunk;
	}
	
	public ArrayList<String> createArrayListForTree() throws IOException {
		ArrayList <String> arr = new ArrayList<String>();
		File f =  new File ("index");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		while (reader.ready()) {
			String s = reader.readLine();
			int i = s.indexOf(" :");
			String fileName = s.substring(0,i);
			String sha = s.substring(i+3);
			s="blob : "+sha+" "+fileName;
			arr.add(s);
		}
		reader.close();
		if (previous!=null) {
			String s = "tree : "+previous.getTree().getName();
			arr.add(s);
		}
		f.delete();
		f.createNewFile();
		
		return arr;
	}
	
	public String getDate()
	{
		Date d = new Date();
		return d.toString();
	}
	
	public void writeFile()
	{
		String toWrite = "./objects/"+trunk.getName() + "\n";
		if(previous != null)
			toWrite += "./objects/" + previous.commitSHA1();
		toWrite += "\n";
		if(next != null)
			toWrite += "./objects/" + next.commitSHA1();
		toWrite += "\n";
		toWrite += author + "\n";
		toWrite += date + "\n";
		toWrite += summary + "\n";
		
		Path p = Paths.get("./objects/" + commitSHA1());
        try {
            Files.writeString(p, toWrite, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public String commitSHA1()
	{
		String info = trunk.getName() + "\n";
		if(previous != null)
		{
			info += previous + "\n";
		}
		info += author + "\n";
		info += date + "\n";
		info += summary + "\n";
		return generateSHA1(info);
	}
	
	public String generateSHA1(String input)
	{
		String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(input.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}
	
	public static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}