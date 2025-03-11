package engine.utils;

public final class CollisionInterval {

    public double min;
    public double max;

    public CollisionInterval(double min, double max){
      this.min = min;
      this.max = max;
    }

    public boolean overlap (CollisionInterval other) {
      return min <= other.max && other.min <= max;
    }

    public Double overlapPolygon (CollisionInterval other) {
      Double aRight = this.max - other.min;
      Double aLeft = other.max - this.min;

      if (aLeft < 0 || aRight < 0) return null;
      if (aRight < aLeft) return aRight;
      else return -aLeft;
    }

    public boolean inRange (double p){
      return min <= p && p <= max;
    }

}
