package fileControl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class Annex {
	//Pass the folder name to annex (Arg Folder). This should be called on startup in main.
	//Returns a 1 for success, and a negative 1 for error. Errors will re-prompt user to select folder.
	public static int RunAnnex(String Folder) { 
		Path targetFolder = Paths.get(Folder);
		
		Map<String, String> fileMap = new HashMap<>(); //God, I love hashmaps! <Songname, path>
		
		try (Stream<Path> stream = Files.walk(targetFolder)) {
            stream.filter(Files::isRegularFile).forEach(
        		path -> { //This looks scary, I promise you it's not. Essentially filter.forEach.Getmethedamnpath
        			fileMap.put(path.getFileName().toString(), path.toAbsolutePath().toString());
        		}
        	);
            fileMap.forEach((name, path) -> System.out.println(name + " -> " + path));
            return 1;
			
		} catch (IOException e) {
			System.err.println("Error! " + targetFolder.toString() + " can't be found. How did the user fucking manage that?");
			return -1;
		}
	}
}
