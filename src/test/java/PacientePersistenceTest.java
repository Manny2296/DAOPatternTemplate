/*
 * Copyright (C) 2016 hcadavid
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

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    
    public PacientePersistenceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void databaseConnectionTest() throws IOException, PersistenceException{
        InputStream input = null;
        input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
        Properties properties=new Properties();
        properties.load(input);
        
        DaoFactory daof=DaoFactory.getInstance(properties);
        
        daof.beginSession();
        
        //IMPLEMENTACION DE LAS PRUEBAS
        
        //PRUEBA 1 
        Paciente pacprub1 = new Paciente(115, "cc", "Manuel Felipe",new Date(0));
        Set<Consulta> list_cons= new LinkedHashSet<>();
         Consulta cons1 = new Consulta(new Date(0), "Mantenimiento Preventivo");
         Consulta cons2 = new Consulta(new Date(0), "Analisis psicodelico");
         list_cons.add(cons2);
         list_cons.add(cons1);
         pacprub1.setConsultas(list_cons);
        // daof.getDaoPaciente().save(pacprub1);
               Paciente load2 = daof.getDaoPaciente().load(115,"cc");
                 assertEquals("Manuel Felipe", load2.getNombre());
               
        //PRUEBA 2
        Paciente tmp = new Paciente(58,"cc", "Isaias", new Date(0));
         
         //daof.getDaoPaciente().save(tmp);
          
        Paciente load = daof.getDaoPaciente().load(58,"cc");
        
        System.out.println("Usuario cargado:" + load.toString());
        //assert que verifica la carga de un usuario por medio del nombre obtenido.
        //En este caso se verifica el usuario 33 .
         assertEquals("Isaias", load.getNombre());


   
   
  //PRUEBA 3
   Paciente pacprub2 = new Paciente(1111, "cc", "Sergio Erick",new Date(0));
        Set<Consulta> list_cons2= new LinkedHashSet<>();
         Consulta cons3 = new Consulta(new Date(0), "Mantenimiento Preventivo");

         list_cons2.add(cons3);
         pacprub2.setConsultas(list_cons2);
         //daof.getDaoPaciente().save(pacprub2);
               Paciente load3 = daof.getDaoPaciente().load(1111,"cc");
                 assertEquals("Sergio Erick", load3.getNombre());
        //PRUEBA 4
        boolean ok= false;
        Paciente load1 = daof.getDaoPaciente().load(1111, "cc");
        if(load1.getId() == 1111){
            ok = true ;
        }
        assertTrue("Este paciente fue cargado", ok);
        
        
        
        
        daof.commitTransaction();
        daof.endSession();        
    }
    

   
    
    
}
