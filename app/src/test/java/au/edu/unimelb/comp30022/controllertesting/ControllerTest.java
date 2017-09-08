package au.edu.unimelb.comp30022.controllertesting;

import android.location.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created by william on 31/8/17.
 */
public class ControllerTest {
    @Before
    public void setUp() throws Exception {
        UI.addWidget("CALCULATE_BUTTON", new Button());

        EditText sourcePostCode = new EditText();

        sourcePostCode.setText("3055");

        UI.addWidget("SOURCE_POST_CODE", sourcePostCode);

        EditText destPostCode = new EditText();

        destPostCode.setText("3010");

        UI.addWidget("DESTINATION_POST_CODE", destPostCode);

        TextView costLabel = new TextView();

        UI.addWidget("COST_LABEL", costLabel);
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testCalculations() throws Exception {
        AddressTools addressToolsMock = Mockito.mock(AddressTools.class);
        PostcodeValidator postcodeValidatorMock = Mockito.mock(PostcodeValidator.class);
        PostageRateCalculator postageRateCalculatorMock = Mockito.mock(PostageRateCalculator.class);

        // pass mock objects as dependency to constructor
        Controller controller = new Controller(addressToolsMock, postcodeValidatorMock, postageRateCalculatorMock);

        // configure mock expected interation
        Mockito.when(postageRateCalculatorMock.computeCost(any(Location.class), any(Location.class))).thenReturn(5);
        Mockito.when(postcodeValidatorMock.isValid(any(String.class))).thenReturn(true);

        // call method being tested
        controller.calculateButtonPressed();

        // check if mock had expected interaction
        Mockito.verify(controller.postageRateCalculator).computeCost(any(Location.class), any(Location.class));

        // assert results
        assertEquals("$5", controller.costLabel.getText());
    }
}