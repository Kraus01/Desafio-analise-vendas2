package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Main {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner (System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();
		
		try (BufferedReader br = new BufferedReader ( new FileReader (path))){
			
			List <Sale> sale = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String [] fields = line.split(",");
				sale.add(new Sale (Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			Set<String> uniqueSellers = sale.stream()
			        .map(Sale::getSeller)
			        .collect(Collectors.toSet());
			
			System.out.println("Total de vendas por vendedor:");
			uniqueSellers.forEach(seller -> {
			    double sum = sale.stream()
			            .filter(s -> s.getSeller().equals(seller))
			            .mapToDouble(Sale::getTotal)
			            .sum();
			    System.out.printf("%s - R$ %.2f%n", seller, sum);
			});
			
			
		}
		
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
