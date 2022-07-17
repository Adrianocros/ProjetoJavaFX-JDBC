package application.projetofxjdbc.model.services;

import application.projetofxjdbc.model.dao.DaoFactory;
import application.projetofxjdbc.model.dao.VendedorDao;
import application.projetofxjdbc.model.entities.Vendedor;

import java.util.List;

public class VendedorService {

    private VendedorDao dao = DaoFactory.createVendedorDao();

    public List<Vendedor> findAll(){
        //Chamada no banco
        return dao.findAll();
    }
    //Verifica o obj inser ou atualizada
    public void saveOrUpdate(Vendedor obj){
        if(obj.getId() == null){
            dao.insert(obj);
        }else {
            dao.update(obj);
        }
    }
    //remove o dpto do banco
    public void remove(Vendedor obj){
        dao.deleteById(obj.getId());
    }
}
