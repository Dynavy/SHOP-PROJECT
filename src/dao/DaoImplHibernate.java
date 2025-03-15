package dao;

import model.Employee;
import model.Product;
import model.ProductHistory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DaoImplHibernate implements Dao {

	private SessionFactory sessionFactory;

	public DaoImplHibernate() {
		connect();
	}

	@Override
	public void connect() {
		// We use the database configuration from hibernate.cfg.xml.
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}

	@Override
	public void disconnect() {

		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@Override
	public Employee getEmployee(String user, String pw) {

		try (Session session = sessionFactory.openSession()) {

			String sql = "SELECT * FROM Employee WHERE employeeId = :user AND password = :pw";
			return (Employee) session.createNativeQuery(sql, Employee.class).setParameter("user", user)
					.setParameter("pw", pw).uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Product> getInventory() {

		try (Session session = sessionFactory.openSession()) {

			return (ArrayList<Product>) session.createQuery("FROM Product", Product.class).list();
		}
	}

	@Override
	public boolean writeInventory(ArrayList<Product> products) {

		Transaction transaction = null;
		
		try (Session session = sessionFactory.openSession()) {

			transaction = session.beginTransaction();

			// Iterates through products ArrayList and saves the data into
			// historical_inventory table.
			for (Product product : products) {
				ProductHistory productHistory = new ProductHistory();
				productHistory.setProductId(product.getId());
				productHistory.setName(product.getName());
				productHistory.setPrice(product.getPrice());
				productHistory.setAvailable(product.isAvailable());
				productHistory.setStock(product.getStock());
				productHistory.setCreatedAt(LocalDateTime.now());

				session.save(productHistory);
			}

			transaction.commit();
			return true;

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addProduct(Product product) {

		Transaction transaction = null;

		try (Session session = sessionFactory.openSession()) {

			transaction = session.beginTransaction();

			// Update the prices thanks to the wholsalerPrice.
			product.updatePricesFromWholesaler();

			// Save the product.
			session.save(product);
			transaction.commit();

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {

		Transaction transaction = null;

		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			// Update the product.
			session.update(product);
			transaction.commit();

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Product product) {

		Transaction transaction = null;
		
		// Load the product entity from the database using its ID. 
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			
			product = session.get(Product.class, product.getId());
			
			  if (product != null) {
				  
		            session.delete(product);
		            transaction.commit();
		            
		        } else {
		        	
		            System.out.println("Product not found for deletion");
		        }

		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			
			e.printStackTrace();
		}
	}
}
