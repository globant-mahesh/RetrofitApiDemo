package commonutilities;

import com.aventstack.extentreports.Status;

public interface Configurable {
    void reportTest(String methodName, Status status);
}
