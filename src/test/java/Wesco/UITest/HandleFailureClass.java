package Wesco.UITest;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class HandleFailureClass implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("Test failed: " + context.getDisplayName());
        // Call your function here
        myCustomFunction(context, cause);
    }

   // nothing to do on success of tests
    // if test fails run my custom function
   private void myCustomFunction(ExtensionContext context, Throwable cause) {
       // Example: take screenshot, log, cleanup
       // can also include screenshot class to add in report.
       System.out.println("Test Failed : " + context.getDisplayName());
   }
}
