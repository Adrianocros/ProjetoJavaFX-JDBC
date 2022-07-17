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
    //Verifica o obj inser ou atualizada
    public void saveOrUpdate(Departamento obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else {
            dao.update(obj);
        }
    }
    //remove o dpto do banco
    public void remove(Departamento obj){
        dao.deleteById(obj.getId());
    }
}
