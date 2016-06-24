import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CountLines {
	
	
	public int sum = 0;
	public String path = "";
	public String extension = "";
	
	
	public CountLines(String path, String ext, boolean isRecursive) throws IOException{
		
		this.path = path;
		extension = ext;
		
		
		startCountingLines(path, extension ,isRecursive);

//		displayNamesFiles(files);
		
		System.out.println("\n ********************************** \n  NB lines total ("+ext+"): "+sum +"\n ************************");
	}
	
	public void startCountingLines(String path, String ext, boolean isRecursive) throws IOException{
		File[] files = listFiles(path);
		countLinesFiles(files, path, ext, isRecursive);
	}
	
	
	public void countLinesFiles(File[] files, String path, String ext, boolean isRecursive) throws IOException{
		if(files != null){
			for(int i=0; i<files.length; i++){
				if(files[i].getName().endsWith(ext)){
					int nbLines = countLineForAFile2(files[i].getName(), path);
					sum += nbLines;
					System.out.println("nbline for file : "+files[i].getName() + " : "+ nbLines);
				}
				else{
					if(isRecursive){
						startCountingLines(path+"\\"+files[i].getName(), ext, isRecursive);
					}
				}
			}
		}
		
		
	}
	
//	public int countLineForAFile(String filename, String path) throws IOException {
//		int sum = 0;
//		try (BufferedReader br = new BufferedReader(new FileReader(path+"\\"+filename))) {
//		    String line;
//		    while ((line = br.readLine()) != null) {
//		       // process the line.
//		    	sum++;
//		    }
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return sum;
//	}
	
	
	public int countLineForAFile2(String filename, String path) throws IOException {
		int sum=0;
		try{
			InputStream ips=new FileInputStream(path+"\\"+filename); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				

		    	sum++;
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return sum;
		
	}
	
	
	public File[] listFiles(String directoryPath){ 
		File[] files = null; 
		File directoryToScan = new File(directoryPath); 
		files = directoryToScan.listFiles();
		
		return files; 
	} 
	
	public void displayNamesFiles(File[] files){
		for(int i=0; i<files.length; i++){
			if(files[i].getName().endsWith(extension)){
				System.out.println(files[i].getName());
			}
		}
	}
	
	
	
	public static void main(String[] args) throws IOException{		

		boolean recursive = true;
		
		/*new CountLines("D:\\Users\\fsauce\\workspace\\webHolidays", ".js", recursive);
		new CountLines("D:\\Users\\fsauce\\workspace\\webHolidays", ".jsp", recursive);
		new CountLines("D:\\Users\\fsauce\\workspace\\webHolidays", ".css", recursive);
		new CountLines("D:\\Users\\fsauce\\workspace\\webHolidays", ".java", recursive);*/

		int sumTotal=0;

		CountLines cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\webapp\\js", ".js", recursive); //7365
		sumTotal += cl.sum;
		cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\webapp\\partials", ".html", recursive); //4373
		sumTotal += cl.sum;
		cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\webapp", ".html", false); //635
		sumTotal += cl.sum;
		cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\webapp\\css", "myCSS.css", recursive); //3224
		sumTotal += cl.sum;
		cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\resources", ".sql", recursive); //1367
		sumTotal += cl.sum;
		cl = new CountLines("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\java", ".java", recursive); //6264
		sumTotal += cl.sum;

		System.out.println("-------------------------------------------------------------------------------------------------------\n SUM TOTAL : "+ sumTotal+" lines!!");
	}

}
