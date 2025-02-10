package uk.gov.companieshouse.docsapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.companieshouse.docsapp.model.company.Company;

import java.util.List;


public interface CompanyRepository extends JpaRepository<Company, String> {

    boolean existsByCompanyName(String companyName);
    boolean existsById(String number);


}

