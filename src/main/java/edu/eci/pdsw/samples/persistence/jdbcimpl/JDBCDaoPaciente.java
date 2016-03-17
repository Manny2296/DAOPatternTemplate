/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;
    boolean validador;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
        
        validador = false;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        Paciente p = null;
       // String query_loadpac="select nombre, fecha_nacimiento  from PACIENTES where id=?";
        String query_loadpac ="select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac inner join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where pac.id=? and pac.tipo_id='cc'";
       try {
          con.setAutoCommit(false);
          ps= con.prepareStatement(query_loadpac);
          ps.setInt(1,idpaciente);
  
            ResultSet executeQuery = ps.executeQuery();
        
            while(executeQuery.next()){
                
            
              String string = executeQuery.getString(1);
             return new Paciente(idpaciente, tipoid, string, executeQuery.getDate(2));
               // System.out.println("Carga exitosa Paciente:" + aInt + "String:"+string  );
             
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("Se cayo la transa "+idpaciente,ex);
        }
       return p;
    }

 
    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps,ps2;
        String query_addpaciente = "INSERT INTO PACIENTES(id,tipo_id,nombre,fecha_nacimiento) values(?,?,?,?)";
        String query_addconsulta = "INSERT INTO CONSULTAS(idCONSULTAS,fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id)values(?,?,?,?,'cc')";
        try {
        con.setAutoCommit(false);
        ps = con.prepareStatement(query_addpaciente);
        ps.setString(1,""+p.getId());
        ps.setString(2,p.getTipo_id());
        ps.setString(3,p.getNombre());
        ps.setString(4,""+p.getFechaNacimiento());
        ps2= con.prepareStatement(query_addconsulta);
        ps2.setString(1,"01");
        ps2.setDate(2, new Date(0));
        ps2.setString(3, "primera consulta");
        ps2.setString(4,""+p.getId());
        
            int executeQuery = ps.executeUpdate();
            int executeUpdate = ps2.executeUpdate();
            if(executeQuery ==1)
            {System.out.println("Registro agregado satisfactoriamoente" + executeQuery);}
            if(executeUpdate ==1){
                System.out.println("Consulta agregada");
            }
           con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product. Agregado raro",ex);
        }
        
      

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } */
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");
    }

    @Override
    public boolean isValidador() {
      return validador;
    }
    
}
    