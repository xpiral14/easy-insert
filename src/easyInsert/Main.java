package easyInsert;

import java.util.Scanner;

import easyInsert.database.Action;

public class Main {
	public static void main(String[] args) {
		Scanner inp = new Scanner(System.in);
		Action act = new Action();
		
		System.out.println("Qual é o caminho do arquivo?");
		String filePath = inp.next();
		
		System.out.println("Qual é o delimitador?");
		String delimiter = inp.next();
		
		System.out.println("Qual o nome da tabela?");
		String tableName = inp.next();
				
		act.mountInsertQuery(filePath, delimiter,tableName);
		System.out.println("Insert Query criado com sucesso");
		
		inp.close();
	}
}
