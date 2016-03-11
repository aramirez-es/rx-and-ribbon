package es.aramirez.rxribbon;

public class ServiceRequest {
  private final String userName;
  private final Double latitude;
  private final Double longitude;
  private final Integer itemId;

  public ServiceRequest(String userName, Double latitude, Double longitude, Integer itemId) {
    this.userName = userName;
    this.latitude = latitude;
    this.longitude = longitude;
    this.itemId = itemId;
  }

  public String getUserName() {
    return userName;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Integer getItemId() {
    return itemId;
  }
}
