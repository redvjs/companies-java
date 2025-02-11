package uk.gov.companieshouse.docsapp.model.company;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "registrationNumber")
public final class LimitedCompany extends Company {
    private int numberOfShares;
    private boolean plc;

    public LimitedCompany(String companyName, boolean active) {
        super(companyName, active);
    }
    public LimitedCompany(){}

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public boolean isPublic() {
        return plc;
    }

    public void setPublic(boolean plc) {
        this.plc = plc;
    }

}
