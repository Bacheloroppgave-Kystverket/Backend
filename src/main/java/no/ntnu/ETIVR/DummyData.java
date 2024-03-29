package no.ntnu.ETIVR;

import java.util.Random;
import lombok.SneakyThrows;
import no.ntnu.ETIVR.model.*;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.exceptions.*;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.registers.*;
import no.ntnu.ETIVR.model.services.*;
import no.ntnu.ETIVR.model.trackable.GazeData;
import no.ntnu.ETIVR.model.trackable.TrackableRecord;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.trackable.ViewDistance;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Adds dummy data to the setup.
 */
@Component
@Profile("!test")
public class DummyData implements ApplicationListener<ApplicationReadyEvent> {

    private final TrackableObjectsService trackableObjectsService;

    private final SessionRegister sessionRegister;

    private final Logger logger = Logger.getLogger("DummyInit");

    private Random random;

    /**
     * Makes an instance of dummy data
     * @param trackableObjectsService trackable object service
     * @param sessionService session service
     * @param simulationSetupService simulation setup service
     * @param userRegister user register
     * @param supportCategoryService support category service
     */
    public DummyData(TrackableObjectsService trackableObjectsService, SessionService sessionService, SimulationSetupService simulationSetupService, UserService userRegister, SupportCategoryService supportCategoryService) {

        this.trackableObjectsService = trackableObjectsService;
        this.sessionRegister = sessionService;
        this.random = new Random();
        try {
            addTrackableObjects(trackableObjectsService);
            addTestSimulationSetup(simulationSetupService, trackableObjectsService);
            addDefaultUsers(userRegister);
            addTestSession(simulationSetupService, userRegister);
            addSupportCategory(supportCategoryService);

        } catch (Exception couldNotAddTrackableObjectException) {
            logger.warning("Test data could not be added but got an " + couldNotAddTrackableObjectException.getClass().getSimpleName() + ".");
            couldNotAddTrackableObjectException.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!trackableObjectsService.getAllTrackableObjects().isEmpty()) {
            logger.info("Data already exists");
        } else {
            logger.info("importing test data...");
        }
    }

    /**
     * Adds support category dummy data
     * @param supportCategoryService SupportCategoryService
     * @throws CouldNotAddSupportCategoryException gets thrown if support category could not be added
     */
    private void addSupportCategory(SupportCategoryService supportCategoryService) throws CouldNotAddSupportCategoryException {
        if (supportCategoryService.getSupportCategories().isEmpty()) {
            List<SupportItem> suppoortItemsForMetrics = new ArrayList<>();
            suppoortItemsForMetrics.add(new SupportItem("Fixations", "A fixation is when you foucs on an object for a longer period of time. If you look at something stationary for longer than 50-200ms ++ it can be qualified as a fixation."));
            suppoortItemsForMetrics.add(new SupportItem("Fixation duration", "The fixation duration is the time that you have spent looking at an area of interest or an point in space."));
            suppoortItemsForMetrics.add(new SupportItem("Average fixation", "Average fixation duration is the average time spent at an area of interest or point in space. It can be found by taking the fixation duration and divide it by the amount of fixations."));
            suppoortItemsForMetrics.add(new SupportItem("Feedback timeframes", "Feedback timeframes is fixation duration over time. The numbers stands for feedback per 10 seconds. 1 is 10 seconds, 2 is 20 seconds and so on. "));
            SupportCategory supportCategory = new SupportCategory(0, "Eyetracking metrics", "Introduction to eyetracking.", suppoortItemsForMetrics, 2);

            List<SupportItem> supportItemsForProfile = new ArrayList<>();
            supportItemsForProfile.add(new SupportItem("Profile name", "This name is unique and represents the user. This is the name used to login to the application."));
            supportItemsForProfile.add(new SupportItem("Sessions done", "This is the number of sessions done in total."));
            supportItemsForProfile.add(new SupportItem("Total Time In Sessions", "This is the total time used in sessions in total."));
            supportItemsForProfile.add(new SupportItem("Session Type Done", "The session type depends on what type of session it is."));
            SupportCategory supportCategory1 = new SupportCategory(1, "Profile", "Information about your profile", supportItemsForProfile, 1);

            List<SupportItem> supportItemsForGraphs = new ArrayList<>();
            supportItemsForGraphs.add(new SupportItem("Bar graph", "A bar graph is a chart that uses rectangular bars to represent and compare different categories or quantities."));
            supportItemsForGraphs.add(new SupportItem("Pie graph", "A pie graph is a circular chart that illustrates the relative proportions or percentages of different categories or variables using slices of a pie."));
            supportItemsForGraphs.add(new SupportItem("Donut graph", "A donut graph, also called a donut chart, is a circular chart with a center hole. It represents data proportions using ring-shaped slices."));
            supportItemsForGraphs.add(new SupportItem("Polar graph", "A polar graph is a chart that represents data using radial and angular coordinates, typically used for plotting values in a circular format."));
            supportItemsForGraphs.add(new SupportItem("Radar graph", "A radar graph, also known as a spider chart, displays multivariate data on multiple axes emanating from a central point, facilitating comparison between variables."));
            supportItemsForGraphs.add(new SupportItem("Area graph", "An area graph is a chart that portrays quantitative data over time, using line segments and filled areas below the lines to represent values."));
            SupportCategory supportCategory2 = new SupportCategory(2, "Graph", "Example of bar graphs", supportItemsForGraphs, 3);

            //!TODO what is this
            List<SupportItem> supportItemsForFilterCard = new ArrayList<>();
            //supportItemsForGraphs.add(new SupportItem("User", "Filter by users"));
            SupportCategory supportCategory3 = new SupportCategory(3, "Filter card", "How to filter sessions", supportItemsForFilterCard, 4);

            supportCategoryService.addSupportCategory(supportCategory);
            supportCategoryService.addSupportCategory(supportCategory1);
            supportCategoryService.addSupportCategory(supportCategory2);

        }
    }

