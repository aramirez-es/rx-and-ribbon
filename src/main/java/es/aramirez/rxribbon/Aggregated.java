package es.aramirez.rxribbon;

class Aggregated {
  private final User user;
  private final Location location;
  private final Item item;

  public Aggregated(User user, Location location, Item item) {
    this.user = user;
    this.location = location;
    this.item = item;
  }

  public String getUserName() {
    return user.getName();
  }

  public Location getCoordinates() {
    return this.location;
  }

  public Integer getItemId() {
    return item.getId();
  }

  @Override
  public String toString() {
    return String.format("Name: %s; Location: %s; Item: %d",
      getUserName(),
      getCoordinates(),
      getItemId()
    );
  }
}
