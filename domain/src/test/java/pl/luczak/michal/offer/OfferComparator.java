package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.util.Comparator;

class OfferComparator implements Comparator<OfferDTO> {


    @Override
    public int compare(OfferDTO o1, OfferDTO o2) {
        int urlComparison = o1.url().compareToIgnoreCase(o2.url());
        if (urlComparison != 0) {
            return urlComparison;
        }

        int companyComparison = o1.companyName().compareToIgnoreCase(o2.companyName());
        if (companyComparison != 0) {
            return companyComparison;
        }

        int salaryComparison = o1.salary().compareToIgnoreCase(o2.salary());
        if (salaryComparison != 0) {
            return salaryComparison;
        }

        return o1.jobName().compareToIgnoreCase(o2.jobName());
    }
}
