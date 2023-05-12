package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Main {
	
	public static void main (String[] args) {
		
		Scanner sc = new Scanner (System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();
		
		try (BufferedReader br = new BufferedReader ( new FileReader (path))){
			
			List <Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String [] fields = line.split(",");
				list.add(new Sale (Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			
			}
			
			List<Sale> topFiveSales2016 = list.stream().filter(sale -> sale.getYear() == 2016).sorted(Comparator.comparing(Sale::averagePrice).reversed()).limit(5).collect(Collectors.toList());
			double totalLogan = list.stream().filter(sale -> sale.getSeller().charAt(0) == 'L' && (sale.getMonth() == 1 || sale.getMonth() == 7 ))
					.map(sale -> sale.getTotal())
					.reduce(0.0, (x,y) -> x + y);
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			topFiveSales2016.forEach(System.out::println);
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", totalLogan));
		}
		
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
