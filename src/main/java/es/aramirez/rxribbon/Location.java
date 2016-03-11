package es.aramirez.rxribbon;

public class Location {
  private final Double latitude;
  private final Double longitude;

  public Location(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  @Override
  public String toString() {
    return String.format("%.2f:%.2f", getLatitude(), getLongitude());
  }
}