using System.Collections.Generic;

namespace SoftEngMidterm
{
    //  Student 2 dev time 2 hrs

    //  Student 3 cleaned up code and modified to support a generic type: took 1 hour of development time

    //  C17
    public class IteratorSet<T>
    {
        //  C18
        public List<T> list { get; private set; }

        // C19
        public class Iterator
        {
            private IteratorSet<T> set { get; set; }
            private IEnumerator<T> e { get; set; }

            // C23
            public T currentValue { get; private set; }

            // C22
            public bool isDone { get; private set; }

            //  Added a current position variable since c# list refuses to throw an exception and instead once it
            //  reaches the end of the list it simply returns 0
            private int curPos { get; set; }

            // C20
            public Iterator(IteratorSet<T> inSet)
            {
                isDone = false;
                set = inSet;                
            }

            // C21
            public void first()
            {             
                e = set.list.GetEnumerator();

                //  Added this to set the starting position to 0
                curPos = 0;

                next();
            }

            // C24
            public void next()
            {
                //  Instead of try catch since List won't break we instead check if current position
                //  has reached the end of the list and if so set current = -1
                if (curPos == set.list.Count)
                    isDone = true;
                else
                {
                    //  Move Enumerator first and then set current to the value at the new position
                    e.MoveNext();
                    currentValue = e.Current;
                    curPos++;
                }
            }
        }

        // Not present in the Java code
        public IteratorSet()
        {
            list = new List<T>();
        }

        // C25
        public void add(T value)
        {
            list.Add(value);
        }

        // C26
        public bool isMember(T value)
        {
            return list.Contains(value);
        }

        // C27
        public List<T> getList()
        {
            return list;
        }

        // C28
        public Iterator createIterator()
        {
            return new Iterator(this);
        }
    }
}
