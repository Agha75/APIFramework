package resources;

public enum ApiResources {
    AddPlaceAPI("/maps/api/place/add/json"),
    GetPlaceAPI("/maps/api/place/get/json"),
    DeletePlaceAPI("/maps/api/place/delete/json");

    private final String resource;

    ApiResources(String resource) {
        this.resource=resource;
    }

    public String getResource(){
        return resource;
    }
}
