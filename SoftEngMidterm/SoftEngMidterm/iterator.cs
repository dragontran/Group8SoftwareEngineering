using System.Collections.Generic;

namespace SoftEngMidterm
{
    public class IteratorSet<T>
    {
        public List<T> list { get; private set; }

        public class Iterator
        {
            private IteratorSet<T> set { get; set; }
            private IEnumerator<T> e { get; set; }
            public T currentValue { get; private set; }
            public bool isDone { get; private set; }
            //  Added a current position variable since c# list refuses to throw an exception and instead once it
            //  reaches the end of the list it simply returns 0
            private int curPos { get; set; }

            public Iterator(IteratorSet<T> inSet)
            {
                isDone = false;
                set = inSet;                
            }

            public void first()
            {             
                e = set.list.GetEnumerator();

                //  Added this to set the starting position to 0
                curPos = 0;

                next();
            }

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
        public IteratorSet()
        {
            list = new List<T>();
        }
        public void add(T value)
        {
            list.Add(value);
        }

        public bool isMember(T value)
        {
            return list.Contains(value);
        }

        public List<T> getList()
        {
            return list;
        }

        public Iterator createIterator()
        {
            return new Iterator(this);
        }
    }
}
