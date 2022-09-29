import java.io.IOException;

public class IndexTester {

	public static void main(String[] args) throws IOException
	{
		
		Index index = new Index();
		index.initialize();
		
		index.add("textfile.txt");
		index.add("textfile2.txt");
		index.add("textfile3.txt");
		
		//index.remove("textfile3.txt");
	}

}
