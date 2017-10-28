using System;

namespace SoftEngMidterm
{
    class NodeTwo
    {
        public int value;
        public NodeTwo prev, next;

        public NodeTwo(int value)
        {
            this.value = value;
        }
    }

    public interface stackObjects
    {
        int returnValue();
        StackArrayTwo returnStackArray();
    }

    public class StackArrayTwo
    {
        private int[] items;
        private int size = -1;

        public StackArrayTwo()
        {
            this.items = new int[12];
        }

        public StackArrayTwo(int cells)
        {
            this.items = new int[cells];
        }

        public void push(int i)
        {
            if (!isFull())
            {
                items[++size] = i;
            }
        }

        public bool isEmpty()
        {
            return size == -1;
        }

        public bool isFull()
        {
            return size == items.Length - 1;
        }

        public int top()
        {
            if (isEmpty())
            {
                return -1;
            }
            return items[size];
        }

        public int pop()
        {
            if (isEmpty())
            {
                return -1;
            }
            return items[size--];
        }
    }

    public class StackFIFOTwo : stackObjects
    {
        private StackArrayTwo stackArray;

        private int[] items;
        private int size = -1;

        public StackFIFOTwo()
        {
            this.stackArray = new StackArrayTwo();
        }

        public StackFIFOTwo(int cells)
        {
            this.stackArray = new StackArrayTwo(cells);
        }

        public void push(int i)
        {
            if (!isFull())
            {
                items[++size] = i;
            }
        }

        public bool isEmpty()
        {
            return size == -1;
        }

        public bool isFull()
        {
            return size == items.Length - 1;
        }

        public int top()
        {
            if (isEmpty())
            {
                return -1;
            }
            return items[size];
        }

        public int pop()
        {
            while (!isEmpty())
            {
                // Please check this out if needed
                // was previously base.pop()
                stackArray.push(stackArray.pop());
            }
            int ret = stackArray.pop();
            while (!stackArray.isEmpty())
            {
                push(stackArray.pop());
            }
            return ret;
        }

        public int returnValue()
        {
            throw new NotImplementedException();
        }

        public StackArrayTwo returnStackArray()
        {
            return stackArray;
        }
    }

    public class StackHanoiTwo : stackObjects
    {
        private StackArrayTwo stackArray;

        private int[] items;
        private int size = -1;

        public StackHanoiTwo()
        {
            this.stackArray = new StackArrayTwo();
        }

        public StackHanoiTwo(int cells)
        {
            this.stackArray = new StackArrayTwo(cells);
        }

        private int totalRejected = 0;

        public int reportRejected()
        {
            return totalRejected;
        }

        public bool isEmpty()
        {
            return size == -1;
        }

        public bool isFull()
        {
            return size == items.Length - 1;
        }

        public int top()
        {
            if (isEmpty())
            {
                return -1;
            }
            return items[size];
        }

        public int pop()
        {
            if (isEmpty())
            {
                return -1;
            }
            return items[size--];
        }

        public void push(int i)
        {
            if (!isEmpty() && i > top())
            {
                totalRejected++;
            }
            else
            {
                // Again.. this part had base
                // base.push(i);
                stackArray.push(i);
            }
        }

        public StackArrayTwo returnStackArray()
        {
            return stackArray;
        }

        public int returnValue()
        {
            throw new NotImplementedException();
        }
    }

    class StackListTwo
    {
        private NodeTwo last;

        public void push(int i)
        {
            if (last == null)
            {
                last = new NodeTwo(i);
            }
            else
            {
                last.next = new NodeTwo(i);
                last.next.prev = last;
                last = last.next;
            }
        }

        public bool isEmpty()
        {
            return last == null;
        }

        public bool isFull()
        {
            return false;
        }

        public int top()
        {
            if (isEmpty())
            {
                return -1;
            }
            return last.value;
        }

        public int pop()
        {
            if (isEmpty())
            {
                return -1;
            }
            int ret = last.value;
            last = last.prev;
            return ret;
        }
    }
    /*class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello world");

            StackArray[] stacks = new StackArray[3];

            StackArray stack0 = new StackArray();
            stackObjects stack1 = new StackFIFO();
            stackObjects stack2 = new StackHanoi();

            stacks[0] = stack0;
            stacks[1] = stack1.returnStackArray();
            stacks[2] = stack2.returnStackArray();
            StackList stackList = new StackList();

            for (int i = 1; i < 15; i++)
            {
                stacks[0].push(i);
                stackList.push(i);
                stacks[1].push(i);
            }

            Random random = new Random();
            for (int i = 1; i < 15; i++)
            {
                stacks[2].push(random.Next(0, 20));
            }
            while (!stacks[0].isEmpty())
            {
                Console.Write(stacks[0].pop() + " ");
            }
            Console.WriteLine();
            while (!stackList.isEmpty())
            {
                Console.Write(stackList.pop() + " ");
            }
            Console.WriteLine();
            while (!stacks[1].isEmpty())
            {
                Console.Write(stacks[1].pop() + " ");
            }
            Console.WriteLine();
            while (!stacks[2].isEmpty())
            {
                Console.Write(stacks[2].pop() + " ");
            }
            Console.WriteLine();
            //Console.WriteLine("total rejected is " + ((StackHanoi)stacks[2]).reportRejected());

        }
    }*/

}
