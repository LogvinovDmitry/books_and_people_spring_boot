package logvinov.springcourse.Project2MyBoot.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "author")
    @NotEmpty(message = "This field cannot be empty")
    private String author;
    @Column(name = "name")
    @NotEmpty(message = "This field cannot be empty")
    private String name;
    @Column(name = "publication_year")
    //@NotEmpty(message = "This field cannot be empty")
    @Min(value = 0, message = "Age must be greater than zero")
    private int publicationYear;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_when_book_was_taken")
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateWhenBookWasTaken;

    @Transient // Hibernate не будет замечать этого поля, что нам и нужно. По-умолчанию false.
    private boolean isDifferenceGreaterThan10Days;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {

    }

    public Book(String author, String name, int publicationYear, Person owner) {
        this.author = author;
        this.name = name;
        this.publicationYear = publicationYear;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDateTime getDateWhenBookWasTaken() {
        return dateWhenBookWasTaken;
    }

    public void setDateWhenBookWasTaken(LocalDateTime dateWhenBookWasTaken) {
        this.dateWhenBookWasTaken = dateWhenBookWasTaken;
    }

    public boolean isDifferenceGreaterThan10Days() {
        return isDifferenceGreaterThan10Days;
    }

    public void setDifferenceGreaterThan10Days(boolean differenceGreaterThan10Days) {
        isDifferenceGreaterThan10Days = differenceGreaterThan10Days;
    }
}
