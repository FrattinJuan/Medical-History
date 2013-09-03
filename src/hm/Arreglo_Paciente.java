package hm;
//Declaracion de las librerias adicionales
import java.io.*;
import java.util.ArrayList;

public class Arreglo_Paciente implements Serializable {
//Arreglo de objetos para los registros academicos
    private ArrayList<Paciente> a;

//Generacion del contructor
    public Arreglo_Paciente() {
//Creando el objeto
        a = new ArrayList();
    }//Fin del constructor

//METEDOS PARA ADMINISTRAR LA INFORMACION DEL ARREGLO
//Adiciona un nuevo registro de datos
    public void Agregar(Paciente nuevo) {
        a.add(nuevo);
    }

//Obtiene un registro
    public Paciente getCliente(int i) {
        return a.get(i);
    }

//Remplaza una informacion de un registro
    public void Reemplaza(int i, Paciente Actualizado) {
        a.set(i, Actualizado);
    }

//Obteniendo el numero de registros de los alumnos
    public int numeroClientes() {
        return a.size();
    }

//Elimina un registro del alumno
    public void Elimina(int i) {
        a.remove(i);
    }

//Elimina todo el registro del alumno
    public void Elimina() {
        for (int i = 0; i < numeroClientes(); i++) {
            a.remove(0);
        }//Fin del for
    }

//Busca alumno por codigo
    public int Buscar(String codigo) {
        for (int i = 0; i < numeroClientes(); i++) {
            if (codigo.equalsIgnoreCase(getCliente(i).getCodigo())) {
                return i;//retornando el indice
            }
        }//Fin del for
        return -1;//Significa que no encontró ningun valor
    }

    public int BuscarPorNombre(String nombre) {
        for (int i = 0; i < numeroClientes(); i++) {
            if (nombre.equalsIgnoreCase(getCliente(i).getNombre())) {
                return i;//retornando el indice
            }
        }//Fin del for
        return -1;
       //Significa que no encontró ningun valor
    }
}//Fin de la clace
