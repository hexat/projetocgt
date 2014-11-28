package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("/view/Ferramenta.fxml"));
			Scene scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
//		try {
////		String[] array = {"../gradlew.bat"};
////		Process p = Runtime.getRuntime().exec(array);
//		List<String> commands = new ArrayList<String>();
//		commands.add("gradlew.bat");
//		commands.add(" android:tasks");
//		 ProcessBuilder pb = new ProcessBuilder(commands);
//		 System.out.println("lksajdfla");
//		 pb.directory(new File("C:/Users/wendel/workspace/projetocgt"));
//		 System.out.println(pb.directory().getAbsolutePath());
//	    Process p = pb.start();
//	   // Process p = new ProcessBuilder("../gradlew.bat", "assembleDebug").start();
//		
//		System.out.println("ok");
//		new Thread() {
//			public void run() {
//				String line;
//				try {
//					BufferedReader reader;
//					reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
//
//					while((line = reader.readLine())!=null){
//						System.out.println("ok");
//						System.out.println(line);
//					}
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}  
//				System.out.println("ok");
//			}
//		}.start();
//		} catch (Exception e) {}
	}
}
