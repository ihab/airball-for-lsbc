public class VaryingValue {

    private static final double COUNTS_PER_CYCLE = 16000;

    private final float mMin;
    private final float mMax;
    private long mCount;
    private float mValue;

    public VaryingValue(float min, float max) {
        mMin = min;
        mMax = max;
        computeValue();
    }

    public float getValue() {
        return mValue;
    }

    public void update(long t) {
        mCount = t;
        computeValue();
    }

    private void computeValue() {
        double ratio = (Math.sin(mCount / COUNTS_PER_CYCLE * 2 * Math.PI) + 1) / 2;
        double value = mMin + ratio * (mMax - mMin);
        mValue = (float) value;
    }

    public static void main(String[] argv) {
        VaryingValue v = new VaryingValue(50, 100);
        while (true) {
            int x = (int) v.getValue();
            String  s = "";
            for (int i = 0; i < x; i++) { s += "."; }
            System.out.println("" + v.getValue() + " " + s);
            v.update(System.currentTimeMillis());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.exit(-1);
            }
        }
    }
}
