package se.plushogskolan.ju15.beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Creates a PersonBean to store data in.
 * @version 1.2.2 2015-11-05
 */
public class PersonBean {
	
	private StringProperty firstName = new SimpleStringProperty() ;
	private StringProperty lastName = new SimpleStringProperty() ;
	private StringProperty email = new SimpleStringProperty() ;
	private StringProperty pet = new SimpleStringProperty() ;
	private IntegerProperty age = new SimpleIntegerProperty() ;
	
	public PersonBean () {
	
	}
	
	//Ändrar PersonBean så den har bara fullname.
	public PersonBean(String firstName,String lastName, String email, String pet, Integer age) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPet(pet);
		setAge(age);
	}
	
	public final StringProperty firstNameProperty() {
        return this.firstName;
    }

    public final String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(final String name) {
        this.firstNameProperty().set(name);
    }
    
    public final StringProperty lastNameProperty() {
        return this.lastName;
    }

    public final String getLastName() {
        return this.lastNameProperty().get();
    }

    public final void setLastName(final String name) {
        this.lastNameProperty().set(name);
    }
    
	public final StringProperty emailProperty() {
        return this.email;
    }

    public final String getEmail() {
        return this.emailProperty().get();
    }

    public final void setEmail(final String email) {
        this.emailProperty().set(email);
    }
	
	public final StringProperty petProperty() {
        return this.pet;
    }

    public final String getPet() {
        return this.petProperty().get();
    }

    public final void setPet(final String n) {
		this.petProperty().set(n);
    }
	
	public final IntegerProperty ageProperty() {
        return this.age;
    }

    public final Integer getAge() {
        return this.ageProperty().get();
    }

    public final void setAge(final Integer age) {
    	this.ageProperty().set(age);
    }

 }