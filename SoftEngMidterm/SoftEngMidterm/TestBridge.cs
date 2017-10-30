using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SoftEngMidterm
{
    // Student 3: 45 minutes of dev time
    [TestClass]
    public class TestBridge
    {
        //[TestMethod]
        //public void TestNodeCreation()
        //{
        //    int value = 1;
        //    Node node = new Node(1);

        //    // Newly created node should contain the value provided
        //    // and its previous and next nodes should be null
        //    Assert.AreEqual(node.value, value);
        //    Assert.AreEqual(node.prev, null);
        //    Assert.AreEqual(node.next, null);
        //}

        [TestMethod]
        public void TestDefaultStackArrayCreation()
        {
            StackArray stackArray = new StackArray();

            // A default stack array should have length 12 and it's
            // size should be set to -1
            Assert.AreEqual(stackArray.GetItemLength(), new int[12].Length);
            Assert.AreEqual(stackArray.size, -1);
        }

        [TestMethod]
        public void TestStackArrayWithProvidedNumberOfCells()
        {
            int cells = 15;
            StackArray stackArray = new StackArray(cells);

            // A stack array should have length the provided length of cells 
            // and it's size should be set to -1
            Assert.AreEqual(stackArray.GetItemLength(), new int[cells].Length);
            Assert.AreEqual(stackArray.size, -1);

            cells = -1;
            stackArray = new StackArray(cells);

            // A stack array with cells input of 0 or below should have length 12 
            // and it's size should be set to -1
            Assert.AreEqual(stackArray.GetItemLength(), new int[12].Length);
            Assert.AreEqual(stackArray.size, -1);
        }

        [TestMethod]
        public void TestStackArrayPush()
        {
            StackArray stackArray = new StackArray(2);

            stackArray.push(1);

            // the first element of the stack array should be the first value pushed 
            // and size should be increased to 0 (from -1)
            Assert.AreEqual(stackArray.peek(), 1);
            Assert.AreEqual(stackArray.size, 0);

            stackArray.push(2);

            // the second element of the stack array should be the second value pushed 
            // and size should be increased to 1 (from 0), 
            // and the stack array should now be full
            Assert.AreEqual(stackArray.peek(), 2);
            Assert.AreEqual(stackArray.size, 1);
            Assert.AreEqual(stackArray.isFull, true);

            stackArray.push(3);

            // the third value should not have been pushed because the stack is full
            Assert.AreEqual(stackArray.size, 1);
        }

        [TestMethod]
        public void TestStackArrayPushNegative()
        {
            StackArray stackArray = new StackArray();

            stackArray.push(-2);

            // negative values should be converted to positive values when being pushed
            Assert.AreEqual(2, stackArray.pop());
        }

        [TestMethod]
        public void TestStackArrayIsEmpty()
        {
            StackArray stackArray = new StackArray();

            // isEmpty should report true if nothing has been added
            Assert.AreEqual(stackArray.isEmpty, true);

            stackArray.push(1);

            // isEmpty should report false if something has been added
            Assert.AreEqual(stackArray.isEmpty, false);
        }

        [TestMethod]
        public void TestStackArrayIsFull()
        {
            // Create a stack array with 2 cells
            StackArray stackArray = new StackArray(2);

            // Initially the stack array should not be full
            Assert.AreEqual(stackArray.isFull, false);

            stackArray.push(1);

            // After pushing one element the stackArray should still not be full
            Assert.AreEqual(stackArray.isFull, false);

            stackArray.push(1);

            // After adding two elements the stack array should be full
            Assert.AreEqual(stackArray.isFull, true);
        }

        [TestMethod]
        public void TestStackArrayPeek()
        {
            // Create a stack array with 2 cells
            StackArray stackArray = new StackArray(2);

            // Stack array returns -1 if empty
            Assert.AreEqual(stackArray.peek(), -1);

            //need to add something so we can pop it
            stackArray.push(1);
            stackArray.push(2);

            // peeking on a stack array retuns the element at top of the stack
            // Size should be 1 peeking (because it is not changed)
            Assert.AreEqual(stackArray.peek(), 2);
            Assert.AreEqual(stackArray.size, 1);

        }

        [TestMethod]
        public void TestStackArrayPop()
        {
            // Create a stack array with 2 cells
            StackArray stackArray = new StackArray(2);

            // Stack array returns -1 if nothing was added
            Assert.AreEqual(stackArray.pop(), -1);

            //need to add something so we can pop it
            stackArray.push(1);
            stackArray.push(2);

            // popping on a stack array retuns the element at top of the stack after "removal"
            // Size should be 0 after popping (decremented from 1 after the 2 pushes)
            Assert.AreEqual(stackArray.pop(), 2);
            Assert.AreEqual(stackArray.size, 0);

        }


        //[TestMethod]
        //public void TestStackListPush()
        //{
        //    StackList stackList = new StackList();

        //    stackList.push(1);

        //    // peek returns the top of the stack (aka the value pushed)
        //    Assert.AreEqual(stackList.peek(), 1);

        //    stackList.push(2);

        //    // ensure pushing updated properly
        //    Assert.AreEqual(stackList.peek(), 2);
        //   // Assert.AreEqual(stackList.last.prev.value, 1);
        //}

        //[TestMethod]
        //public void TestStackListIsEmpty()
        //{
        //    StackList stackList = new StackList();

        //    // nothing added, should return true
        //    Assert.AreEqual(stackList.isEmpty(), true);

        //    stackList.push(1);

        //    // something added, should return false
        //    Assert.AreEqual(stackList.isEmpty(), false);
        //}

        //[TestMethod]
        //public void TestStackListIsFull()
        //{
        //    StackList stackList = new StackList();

        //    // always returns false, no max size for a stack list
        //    Assert.AreEqual(stackList.isFull(), false);
        //}

        //[TestMethod]
        //public void TestStackListPeek()
        //{
        //    StackList stackList = new StackList();

        //    // peek returns -1 on empty lists
        //    Assert.AreEqual(stackList.peek(), -1);

        //    stackList.push(1);

        //    // peek returns the top of the stack (aka the value pushed)
        //    Assert.AreEqual(stackList.peek(), 1);
        //}

        //[TestMethod]
        //public void TestStackListPop()
        //{
        //    StackList stackList = new StackList();

        //    // pop returns -1 on empty lists
        //    Assert.AreEqual(stackList.pop(), -1);

        //    stackList.push(1);
        //    stackList.push(2);

        //    // pop returns the top of the stack (aka the last value pushed)
        //    // and removes it from the stack 
        //    Assert.AreEqual(stackList.pop(), 2);
        //    Assert.AreEqual(stackList.peek(), 1);
        //}

        //[TestMethod]
        //public void TestStackListPushNegative()
        //{
        //    StackList stackList = new StackList();

        //    stackList.push(-2);

        //    Assert.AreEqual(2, stackList.pop());
        //}


        [TestMethod]
        public void TestStackFIFOPop()
        {
            StackFIFO stackFIFO = new StackFIFO();

            // if empty pop returns -1
            Assert.AreEqual(stackFIFO.pop(), -1);

            stackFIFO.push(1);
            stackFIFO.push(2);
            stackFIFO.push(3);

            // should be poped in order they were pushed
            Assert.AreEqual(stackFIFO.pop(), 1);
            Assert.AreEqual(stackFIFO.pop(), 2);
            Assert.AreEqual(stackFIFO.pop(), 3);
        }


        [TestMethod]
        public void TestStackHanoiReportRejected()
        {
            StackHanoi stackHanoi = new StackHanoi();

            // initially 0 are rejected
            Assert.AreEqual(stackHanoi.reportRejected(), 0);
        }

        [TestMethod]
        public void TestStackHanoiPush()
        {
            StackHanoi stackHanoi = new StackHanoi();

            stackHanoi.push(2);

            // nothing should be rejected after the first thing
            Assert.AreEqual(stackHanoi.reportRejected(), 0);
            Assert.AreEqual(stackHanoi.peek(), 2);


            stackHanoi.push(3);

            // value pushed is larger than top of stack so report rejected is increased
            // and top remains the same as before
            Assert.AreEqual(stackHanoi.reportRejected(), 1);
            Assert.AreEqual(stackHanoi.peek(), 2);


            stackHanoi.push(1);

            // top of the stack is updated with the pushed value
            Assert.AreEqual(stackHanoi.peek(), 1);
        }
    }
}
