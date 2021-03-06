/***********************************************************************************************************************

Esta clase se encarga de verificar que se puedan realizar las transferecias, ingresos y reintegros de las cuentas.
Contiene los atributos y metodos que tienen las cuentas.

***********************************************************************************************************************/
public class Cuenta {
	//ATRIBUTOS
	private String nombre, nroCuenta;
	private double interes, saldo;
	private int id;
	
	//Constructor con parametros completo
	Cuenta(int id, String nombre, String nroCuenta, double interes, double saldo){
		this.id=id;
		this.nombre=nombre;
		this.nroCuenta=nroCuenta;
		this.interes=interes;
		this.saldo=comprobarSaldo(saldo);
	}
	//Constructor por defecto
	Cuenta(){
		this.id=0;
		this.nombre="";
		this.nroCuenta="";
		this.interes=0.0;
		this.saldo=0.0;
	}
	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getnroCuenta() {
		return nroCuenta;
	}
	public void setnroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	public double getInteres() {
		return interes;
	}
	public void setInteres(double interes) {
		this.interes = interes;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	//Comprueba que el saldo ingresado sea positivo, de lo contrario se inicializa en 0
	public double comprobarSaldo(double saldo) {
		if(saldo<0)
			saldo=0.0;
		return saldo;
	}
	//Compruebo que se pueda realizar el ingreso y retorno un boolean de acuerdo a si es posible o no
	public boolean ingresos(double monto){
		boolean ingreso=true; //por defecto mi booleano va a ser true para que se pueda realizar
		ing=comprobarSaldo(monto);
		try{//intento hacer el ingreso
			saldo+=monto;
			ingreso=true;
		}catch (Exception e){ //de lo contrario atrapo la excepcion y devuelvo false
			ingreso=false;
		}
		return ingreso;
	}
	//Compruebo que se pueda realizar el reintegro y retorno un boolean de acuerdo a si es posible o no
	public boolean reintegros(double monto){
		boolean reintegro=true;//por defecto mi booleano va a ser true para que se pueda realizar
		ing=comprobarSaldo(monto);
		try{ //intento hacer el reintegro
			if(saldo>=monto){//compruebo que mi saldo sea mayor al reintegro para que no quede negativo
				saldo-=monto;
				reintegro=true; //en caso de que mi saldo sea mayor al reintegro resto el valor a mi saldo y devuelvo true
			}else
				reintegro=false; //si mi saldo es menor al reintegro devuelvo false
		}catch (Exception e){ //de lo contrario atrapo la excepcion y devuelvo false
			reintegro=false; 
		}
		return reintegro;
	}
	//Compruebo que se pueda realizar la transferencia y retorno un boolean de acuerdo a si es posible o no
	public boolean transferencias(Cuenta obj, double monto){ //recibo como paremetros el monto y la cuenta destino
		boolean transferencia=true;//por defecto mi booleano va a ser true para que se pueda realizar
		monto=obj.comprobarSaldo(monto);
		try{
			if(saldo>=monto){ //compruebo que mi saldo sea mayor al reintegro para que no quede negativo
				saldo-=monto;
				transferencia=true; //en caso de que mi saldo sea mayor al reintegro resto el valor a mi saldo y devuelvo true
			}else
				transferencia=false;//si mi saldo es menor al reintegro devuelvo false
		}catch (Exception e){//de lo contrario atrapo la excepcion y devuelvo false
			transferencia=false;
		}
		return transferencia;
	}
	//Sobreescribo el metodo toString() para mostrar los datos de las cuentas
	@Override
	public String toString(){
		return "ID: " + id +
				"\tNOMBRE: " + nombre +
				"\tNro. de CUENTA: " + nroCuenta +
				"\tINTERES: " + interes +
				"\tSALDO: " + saldo;
	}
}
