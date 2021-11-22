package stepDefinitions;


import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        AddPaceStepDefinition m =new AddPaceStepDefinition();
        //if variable is static then then that variable will be call called by call name not by object of class
        if(AddPaceStepDefinition.place_Id==null) {
            m.add_place_payload("New", "Pashto", "abc town");
            m.user_calls_with_http_request("AddPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("New", "GetPlaceAPI");

        }
    }
}
