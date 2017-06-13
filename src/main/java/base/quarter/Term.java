package base.quarter;

public enum Term {
    FALL(1), WINTER(2), SPRING(3), SUMMER(4);

  private int value;

  Term(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static Term fromValue(int val) {
    for (Term t : Term.values()){
      if (t.value == val) {
        return t;
      }
    }
    throw new IllegalArgumentException("No Term with val " + val);
  }
}
