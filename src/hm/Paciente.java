package hm;
//Importando las librerias
import java.io.Serializable;
import javax.swing.*;

public class Paciente implements Serializable
{
//Declaramos la lista de atributos
private String codigo;
private String RUC;
private String DNI;
private String nombre;
private String direccion;
private String fecha;
private String telefono;
private String celular;
private String fax;
private String forma_p;
private String credito;
private String mail;
private String medico;
private String obs;
private Icon foto;

//Declaramos el contructor vacio
public Paciente () {}

//Declaramos el contructor con parametros
public Paciente (String codigo, String RUC, String DNI, String nombre, String direccion, String fecha, String telefono, 
                String celular, String fax, String forma_p, String credito, String mail, String medico, String obs, Icon foto)
{
this.codigo = codigo;
this.RUC = RUC;
this.DNI = DNI;
this.nombre = nombre;
this.direccion = direccion;
this.fecha = fecha;
this.telefono = telefono;
this.celular = celular;
this.fax = fax;
this.forma_p = forma_p;
this.credito = credito;
this.mail = mail;
this.medico = medico;
this.obs = obs;
this.foto = foto;
}

//iniziamos el seteo

    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the RUC
     */
    public String getRUC() {
        return RUC;
    }

    /**
     * @param RUC the RUC to set
     */
    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    /**
     * @return the DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * @param DNI the DNI to set
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the razon
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param razon the razon to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the forma_p
     */
    public String getForma_p() {
        return forma_p;
    }

    /**
     * @param forma_p the forma_p to set
     */
    public void setForma_p(String forma_p) {
        this.forma_p = forma_p;
    }

    /**
     * @return the credito
     */
    public String getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(String credito) {
        this.credito = credito;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the medico
     */
    public String getMedico() {
        return medico;
    }

    /**
     * @param medico the vendedor to set
     */
    public void setMedico(String medico) {
        this.medico = medico;
    }

    /**
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
    }

    /**
     * @return the foto
     */
    public Icon getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Icon foto) {
        this.foto = foto;
    }
}// Fin de la clace

