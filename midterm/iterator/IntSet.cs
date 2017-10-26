using System.Collections.Generic;

namespace DesignPatterns
{
    class IntSet
    {
        private List<int> list = new List<int>();

        public class Iterator
        {
            private IntSet set;
            private IEnumerator<int> e;
            private int current;

            //  Added a current position variable since c# list refuses to throw an exception and instead once it
            //  reaches the end of the list it simply returns 0
            private int curPos;

            public Iterator(IntSet inSet)
            {
                set = inSet;                
            }

            public void first()
            {             
                e = set.list.GetEnumerator();

                //  Added this to set the starting position to 0
                curPos = 0;

                next();
            }

            public bool isDone()
            {
                return current == -1;
            }

            public int currentValue()
            {
                return current;
            }

            public void next()
            {
                //  Instead of try catch since List won't break we instead check if current position
                //  has reached the end of the list and if so set current = -1
                if (curPos == set.list.Count)
                    current = -1;
                else
                {
                    //  Move Enumerator first and then set current to the value at the new position
                    e.MoveNext();
                    current = e.Current;
                    curPos++;
                }
            }
        }
        public void add(int num)
        {
            list.Add(num);
        }

        public bool isMember(int i)
        {
            return list.Contains(i);
        }

        public List<int> getList()
        {
            return list;
        }

        public Iterator createIterator()
        {
            return new Iterator(this);
        }
    }
}
