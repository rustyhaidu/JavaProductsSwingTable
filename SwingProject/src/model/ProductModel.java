package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entities.Product;

public class ProductModel {

	public List<Product> findAll() {
		List<Product> listProducts = new ArrayList<>();
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("select * from product");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setDescription(rs.getString("description"));
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setQuantity(rs.getInt("quantity"));
				listProducts.add(p);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return listProducts;

	}

	public Product find(int id) {
		Product p = new Product();
		try {
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("select * from product where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				p.setDescription(rs.getString("description"));
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setQuantity(rs.getInt("quantity"));
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return p;
	}
	public boolean create(Product p){
		try{
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("insert into product(name,price,quantity,description) values(?,?,?,?)");
			ps.setString(1, p.getName());
			ps.setDouble(2,  p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.setString(4, p.getDescription());
			return ps.executeUpdate() > 0;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	public boolean edit(Product p){
		try{
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("update product set name=?, price=?, quantity=? where id=?");
			ps.setString(1, p.getName());
			ps.setDouble(2,  p.getPrice());
			ps.setInt(3, p.getQuantity());
			//ps.setString(4, p.getDescription());
			ps.setInt(4, p.getId());
			return ps.executeUpdate() > 0;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	public boolean delete(Product p){
		try{
			PreparedStatement ps = ConnectDB.getConnection().prepareStatement("delete from product where id=?");
			
			ps.setInt(1, p.getId());
			return ps.executeUpdate() > 0;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
}
