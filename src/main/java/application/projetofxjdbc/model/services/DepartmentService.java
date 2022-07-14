package application.projetofxjdbc.model.services;

import application.projetofxjdbc.model.dao.DaoFactory;
import application.projetofxjdbc.model.dao.DepartamentoDao;
import application.projetofxjdbc.model.entities.Departamento;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartamentoDao dao = DaoFactory.createDepartmentDao();

    public List<Departamento> findAll(){
        //Chamada no banco
        return dao.findAll();
    }
}
