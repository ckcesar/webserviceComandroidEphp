package com.example.n1e.webservice.modelo;

import java.io.Serializable;

/**
 * Created by n1e on 28/04/17.
 */

public class UserPass implements Serializable {

    private int codigo;
    private String id;
    private String email;
    private String password_hash;
    private String customer_id;

    public UserPass(){}

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return this.getEmail();
    }

}
