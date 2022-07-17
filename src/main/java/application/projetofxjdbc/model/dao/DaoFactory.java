package application.projetofxjdbc.model.dao;

import application.projetofxjdbc.model.dao.impl.DepartamentoDaoJDBC;
import application.projetofxjdbc.model.dao.impl.VendedorDaoJDBC;
import application.projetofxjdbc.db.DB;

public class DaoFactory {

	public static VendedorDao createVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
}
