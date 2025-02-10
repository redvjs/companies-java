package uk.gov.companieshouse.docsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.companieshouse.docsapp.dao.CompanyRegistry;
import uk.gov.companieshouse.docsapp.dao.InMemoryCompanyRegistry;
import uk.gov.companieshouse.docsapp.dao.JpaCompanyRegistry;
import uk.gov.companieshouse.docsapp.model.company.Company;

import java.util.List;
import java.util.Objects;

@RestController
public class CompaniesController {

    @Autowired
    private JpaCompanyRegistry jpaCompanyRegistry;

    @GetMapping("/companies")
    public List<Company> listCompanies(@RequestParam(required = false)String namePattern, @RequestParam(required = false)Integer yearOfIncorporation, @RequestParam(required = false)Boolean activeState, @RequestParam(required = false)CompanyRegistry.Type companyType, @RequestParam(required = false)CompanyRegistry.Sort sortBy) {
        return jpaCompanyRegistry.getCompanies(namePattern, yearOfIncorporation, activeState, companyType, sortBy);
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> newCompany (@RequestBody Company newCompany){
        if(newCompany.getRegistrationNumber() != null || newCompany.getIncorporatedOn() != null){
            return ResponseEntity.badRequest().build();
        }
        jpaCompanyRegistry.addCompany(newCompany);
        return ResponseEntity.ok().body(newCompany);
    }

    @GetMapping("/companies/{number}")
    public Company listCompany (@PathVariable String number) {
        return jpaCompanyRegistry.getCompany(number);
    }

    @PutMapping("/companies/{number}")
    public ResponseEntity<Company> updateCompany(@PathVariable String number, @RequestBody Company updatedCompany){

        System.out.println("Updated Company: " + updatedCompany);

        updatedCompany.setRegistrationNumber(number);
        updatedCompany.setIncorporatedOn(jpaCompanyRegistry.getCompany(number).getIncorporatedOn());

        if (!Objects.equals(number, updatedCompany.getRegistrationNumber())){
            System.out.println("Non equal reg num: " + number + " : " + updatedCompany.getRegistrationNumber());
            return ResponseEntity.badRequest().build();
        }
        jpaCompanyRegistry.editCompany(number, updatedCompany);

        System.out.println("Company after editing: " + updatedCompany);
        return ResponseEntity.ok().body(updatedCompany);
    }

    @PatchMapping("/companies/{number}")
    public ResponseEntity<Company> patchCompany(@PathVariable String number, @RequestBody Company patchedCompany){
        if (number.equals(patchedCompany.getRegistrationNumber())){
            return ResponseEntity.badRequest().build();
        }
        jpaCompanyRegistry.patchCompany(number, patchedCompany);
        Company company = jpaCompanyRegistry.getCompany(number);
        return ResponseEntity.ok().body(company);
    }

    @DeleteMapping("/companies/{number}")
    public ResponseEntity<Company> deleteCompany(@PathVariable String number){
        jpaCompanyRegistry.deleteCompany(number);
        return ResponseEntity.noContent().build();
    }
 }
