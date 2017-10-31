using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftEngMidterm;


namespace DesignPatterns
{
    // student 1 dev time for factory tests 30 minutes
    [TestClass]
    public class TestFactoryStackArray
    {

        // T1
        [TestMethod]
        public void TestFactoryStackArrayCreation()
        {

            stackFactory stackArray = new stack();

            Assert.AreEqual((stackArray).getStackObject().GetItemLength(), new int[12].Length);
            Assert.AreEqual((stackArray).getStackObject().size, -1);
        }

        // T2 
        [TestMethod]
        public void TestGetStackObject()
        {
            stackFactory stackArray = new stack();

            Assert.IsInstanceOfType((stackArray).getStackObject(), typeof(StackArray));
        }
    }
}
