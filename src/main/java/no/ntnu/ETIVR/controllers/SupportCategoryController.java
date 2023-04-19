package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;
import no.ntnu.ETIVR.model.registers.SupportCategoryRegister;
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
    @CrossOrigin
    public List<SupportCategory> getAllSupportCategories() {
        return supportCategoryRegister.getSupportCategories();
    }

    @PostMapping
    public void addSupportCategory(@RequestBody String body)
            throws JsonProcessingException, CouldNotAddSupportCategoryException {
        SupportCategory supportCategory = makeSupportCategory(body);
        supportCategoryRegister.addSupportCategory(supportCategory);
    }

    @GetMapping("/{id}")
    public SupportCategory getSupportCategoryById(@PathVariable("id") long id) throws CouldNotGetSupportCategoryException {
        return supportCategoryRegister.getSupportCategoryById(id);
    }

    public void deleteSupportCategory(@RequestParam(value = "support-category") SupportCategory supportCategory)
            throws CouldNotRemoveSupportCategoryException {
        supportCategoryRegister.removeSupportCategory(supportCategory);
    }

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
