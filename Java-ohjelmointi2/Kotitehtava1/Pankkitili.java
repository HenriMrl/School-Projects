
public class Pankkitili {
	private String Tilinumero;
	private String Tilin_omistaja;
	private double Saldo;
	
	public Pankkitili(String Tilin_omistaja, String Tilinumero, double Saldo) {
		this.Tilin_omistaja = Tilin_omistaja;
		this.Tilinumero = Tilinumero;
		this.Saldo = Saldo;
		
		
	}
	
	public void setTilinumero(String Tilinumero) {
		this.Tilinumero = Tilinumero;
	}
	
	public void setTilin_omistaja(String Tilin_omistaja) {
		this.Tilin_omistaja = Tilin_omistaja;
		
	}
	
   public void setSaldo(double Saldo) {
	   this.Saldo = Saldo;
   }
   public String getTilin_omistaja() {
	   return this.Tilin_omistaja;
   }
   
   public Double getSaldo() {
	   return this.Saldo;
   }
   
   public String getTilinumero() {
	   return this.Tilinumero;
   }
   
   
  void talletus(double maara) {
	  if (maara <= 0) {
		  System.out.println("Anna positiivinen luku!");
      }
	  else {
	      this.Saldo = this.Saldo +maara;
	      System.out.println("Talletus onnistui.");
	  }
  }
  
  void otto(double maara) {
	  if (maara > Saldo) {
		  System.out.println("Tilin saldo ylittyi, nosto epäonnistui.");
      }
		  
		  
	  if (maara <= 0) {
		  System.out.println("Anna positiivinen luku!");
      }
	  
	  else{ 
		  if((maara <= Saldo) & (maara > 0)){
			  this.Saldo = this.Saldo - maara;
			  System.out.println("Nosto onnistui.");
			  
		  }
		  
      }
        
			  
  }
 
 void tulosta() {
    System.out.println("Tilin omistaja:" + " " + this.Tilin_omistaja);
	System.out.println("Tilinumero:" + " " + this.Tilinumero);
	System.out.println("Saldo:" + " " + this.Saldo);
	
  }
  
  }
	
    
   
   
	
	

	