    /**
     * Adds default users to the backend.
     * @param userRegister the user register.
     */
    private void addDefaultUsers(UserRegister userRegister) throws CouldNotAddUserException {
        for (int i = 0; i < 5; i++) {
            User user = new User(5000, "HeiHeiHei" + i, "Password1");
            userRegister.addNewUser(user);
        }
    }

    /**
     * Adds default test simulation setups.
     * @param simulationSetupService the setup simulation register.
     * @param trackableObjectRegister the trackable objects register.
     * @throws CouldNotAddSimulationSetupException gets thrown if the simulation setup could not be added.
     */
    private void addTestSimulationSetup(SimulationSetupService simulationSetupService, TrackableObjectRegister trackableObjectRegister) throws CouldNotAddSimulationSetupException {
        List<TrackableObject> trackableObjects = trackableObjectRegister.getAllTrackableObjects();
        List<ReferencePosition> referencePositions = makeReferencePositions();
        for (int i = 0; i < 1; i++) {
            simulationSetupService.addSimulationSetup(new SimulationSetup("Tugboat", trackableObjects, referencePositions));
        }
    }


    /**
     * Adds test sessions to the database.
     * @param simulationSetupRegister the simulation setup register.
     */
    private void addTestSession(SimulationSetupRegister simulationSetupRegister, UserRegister userRegister) {
        try {
            SimulationSetup simulationSetup = simulationSetupRegister.getSimulationSetups().get(0);
            User user = userRegister.getAllUsers().get(0);
            int amount = 0;
            for (int i = 0; i < 4; i++) {
                List<TrackableObject> trackableObjects = simulationSetup.getCloseTrackableObjects();
                List<ReferencePosition> referencePositions = simulationSetup.getReferencePositionList();
                Session session = new Session(LocalDateTime.now().minusDays(i % 2 == 0 ? 3 : 0), user, 500000, makeTrackableLog(trackableObjects, referencePositions), makePositionLog(referencePositions, simulationSetup.getCloseTrackableObjects(), amount),
                        simulationSetup);
                session.setUser(user);
                sessionRegister.addSession(session);
                if (i == 1) {
                    user = userRegister.getAllUsers().get(1);
                }
                amount++;
            }
        } catch (CouldNotAddSessionException couldNotAddSessionException) {
            logger.warning("The default session could not be added.");
        }
    }

    /**
     * Makes position log
     * @param referencePositions list of reference positions
     * @param trackableObjects list of trackable objects
     * @param amount int
     * @return list of position records
     */
    private List<PositionRecord> makePositionLog(List<ReferencePosition> referencePositions, List<TrackableObject> trackableObjects, int amount) {
        List<PositionRecord> positionRecords = new ArrayList<>();

        for (ReferencePosition referencePosition : referencePositions) {
            positionRecords.add(new PositionRecord(referencePosition, 20, makeAdaptiveFeedback(trackableObjects, amount)));

        }
        return positionRecords;
    }

    /**
     * Makes position configuration
     * @return position configuration
     */
    public PositionConfiguration makePositionConfiguration() {
        return new PositionConfiguration(makeCategoryFeedback());
    }

