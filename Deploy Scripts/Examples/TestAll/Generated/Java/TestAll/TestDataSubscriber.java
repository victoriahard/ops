//Auto generated OPS-code. !DO NOT MODIFY!

package TestAll;

import java.io.IOException;
import ops.Subscriber;
import ops.OPSObject;
import ops.Topic;
import ops.OPSTopicTypeMissmatchException;

public class TestDataSubscriber extends Subscriber 
{
    public TestDataSubscriber(Topic<TestData> t) //throws ops.OPSTopicTypeMissmatchException
    {
        super(t);
        
    }

    public TestData getData()
    {
        return (TestData)super.getData();
    }
}