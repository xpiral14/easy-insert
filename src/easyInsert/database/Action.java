package easyInsert.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Action {
	
	protected final String baseSQLpath = System.getProperty("user.dir");

	public void mountInsertQuery(String path, String csvDivisor, String tableName) {
		String line = "";
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		File insertFile = null;
		File csvFile = null;
		
		String baseSQLInsertQuery;
		
		try {
			//get file based on path provided by client
			csvFile = new File(path);
			br = new BufferedReader(new FileReader(csvFile));
						
			// Insert field names in the base query based on the first Line of file.
			if((line = br.readLine()) != null) {
				
				//make file in a outside project folder
				Path insertSqlFilepath = Paths.get(this.baseSQLpath + csvFile.getName() + ".sql");
				insertFile = new File(insertSqlFilepath.toString());
				System.out.println(this.baseSQLpath);
				if(insertFile.createNewFile()) {
					System.out.println("Arquivo sql criado em " +insertFile.getAbsolutePath()); 
				}
				
				bw = new BufferedWriter(new FileWriter(insertFile));
				
				String[] fields = line.split(csvDivisor);
				String[] fieldsData = null;
				baseSQLInsertQuery = "INSERT INTO " + tableName + " ( " + String.join(", ", fields) + ") VALUES ";
				String insertValues = "";
				
				// Insert the fields values on the base query from the second line.
				while ((line = br.readLine()) != null) {
					fieldsData = line.split(csvDivisor);
					
					for(int i = 0; i < fieldsData.length; i++) {
						try {
							if(!fieldsData[i].matches("^(\\d+(\\.\\d+)?)") && !fieldsData[i].matches("^\".{1,}\"$")){
								System.out.println(fieldsData[i]);
								fieldsData[i] = "'"+fieldsData[i] + "'";
							}
						}catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					insertValues += baseSQLInsertQuery + " ( " + String.join(", ", fieldsData) + ");\n";
				}
				bw.append(insertValues);
				br.close();
				bw.close();
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Parece que o arquivo csv nao está correto. Corrija-o e tente novamente.");
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
		}
	}
}
