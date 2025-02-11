package uk.gov.companieshouse.docsapp.model.company;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "registrationNumber")
public final class LimitedLiabilityPartnership extends Company {
    private int numberOfPartners;

    public LimitedLiabilityPartnership(String companyName, boolean active) {
        super(companyName, active);
    }
    public LimitedLiabilityPartnership(){}

    public int getNumberOfPartners() {
        return numberOfPartners;
    }

    public void setNumberOfPartners(int numberOfPartners) {
        this.numberOfPartners = numberOfPartners;
    }
}
