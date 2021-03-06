package com.spilna.sprava.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ivanov Eduard
 * @version 1.0
 */
// This annotation, we specify that the class is an entity.
@Entity
/**
 * This annotation, we point out that for this
 * entity in the database corresponds to a table named 'user'
 */
@Table(name = "user")
public class User {
	@Id
	@Column(name = "id_user")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "token")
	private String token;
	private String city;
	private String region;
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    private Set<Post> postSet;


	
	public String getId() {
		return id;
	}

	public void setId(String idU) {
		this.id = idU;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
