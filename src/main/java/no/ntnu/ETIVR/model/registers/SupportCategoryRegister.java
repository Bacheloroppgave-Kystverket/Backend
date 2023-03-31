package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;

import java.util.List;

public interface SupportCategoryRegister {
    void addSupportCategory(SupportCategory supportCategory) throws CouldNotAddSupportCategoryException;

    void removeSupportCategory(SupportCategory supportCategory) throws CouldNotRemoveSupportCategoryException;

    SupportCategory getSupportCategoryById(long supportCategoryId) throws CouldNotGetSupportCategoryException;

    SupportCategory getSupportCategoryByName(String supportCategoryName) throws CouldNotGetSupportCategoryException;

    List<SupportCategory> getSupportCategories();
}
