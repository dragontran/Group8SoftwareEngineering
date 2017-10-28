using System;

namespace SoftEngMidterm
{

    // dragon dev time for factory 45 minutes
    interface stackFactory {
        StackArray getStackObject();
    }

    class stack : stackFactory
    {
        private StackArray stackObject;

        public stack()
        {
            this.stackObject = new StackArray();
        }

        public StackArray getStackObject()
        {
            return stackObject;
        }
    }

    class fifoStack : stackFactory
    {
        private StackArray stackObject;

        public fifoStack() {
            this.stackObject = new StackFIFO();
        }


        public StackArray getStackObject()
        {
            return stackObject;
        }
    }

    class hanoiStack : stackFactory
    {
        private StackArray stackObject;

        public hanoiStack()
        {
            this.stackObject = new StackHanoi();
        }

        public StackArray getStackObject()
        {
            return stackObject;
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
