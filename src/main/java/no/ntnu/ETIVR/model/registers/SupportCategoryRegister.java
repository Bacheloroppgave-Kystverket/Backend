package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;

import java.util.List;

public interface SupportCategoryRegister {
    /**
     * Adds a support category to the register
     * @param supportCategory the support category
     * @throws CouldNotAddSupportCategoryException gets thrown if the support category already is in the register.
     */
    void addSupportCategory(SupportCategory supportCategory) throws CouldNotAddSupportCategoryException;

    /**
     * Removes a support category from the register
     * @param supportCategory the support category to be removed
     * @throws CouldNotRemoveSupportCategoryException gets thrown if support category does not exist.
     */
    void removeSupportCategory(SupportCategory supportCategory) throws CouldNotRemoveSupportCategoryException;

    /**
     * Gets support category by id
     * @param supportCategoryId the id of the support category
     * @return the support category
     * @throws CouldNotGetSupportCategoryException gets thrown if the support category with the id does not exist.
     */
    SupportCategory getSupportCategoryById(long supportCategoryId) throws CouldNotGetSupportCategoryException;

    /**
     * Gets all the support categories in the register
     * @return the support category
     */
    List<SupportCategory> getSupportCategories();
}
