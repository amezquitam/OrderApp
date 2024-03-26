package ford.group.orderapp.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "clients",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"})
)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String address;

    public Client() {
    }

    public Client(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static final class ClientBuilder {
        private Long id;
        private String name;
        private String email;
        private String address;

        private ClientBuilder() {
        }

        public static ClientBuilder aClient() {
            return new ClientBuilder();
        }

        public ClientBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ClientBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Client build() {
            Client client = new Client();
            client.setId(id);
            client.setName(name);
            client.setEmail(email);
            client.setAddress(address);
            return client;
        }
    }
}
