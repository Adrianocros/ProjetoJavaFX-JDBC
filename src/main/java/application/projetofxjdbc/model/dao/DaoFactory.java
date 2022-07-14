package application.projetofxjdbc.model.dao;

import application.projetofxjdbc.model.dao.impl.DepartamentoDaoJDBC;
import application.projetofxjdbc.model.dao.impl.VendedorDaoJDBC;
import application.projetofxjdbc.db.DB;

public class DaoFactory {

	public static VendedorDao createSellerDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartmentDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
}
