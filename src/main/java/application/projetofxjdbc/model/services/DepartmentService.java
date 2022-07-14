package application.projetofxjdbc.model.services;

import application.projetofxjdbc.model.entities.Departamento;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Departamento> findAll(){
        List<Departamento> list = new ArrayList<>();
        list.add(new Departamento(1,"Livros"));
        list.add(new Departamento(2,"Computadores"));
        list.add(new Departamento(3,"Alimentos"));
        return list;

    }
}
