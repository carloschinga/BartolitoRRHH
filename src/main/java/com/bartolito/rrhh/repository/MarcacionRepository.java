package com.bartolito.rrhh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MarcacionRepository {

    @Autowired
    @Qualifier("wdmsJdbcTemplate")
    private JdbcTemplate wdmsJdbc;


    /*====================== SECCIÓN PERSONAL ======================*/

    public List<Map<String, Object>> listarPersonal() {
        String sql = "EXEC sp_bart_personal_listar";
        return wdmsJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarPersonal(int codiPers) {
        String sql = "EXEC sp_bart_personal_seleccionar ?";
        return wdmsJdbc.queryForList(sql);
    }

    /*====================== MARCACION PERSONAL ======================*/


}
