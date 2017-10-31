using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftEngMidterm;


// Student 5: Write/comment all initial tests 2 hours 15 mins of dev time
// Student 3: 45 minutes of dev time    
// Student 2: 30 mins of dev time
namespace DesignPatterns
{
    [TestClass]
    public class TestBridge
    {
        // T1
        [TestMethod]
        public void TestDefaultStackArrayCreation()
        {
            StackArray stackArray = new StackArray();

            // A default stack array should have length 12 and it's
            // size should be set to -1
            Assert.AreEqual(stackArray.GetItemLength(), new int[12].Length);
            Assert.AreEqual(stackArray.size, -1);
        }

        // T2
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

        // T3
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

        // T4
        [TestMethod]
        public void TestStackArrayPushNegative()
        {
            StackArray stackArray = new StackArray();

            stackArray.push(-2);

            // negative values should be converted to positive values when being pushed
            Assert.AreEqual(2, stackArray.pop());
        }

        // T5
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

        // T6
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

        // T7
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

        // T8
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

        // T9
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

        // T10
        [TestMethod]
        public void TestStackHanoiReportRejected()
        {
            StackHanoi stackHanoi = new StackHanoi();

            // initially 0 are rejected
            Assert.AreEqual(stackHanoi.reportRejected(), 0);
        }

        // T11
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
