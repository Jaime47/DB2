package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class ServicePackage extends AbstractEntity {

    @Lob
    private String icon;
    private String name;

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
