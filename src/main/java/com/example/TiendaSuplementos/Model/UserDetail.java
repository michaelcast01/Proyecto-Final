package com.example.TiendaSuplementos.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Long id;
    private String username;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Roles role;

    @Column(name = "role_id")
    private Long role_id;
    @ManyToOne
    @JoinColumn(name = "setting_id", insertable = false, updatable = false, nullable = true)
    private SettingsDetail settings;

    @Column(name = "setting_id", nullable = true)
    private Long setting_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
     }

     public void setRole(Roles role) {
         this.role = role;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

     public SettingsDetail getSettings() {
         return settings;
    }

     public void setSettings(SettingsDetail settings) {
         this.settings = settings;
     }

    public Long getSetting_id() {
        return setting_id;
    }

    public void setSetting_id(Long setting_id) {
        this.setting_id = setting_id;
    }
}
