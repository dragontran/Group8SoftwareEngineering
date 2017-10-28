using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftEngMidterm;


namespace DesignPatterns
{

    [TestClass]
    public class TestFactoryStackArray
    {

        [TestMethod]
        public void TestFactoryStackArrayCreation()
        {

            stackFactory stackArray = new stack();

            Assert.AreEqual((stackArray).getStackObject().GetItemLength(), new int[12].Length);
            Assert.AreEqual((stackArray).getStackObject().GetSize(), -1);
        }

        [TestMethod]
        public void TestGetStackObject()
        {
            stackFactory stackArray = new stack();

            Assert.IsInstanceOfType((stackArray).getStackObject(), typeof(StackArray));
        }
    }
}
