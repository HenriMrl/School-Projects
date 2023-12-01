import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Anna Tilin omistaja: ");
	    String Tilin_omistaja = sc.nextLine();
		System.out.print("anna Tilinumero: ");
		String Tilinumero = sc.nextLine();
		System.out.print("anna saldo: ");
		Double Saldo = sc.nextDouble();
		
		
		
		
		
		Pankkitili x = new Pankkitili(Tilin_omistaja, Tilinumero , Saldo);
		x.tulosta();
		System.out.println("Anna nostettava m‰‰r‰: ");
		Double otto = sc.nextDouble();
		x.otto(otto);
		x.tulosta();
		System.out.println("Anna tallennettava m‰‰r‰: ");
		double talletus = sc.nextDouble();
		x.talletus(talletus);
		x.tulosta();
		
        }

}
