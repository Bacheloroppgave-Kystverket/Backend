package no.ntnu.ETIVR.model.services;

import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;
import no.ntnu.ETIVR.model.registers.SupportCategoryRegister;
import no.ntnu.ETIVR.model.repository.SupportCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a support category service that handles JPA interaction.
 */
@Service
public class SupportCategoryService implements SupportCategoryRegister {
    private final SupportCategoryRepository supportCategoryRepository;

    /**
     * Makes an instance of the support category service class.
     * @param supportCategoryRepository the support category repository.
     */
    public SupportCategoryService(SupportCategoryRepository supportCategoryRepository) {
        checkIfObjectIsNull(supportCategoryRepository, "support category repository");
        this.supportCategoryRepository = supportCategoryRepository;
    }

    @Override
    public void addSupportCategory(SupportCategory supportCategory) throws CouldNotAddSupportCategoryException {
        checkSupportCategory(supportCategory);
        if (!supportCategoryRepository.existsById(supportCategory.getSupportCategoryId())) {
            supportCategoryRepository.save(supportCategory);
        } else {
            throw new CouldNotAddSupportCategoryException("There is already a support category with " + supportCategory.getSupportCategoryId() + " in the register");
        }

    }

    @Override
    public void removeSupportCategory(SupportCategory supportCategory) throws CouldNotRemoveSupportCategoryException {
        checkSupportCategory(supportCategory);
        if (supportCategoryRepository.existsById(supportCategory.getSupportCategoryId())) {
            supportCategoryRepository.delete(supportCategory);
        } else {
            throw new CouldNotRemoveSupportCategoryException("The support category with id " + supportCategory.getSupportCategoryId() + " is not in the register.");
        }

    }

    @Override
    public SupportCategory getSupportCategoryById(long supportCategoryId) throws CouldNotGetSupportCategoryException {
        checkFloat(supportCategoryId, "support category id");
        Optional<SupportCategory> optionalSupportCategory = supportCategoryRepository.findById(supportCategoryId);
        if (optionalSupportCategory.isEmpty()) {
            throw new CouldNotGetSupportCategoryException("The support category with the id " + supportCategoryId + " is not in the register");
        }
        return optionalSupportCategory.get();
    }


    @Override
    public List<SupportCategory> getSupportCategories() {
        return new ArrayList<>(supportCategoryRepository.findAll());
    }

    /**
     * Checks if the support category is null.
     * @param supportCategory the support category
     */
    public void checkSupportCategory(SupportCategory supportCategory) {
        checkIfObjectIsNull(supportCategory, "support category");
    }

    /**
     * Checks if a float is above zero.
     * @param numberToCheck the number to check.
     * @param error the error.
     */
    public void checkFloat(float numberToCheck, String error) {
        if (numberToCheck < 0) {
            throw new IllegalArgumentException("The " + error + " cannot be below zero");
        }
    }


    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