    /**
     * Makes a default set of reference positions.
     * @return the reference positions.
     */
    private List<ReferencePosition> makeReferencePositions() {
        List<ReferencePosition> referencePositions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            referencePositions.add(new ReferencePosition(500000, "Seat " + i, makePositionConfiguration()));
        }
        return referencePositions;
    }

    /**
     * Makes the feedback configurations for the seats.
     * @return the feedback configurations
     */
    private List<CategoryConfiguration> makeCategoryFeedback() {
        List<CategoryConfiguration> categoryConfigurations = new ArrayList<>();
        for (TrackableType trackableType : TrackableType.values()) {
            categoryConfigurations.add(new CategoryConfiguration(trackableType, 0.2f));
        }
        return categoryConfigurations;
    }

    /**
     * Makes default adaptive feedbacks.
     * @param trackableObjects the trackable objects.
     * @return the adaptive feedback.
     */
    private List<AdaptiveFeedback> makeAdaptiveFeedback(List<TrackableObject> trackableObjects, int amount) {
        List<AdaptiveFeedback> adaptiveFeedbacks = new ArrayList<>();
        int time = 40;
        for (int i = amount; i < trackableObjects.size(); i++) {
            if (amount >= 2 && i < 4 || amount < 2) {
                adaptiveFeedbacks.add(new AdaptiveFeedback(time, makeCategoryFeedback(time)));
            }
        }
        return adaptiveFeedbacks;
    }

    /**
     * Makes default category feedbacks.
     * @param time the total time.
     * @return the list of the category feedbacks
     */
    private List<CategoryFeedback> makeCategoryFeedback(int time) {
        List<CategoryFeedback> categoryFeedbacks = new ArrayList<>();
        int currentValue = 0;
        int newValue = 0;
        int valueToSet = 0;

        for (TrackableType trackableType : TrackableType.values()) {
            newValue = random.nextInt(0, time / 4);
            valueToSet = newValue;
            currentValue += newValue;
            if (currentValue > time) {
                valueToSet = 0;
            }
            categoryFeedbacks.add(new CategoryFeedback(trackableType, valueToSet));
        }
        return categoryFeedbacks;
    }

    /**
     * Makes a set of predefined trackable objects.
     * @return the trackable objects
     */
    private List<TrackableRecord> makeTrackableLog(List<TrackableObject> trackableObjectList, List<ReferencePosition> referencePositions) {
        List<TrackableRecord> trackableObjects = new ArrayList<>();
        referencePositions.forEach(referencePosition ->
                trackableObjectList.forEach(trackableObject -> {
                    List<GazeData> gazeDataList = new ArrayList<>();
                    int fixations = random.nextInt(10, 100);
                    float fixationDuration = random.nextInt(10, 100);

                    gazeDataList.add(new GazeData(fixations, fixationDuration, referencePosition));
                    trackableObjects.add(new TrackableRecord(gazeDataList, ViewDistance.CLOSE, trackableObject));
                }));
        return trackableObjects;
    }

    /**
     * Makes a default trackable object based on the name.
     * @param nameOfObject the name of the object.
     * @return the trackable object.
     */
    private TrackableObject makeTrackableObject(String nameOfObject, TrackableType trackableType){
        return new TrackableObject(nameOfObject, trackableType, 500000);
    }

    /**
     * Adding trackable logs test data
     * @param trackableObjectRegister Track
     * @throws CouldNotAddTrackableObjectException
     */
    public void addTrackableObjects(TrackableObjectRegister trackableObjectRegister) throws CouldNotAddTrackableObjectException {
        checkIfObjectIsNull(trackableObjectRegister);
        if (trackableObjectRegister.getAllTrackableObjects().isEmpty()) {
            for (TrackableObject trackableObject : makeDefaultTrackableObjects()) {
                trackableObjectRegister.addTrackableObject(trackableObject);
            }
        }
    }

    /**
     * Makes default trackable objects.
     * @return the list of the trackable objects.
     */
    public List<TrackableObject> makeDefaultTrackableObjects() {
        List<TrackableObject> trackableObjects = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            trackableObjects.add(makeTrackableObject("Pog " + (i + 1), TrackableType.WALL));
            trackableObjects.add(makeTrackableObject("navn" + (i + 1), TrackableType.WINDOW));
            trackableObjects.add(makeTrackableObject("Hei" + (i + 1), TrackableType.OTHER));
        }
        return trackableObjects;
    }



    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("The " + "trackable object register" + " cannot be null.");
        }
    }
}
