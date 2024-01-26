package logvinov.springcourse.Project2MyBoot.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

//@PropertySource("classpath:messages.properties")
@Entity
@Table
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "This field cannot be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50  characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Age must be greater than zero")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "This field cannot be empty")
    @Email(message = "You entered an invalid email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "This field cannot be empty")
    //@Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "You didn't fill in the address correctly. Please: Canada, Toronto, 542365")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+, [А-ЯЁ][а-яё]+, \\d{6}", message = "Вы не заполнили адрес корректно. Пожалуйста, используйте формат: Украина, Запорожье, 123456")
    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_birth_day")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataBirthDay;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_registration")
    private Date dateOfRegistration;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> bookList;

    public Person() {
    }

    public Person(String name, int age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Date getDataBirthDay() {
        return dataBirthDay;
    }

    public void setDataBirthDay(Date dataBirthDay) {
        this.dataBirthDay = dataBirthDay;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

}
