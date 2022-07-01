package PojoClasses.Serialization;

import java.util.ArrayList;
import java.util.List;

public class NestedJson {
	
	public Dashboard getDashboard() {
		return dashboard;
	}
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	public List<Courses> getCoursesList() {
		return CoursesList;
	}
	public void setCoursesList(List<Courses> coursesList) {
		CoursesList = coursesList;
	}
	public Dashboard dashboard;
	public List<Courses> CoursesList;
	
	public static void main(String[] args) {
		
		NestedJson nst=new NestedJson();
		Dashboard ds=new Dashboard();
		Courses cs1=new Courses();
		Courses cs2=new Courses();
		Courses cs3=new Courses();
		cs1.setTitle("Selenium Python");
		cs2.setTitle("Cypress");
		cs3.setTitle("RPA");
		cs1.setPrice(50);
		cs2.setPrice(40);
		cs3.setPrice(45);
		cs1.setCopies(6);
		cs2.setCopies(10);
		cs3.setCopies(4);
		ds.setPurchaseAmount(90);
		ds.setWebsite("rahulshettyacademy.com");
		nst.setDashboard(ds);
		List<Courses> lst=new ArrayList<>();
		lst.add(cs1);
		lst.add(cs2);
		lst.add(cs3);
		nst.setCoursesList(lst);
		
	}

}
