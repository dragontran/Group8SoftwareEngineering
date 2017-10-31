using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftEngMidterm;

// student 1 dev time for factory tests 30 minutes
namespace DesignPatterns
{
    [TestClass]
    public class TestFactoryStackArray
    {

        // T1
        [TestMethod]
        public void TestFactoryStackArrayCreation()
        {

            // create new stack array object using stack factory
            stackFactory stackArray = new stack();

            // A default stack array should have length 12 and it's
            // size should be set to -1
            Assert.AreEqual((stackArray).getStackObject().GetItemLength(), new int[12].Length);
            Assert.AreEqual((stackArray).getStackObject().size, -1);
        }

        // T2 
        [TestMethod]
        public void TestGetStackObject()
        {
            // create new stack array object using stack factory
            stackFactory stackArray = new stack();

            // test if returns object of type stack array 
            Assert.IsInstanceOfType((stackArray).getStackObject(), typeof(StackArray));
        }
    }
}
