package com.example.application.data.service;

import com.example.application.data.entity.ServicePackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ServicePackageService extends CrudService<ServicePackage, Integer> {

    private ServicePackageRepository repository;

    public ServicePackageService(@Autowired ServicePackageRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ServicePackageRepository getRepository() {
        return repository;
    }

}
