package se.plushogskolan.ju15.beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Creates a PersonBean to store data in.
 * @version 1.1
 */
public class PersonBean {
	
	private StringProperty fullName = new SimpleStringProperty() ;
	private StringProperty email = new SimpleStringProperty() ;
	private StringProperty gender = new SimpleStringProperty() ;
	private IntegerProperty age = new SimpleIntegerProperty() ;
	
	public PersonBean () {
	
	}
	
	public PersonBean(String fullName, String email, String gender, Integer age) {
		setFullName(fullName);
		setEmail(email);
		setGender(gender);
		setAge(age);
	}
	
	public final StringProperty fullNameProperty() {
        return this.fullName;
    }

    public final String getFullName() {
        return this.fullNameProperty().get();
    }

    public final void setFullName(final String name) {
        this.fullNameProperty().set(name);
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
	
	public final StringProperty genderProperty() {
        return this.gender;
    }

    public final String getGender() {
        return this.genderProperty().get();
    }

    public final void setGender(final String gender) {
		this.genderProperty().set(gender);
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