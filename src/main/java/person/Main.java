package person;

import com.github.javafaker.Faker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.Locale;


public class Main {

    private static Faker faker = new Faker(new Locale("en"));

    private static Person randomPerson() {
        Person person = Person.builder()
                .name(faker.name().fullName())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(faker.options().option(Person.Gender.class))
                .address(Address.builder()
                        .country(faker.address().country())
                        .state(faker.address().state())
                        .city(faker.address().state())
                        .streetAddress(faker.address().streetAddress())
                        .zip(faker.address().zipCode())
                        .build()
                 )
                .email(faker.internet().emailAddress())
                .profession(faker.company().profession())
                .build();
        return person;
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();

       int emberek = 20;
       for (int i = 0; i < emberek; i++){
           Person person = randomPerson();
           em.getTransaction().begin();
           em.persist(person);
           em.getTransaction().commit();
           System.out.println(person);
       }
       em.close();
       emf.close();

    }

}