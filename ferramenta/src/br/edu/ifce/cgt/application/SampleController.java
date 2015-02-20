package br.edu.ifce.cgt.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class SampleController  implements Initializable{
	@FXML private  ListView<String> listaConfig;
	@FXML private TextField txtProcurar;
	private Path projeto =  Paths.get("data/imagens/"); // pasta onde todos os arquivos do projeto ser�o salvos
	//private Path resources =  Paths.get(projeto.toString()+"/resources"); //parte da estrutura da pasta. Possui os recursos usados, como sons e imagens.
	private static final ObservableList<String> obs = FXCollections.observableArrayList("Cen�rio, Personagem, Inimigos, Sons");
	
	
	

//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//
//		
//		
//	}
	@FXML public void gerar() throws IOException{
		
		String line;
//		String[] array = {"../gradlew.bat"};
//		Process p = Runtime.getRuntime().exec(array);
		List<String> commands = new ArrayList<String>();
		commands.add("../gradlew.bat");
		commands.add(" android:tasks");
		 ProcessBuilder pb = new ProcessBuilder(commands);

	    Process p = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
		while((line = reader.readLine())!=null){
			System.out.println(line);
		}
		
		
//    CGTFileZip zip = new CGTFileZip("C:/Users/wendel/teste.cgt", pastaParaSerCompactada.toString());
//    zip.zipar();
	
		
		
	}
	
	
	@FXML public void salvar() throws IOException{
		
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o local para salvar o arquivo .cgt");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo CGT (*.cgt)", "*.cgt");
		fileChooser.getExtensionFilters().add(extFilter);
		File chosenFile = fileChooser.showSaveDialog(null);
		if(chosenFile != null){
			CGTFileZip zip = new CGTFileZip(chosenFile.getPath(),
					"cgt");
			zip.zipar();
			
		}
	}
	
	@FXML public void procurar() throws IOException{
		
		FileChooser fileChooser = new FileChooser();							
		fileChooser.setTitle("Selecione o Background");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		//fileChooser.showOpenDialog(stage);
		
		//Choose the file
		File chosenFile = fileChooser.showOpenDialog(null);
		//Make sure a file was selected, if not return default
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		} else {
		    //default return value
		    path = null;
		}
		txtProcurar.setText(path);
		new File(projeto.toString()).mkdirs();
		File imagem = new File(projeto.toString()+"/background.png");
		copyFile(chosenFile, imagem );
		// gerar config.cgt
	//	gerarConfig();
		new File("../android/assets/data").mkdirs();
		CGTFileZip zip = new CGTFileZip("projeto", "data");
		zip.zipar();
		File source = new File("projeto.pcgt");
		File destination = new File("../android/assets/config.cgt");
		copyFile(source, destination);
		zip.unzip(destination.toString(), "../android/assets/data/");
		
		destination.delete();
		
		
	}
	
	@FXML public void novoProjeto(){
		
	}
	
	
	// copy File
	public static void copyFile(File source, File destination) throws IOException {  
	     if (destination.exists())  
	         destination.delete();  
	  
	     FileChannel sourceChannel = null;  
	     FileChannel destinationChannel = null;  
	  
	     try {  
	         sourceChannel = new FileInputStream(source).getChannel();  
	         destinationChannel = new FileOutputStream(destination).getChannel();  
	         sourceChannel.transferTo(0, sourceChannel.size(),  
	                 destinationChannel);  
	     } finally {  
	         if (sourceChannel != null && sourceChannel.isOpen())  
	             sourceChannel.close();  
	         if (destinationChannel != null && destinationChannel.isOpen())  
	             destinationChannel.close();  
	    }  
	}

//	public void gerarConfig(){
//		
//		ConfigureGame game = new ConfigureGame();
//		
//		
//	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaConfig = new ListView<String>(obs);
		 listaConfig.setEditable(true);
		 listaConfig.setItems(obs);
		
	}
}