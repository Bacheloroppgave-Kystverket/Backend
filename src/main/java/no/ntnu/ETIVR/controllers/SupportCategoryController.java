package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;
import no.ntnu.ETIVR.model.registers.SupportCategoryRegister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supportCategory")
@CrossOrigin
public class SupportCategoryController {
    private SupportCategoryRegister supportCategoryRegister;

    public SupportCategoryController(SupportCategoryRegister supportCategoryRegister) {
        checkIfObjectIsNull(supportCategoryRegister, "support category register");
        this.supportCategoryRegister = supportCategoryRegister;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<SupportCategory> getAllSupportCategories() {
        return supportCategoryRegister.getSupportCategories();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public void addSupportCategory(@RequestBody String body)
            throws JsonProcessingException, CouldNotAddSupportCategoryException {
        SupportCategory supportCategory = makeSupportCategory(body);
        supportCategoryRegister.addSupportCategory(supportCategory);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public SupportCategory getSupportCategoryById(@PathVariable("id") long id) throws CouldNotGetSupportCategoryException {
        return supportCategoryRegister.getSupportCategoryById(id);
    }

    public void deleteSupportCategory(@RequestParam(value = "support-category") SupportCategory supportCategory)
            throws CouldNotRemoveSupportCategoryException {
        supportCategoryRegister.removeSupportCategory(supportCategory);
    }

    /**
     * Makes a support category from a json string.
     * @param body the json string.
     * @return the support category.
     * @throws JsonProcessingException gets thrown if the support category is invalid format.
     */
    private SupportCategory makeSupportCategory(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, SupportCategory.class);
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }

}
