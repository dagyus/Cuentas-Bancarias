/***********************************************************************************************************************

Esta clase contiene el metodo main y aca se crean las instancias de la clase cuenta.
Este programa cuenta con un menu que se repite al terminar con una operacion y termina solo ingresando la opcion 5 cuando 
se esta situado en el menu.

***********************************************************************************************************************/

//IMPORTS
import java.util.ArrayList;
import java.util.Scanner;
/**********************************************************************************************************************/
public class Main {
	//mis ID van a ir de 100 en adelante incrementandose automaticamente y a su vez aumenta mi cantidad de cuentas
	static int id=100, cantidadCuentas=0; 
	static Scanner leer=new Scanner(System.in);
	static ArrayList <Cuenta> cuentas = new ArrayList<Cuenta>(); //creo una lista de tipo Cuenta 
	public static void main(String[] args) {
		menu(); //dentro del main solo llamo a mi menu
	}
	public static void menu(){
		int op=0; //inicializo mi opcion ingresada en 0
		while(op<1 || op>5){ //hago que mi menu se repita si elijo una opcion inadecuada
			System.out.println("Cuentas Bancarias \n\t"+
								"1. Ingresar nueva cuenta \n\t"+
								"2. Realizar ingresos \n\t"+
								"3. Realizar reintregros\n\t"+
								"4. Realizar transferencias\n\t"+
								"5. Salir");
			op=leer.nextInt(); //muestro el menu y leo la opcion por teclado
		}
		switch(op){	//verifico el valor ingresado y llamo a la accion correspondiente a las opciones del menu
		case 1:
			nuevaCuenta();
			break;
		case 2:
			ingresos();
			break;
		case 3:
			reintegros();
			break;
		case 4:
			transferencias();
			break;
		case 5:
			System.out.println("Gracias por usar el sistema.");
			System.exit(0);	//en caso de la opcion salir, muestro un mensaje y fuerzo el sistema a salir sin codigo de error	
			break;
		default:
			break;
		}
	}
	//aca pido al usuario los datos para crear una nueva cuenta y la agrego a la lista
	public static void nuevaCuenta() {
		System.out.println("¿Cuantas cuentas quiere ingresar?");
		int cant=leer.nextInt(); //pido al usuario la cantidad de cuentas a crear
		String nombre="", nroCuenta="";
		Double interes=0.0, saldo=0.0;
		//este ciclo repetitivo hace que cree la cantidad de cuentas correspondientes a las que quiere crear el usuario
		//ademas tambien incrementa mi atributo ID y mi cantidad de cuentas automaticamente
		for(int i=0;i<cant;i++,id++,cantidadCuentas++){ 
			for(int j=0;j<4;j++){ //con este ciclo me aseguro que van a aparecer los mensajes al usuario de forma correcta
				switch(j){
				case 0:
					leer.nextLine();
					System.out.println("Nombre: ");
					nombre=leer.nextLine();
					break;
				case 1:
					System.out.println("Nro. de Cuenta: ");
					nroCuenta=leer.nextLine();
					break;
				case 2:
					System.out.println("Interes: ");
					interes=leer.nextDouble();
					break;
				case 3:
					System.out.println("Saldo: ");
					saldo=leer.nextDouble();
					break;
				default:
					break;
				}
			}
			cuentas.add(new Cuenta(id, nombre, nroCuenta, interes, saldo)); //instancio la clase cuenta con los valores ingresados y el id generado
		}
		System.out.println("");
		//muestro todas las cuentas
		for(int i=0;i<cuentas.size();i++){
			System.out.println(cuentas.get(i).toString());
		}
		menu(); //vuelvo al menu
	}
	/*
	Esta funcion se encarga de realizar o no la transferencia de acuerdo al valor recibido por el metodo boolean transferencias en la clase Cuenta.
	De ser posible, la realiza y muestra los montos actualizados, de no ser posible muestra los montos sin modificarlos y avisa que no se pudo realizar
	la transferencia.
	*/
	public static void transferencias() {
		int contadorIdOrigen=0, contadorIdDestino=0;
		//ingreso, id de origen, destino y monto a transferir
		System.out.println("Ingrese ID de origen: ");
		int idOrigen=leer.nextInt();
		System.out.println("Ingrese ID de destino: ");
		int idDestino=leer.nextInt();
		System.out.println("Ingrese el monto a transferir: ");
		Double monto=leer.nextDouble();
		for(int i=0;i<cuentas.size();i++){ //recorro la lista en busca del id de origen
			if(cuentas.get(i).getId()==idOrigen){ //consulto si mi id actual es igual al id de origen	
				for(int j=0;j<cuentas.size();j++){ //recorro la lista en busca de mi id de destino
					if(cuentas.get(j).getId()==idDestino){ //consulto si la id actual es la de mi id de destino
						if(cuentas.get(i).transferencias(cuentas.get(j), monto)==true){  //si mi metodo devolvio true
							Double nuevoSaldo=cuentas.get(j).getSaldo()+monto; //sumo el monto a mi saldo actual del id destino 
							cuentas.get(j).setSaldo(nuevoSaldo); //le modifico el valor del saldo a mi id destino por medio del setSaldo() poniendole mi nuevo saldo
							//muestro el mensaje de que pude hacer la transeferencia y muestro los saldos actualizados
							System.out.println("Transferencia realizada correctamente.");
							System.out.println("SALDO CUENTA ORIGEN: \t"+cuentas.get(i).getSaldo()+
											"\nSALDO CUENTA DESTINO: "+cuentas.get(j).getSaldo());
						}else{ //en caso que mi funcion haya devuelto false muestro que no se pudo realizar la transferencia y muestro los saldos sin modificar
							System.out.println("No se pudo realizar la transferencia.");
							System.out.println("Los saldos no se han modificado.");
							System.out.println("SALDO CUENTA ORIGEN: \t"+cuentas.get(i).getSaldo()+
									"\nSALDO CUENTA DESTINO: "+cuentas.get(j).getSaldo());
						}
						contadorIdDestino--; //si lo encontre a mi id de destino le resto 1 a mi contador por si este id es el ultimo de la lista
					}else
						contadorIdDestino++; //aumento mi contador de id destino para verificar que no lo encontre
					if(contadorIdDestino==cuentas.size()){ //en el caso de que el tamaño de la lista y mi contador coincidan muestro que no encontre el id destino y vuelvo a llamar a transferencias
						System.out.println("No se encontro el ID de destino.");
						transferencias();
					}
				}
				contadorIdOrigen--; //si encontre mi id de origen, le resto 1 a mi contador por si este id es el ultimo de la lista
			}else
				contadorIdOrigen++; //aumento mi contador de id origen para verificar que no lo encontre
			if(contadorIdOrigen==cuentas.size()){ //en el caso de que el tamaño de la lista y mi contador coincidan muestro que no encontre el id origen y vuelvo a llamar a transferencias
				System.out.println("No se encontro el ID de origen.");
				transferencias();
			}
		}
		menu(); //vuelvo al menu
	}
	/*
	Esta funcion se encarga de realizar o no el ingreso de acuerdo al valor recibido por el metodo boolean ingresos en la clase Cuenta.
	De ser posible, la realiza y muestra los montos actualizados, de no ser posible muestra los montos sin modificarlos y avisa que no se pudo realizar
	la transferencia.
	*/
	public static void ingresos(){
		int contadorId=0; 
		System.out.println("Ingrese el numero de ID del ingreso: ");
		int idIngreso=leer.nextInt(); //pido al usuario el id del ingreso
		for(int i=0;i<cuentas.size();i++){
			if(cuentas.get(i).getId()==idIngreso){ //recorro la lista y verifico si el id actual es el ingresado por teclado
				System.out.println("Monto a ingresar: ");
				Double ingreso=leer.nextDouble(); //en caso de encontrarlo pido el monto a depositar
				if(cuentas.get(i).ingresos(ingreso)==true){ //si mi metrodo ingresos devuelve true muestro que pude realizar la operacion y muestro el nuevo saldo
					System.out.println("Transferencia realizada.");
					System.out.println("SALDO: "+cuentas.get(i).getSaldo());
				}
				else{ //de lo contrario aviso que no pude hacer la operacion y muestro el saldo sin modificar
					System.out.println("No se pudo realizar la transferencia.");
					System.out.println("SALDO: "+cuentas.get(i).getSaldo());
				}
				contadorId--; //si encontre mi id, le resto 1 a mi contador por si este id es el ultimo de la lista
			}
			else
				contadorId++;//aumento mi contador de id para verificar que no lo encontre
			if(contadorId==cuentas.size()){//en el caso de que el tamaño de la lista y mi contador coincidan muestro que no encontre el id y vuelvo a llamar a ingresos
				System.out.println("No se encontro el id.");
				ingresos();
			}
		}
		menu(); //vuelvo al menu
	}
	/*
	Esta funcion se encarga de realizar o no el reintegro de acuerdo al valor recibido por el metodo boolean reintegros en la clase Cuenta.
	De ser posible, la realiza y muestra los montos actualizados, de no ser posible muestra los montos sin modificarlos y avisa que no se pudo realizar
	la transferencia.
	*/
	public static void reintegros(){
		int contadorId=0;
		System.out.println("Ingrese el numero de ID del reintegro: ");
		int idReintegro=leer.nextInt();//pido al usuario el id del reintegro
		for(int i=0;i<cuentas.size();i++){
			if(cuentas.get(i).getId()==idReintegro){ //recorro la lista y verifico si el id actual es el ingresado por teclado
				System.out.println("Monto a reintegrar: ");
				Double reintegro=leer.nextDouble();//en caso de encontrarlo pido el monto a reintegrar
				if(cuentas.get(i).reintegros(reintegro)==true){//si mi metrodo reintegros devuelve true muestro que pude realizar la operacion y muestro el nuevo saldo
					System.out.println("Transferencia realizada.");
					System.out.println("SALDO: "+cuentas.get(i).getSaldo());
				}
				else{ //de lo contrario aviso que no pude hacer la operacion y muestro el saldo sin modificar
					System.out.println("No se pudo realizar la transferencia.");
					System.out.println("SALDO: "+cuentas.get(i).getSaldo());
				}
				contadorId--; //si encontre mi id, le resto 1 a mi contador por si este id es el ultimo de la lista
			}
			else
				contadorId++;//aumento mi contador de id para verificar que no lo encontre
			if(contadorId==cuentas.size()){//en el caso de que el tamaño de la lista y mi contador coincidan muestro que no encontre el id y vuelvo a llamar a reintegros
				System.out.println("No se encontro el id.");
				reintegros();
			}
		}
		menu(); //vuelvo al menu
	}
}
