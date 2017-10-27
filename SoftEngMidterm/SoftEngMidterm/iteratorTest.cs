using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftEngMidterm;

namespace DesignPatterns
{
    [TestClass]
    public class TestIntSet
    {
        [TestMethod]
        public void TestIterationFirstWithOneElement()
        {
            IntSet set = new IntSet();
            set.add(2);
            
            IntSet.Iterator it1 = set.createIterator();

            //  Newly created iterator should point to the first number in the list
            it1.first();
            Assert.AreEqual(it1.currentValue(), 2);
        }

        [TestMethod]
        public void TestIterationFirstWithMoreThanOneElement()
        {
            IntSet set = new IntSet();
            set.add(2);
            set.add(3);
            
            IntSet.Iterator it1 = set.createIterator();

            //  Newly created iterator should point to the first number that was added into the list
            it1.first();
            Assert.AreEqual(it1.currentValue(), 2);
        }

        [TestMethod]
        public void TestIterationIsDoneWithOneElement()
        {
            IntSet set = new IntSet();
            set.add(2);
            
            IntSet.Iterator it1 = set.createIterator();

            it1.first();
            Assert.AreEqual(it1.currentValue(), 2);

            //  Iterator is still set to the first value so isDone() should return false
            Assert.AreEqual(it1.isDone(), false);

            //  Try and move to non-existent element, should set currentValue() to -1
            it1.next();
            Assert.AreEqual(it1.currentValue(), -1);
            
            //  Iterator tried to go to the second element, but it doesn't exist so isDone should now return true
            Assert.AreEqual(it1.isDone(), true);

        }

        [TestMethod]
        public void TestIterationNextAndIsDoneWithTwoElement()
        {
            IntSet set = new IntSet();
            set.add(2);
            set.add(3);

            IntSet.Iterator it1 = set.createIterator();

            it1.first();
            Assert.AreEqual(it1.currentValue(), 2);

            //  Iterator is still set to the first value so isDone() should return false
            Assert.AreEqual(it1.isDone(), false);

            //  Move to next element, should set currentValue() to 3
            it1.next();
            Assert.AreEqual(it1.currentValue(), 3);

            //  Try and move to non-existent element, should set currentValue() to -1
            it1.next();

            //  Iterator tried to go to the third element, but it doesn't exist so isDone should now return true
            Assert.AreEqual(it1.isDone(), true);

        }

        [TestMethod]
        public void TestIntSetGetListFromSet()
        {
            IntSet set = new IntSet();
            set.add(2);

            List<int> l = set.getList();

            Assert.AreEqual(l, set.getList());
        }

        [TestMethod]
        public void TestIntSetIsMember()
        {
            IntSet set = new IntSet();
            set.add(2);

            Assert.AreEqual(set.isMember(2), true);
            Assert.AreEqual(set.isMember(1), false);
        }
    }
}
