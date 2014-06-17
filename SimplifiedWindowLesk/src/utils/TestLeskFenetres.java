package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestLeskFenetres {

	// Exécution sur Dellyoug
	
	// Exécution sur Brahms
	private final static String base = "/home/martinu/fenetres/";
	private final static String java = "/usr/bin/java";
	private final static String configFolder = base + "LeskFenetres/config_brahms/";
	
	// Paramètres communs
	private final static String answerFolder = base + "LeskFenetres/answers/";
	
	// EXA � LeskFenetres execution of all configurations with Donna � es
	public static void executions() {
		File[] configFiles = (new File(configFolder)).listFiles();
		Arrays.sort(configFiles);
		int nbConfig = configFiles.length;
		
		for (int i = 0; i < nbConfig; i++) {
			try {
				System.err.println("\n\nConfiguration " + (i+1) + "/" + nbConfig + " : " + configFolder + configFiles[i].getName());
				String[] command = new String[] {
						java,
						"-Dfile.encoding=UTF-8",
						"-classpath",
						base + "LeskFenetres/bin:" + base + "LeskFenetres/edu.mit.jwi_2.1.5.jar",
						"lesk.LeskFenetres",
						configFolder + configFiles[i].getName()
				};
				
				Process leskFenetres = Runtime.getRuntime().exec(command);
	            
				BufferedReader input = new BufferedReader(new InputStreamReader(leskFenetres.getErrorStream()));
	            String line = null;
	            while((line = input.readLine()) != null) {
	                System.err.println(line);
	            }
	            
	            input = new BufferedReader(new InputStreamReader(leskFenetres.getInputStream()));
	            line = null;
	            while((line = input.readLine()) != null) {
	                System.out.println(line);
	            }
	
	            int exitValue = leskFenetres.waitFor();
				if (exitValue != 0) {
					System.err.println("Erreur leskFenetres (valeur de retour : " + exitValue + ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Évaluation de tous les résultats
	public static void evaluations() {
		File[] answerFiles = (new File(answerFolder)).listFiles();
		
		for (int i = 0; i < answerFiles.length; i++) {
			try {
				String[] command = {
						"perl",
						"scorer.pl",
						answerFolder + answerFiles[i].getName()
				};
	
				System.out.println("\n-------------------------------");
				// Exécution du scorer, supposé se trouvé dans le dossier ci-dessous
				Process evaluation = Runtime.getRuntime().exec(command, null, new File(base + "semeval-task7/key"));
				
	            BufferedReader input = new BufferedReader(new InputStreamReader(evaluation.getInputStream()));
	            String line = null;
	            while((line = input.readLine()) != null) {
	                System.out.println(line);
	            }
	            
	            input = new BufferedReader(new InputStreamReader(evaluation.getErrorStream()));
	            line = null;
	            while((line = input.readLine()) != null) {
	                System.err.println(line);
	            }
	            
	            int exitValue = evaluation.waitFor();
				if (exitValue != 0) {
					System.err.println("Erreur evaluation (valeur de retour : " + exitValue + ")");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		executions();
		evaluations();
	}
}
