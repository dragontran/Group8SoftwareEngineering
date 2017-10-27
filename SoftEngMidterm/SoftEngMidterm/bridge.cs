using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SoftEngMidterm
{
    class Bridge
    {
        public class Node
        {
            public int value;
            public Node prev, next;

            public Node(int value)
            {
                this.value = value;
            }
        }

        public class StackArray
        {
            private int[] items;
            private int size = -1;

            public StackArray()
            {
                this.items = new int[12];
            }

            public StackArray(int cells)
            {
                this.items = new int[cells];
            }

            public virtual void push(int i)
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

            public virtual int pop()
            {
                if (isEmpty())
                {
                    return -1;
                }
                return items[size--];
            }
        }

        public class StackList
        {
            private Node last;

            public void push(int i)
            {
                if (last == null)
                {
                    last = new Node(i);
                }
                else
                {
                    last.next = new Node(i);
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

        public class StackFIFO : StackArray
        {
            private StackArray stackArray = new StackArray();

            public override int pop()
            {
                while (!isEmpty())
                {
                    stackArray.push(base.pop());
                }
                int ret = stackArray.pop();
                while (!stackArray.isEmpty())
                {
                    push(stackArray.pop());
                }
                return ret;
            }
        }

        public class StackHanoi : StackArray
        {
            private int totalRejected = 0;

            public int reportRejected()
            {
                return totalRejected;
            }

            public override void push(int i)
            {
                if (!isEmpty() && i > top())
                {
                    totalRejected++;
                }
                else
                {
                    base.push(i);
                }
            }
        }
        // class BridgeDisc
        // {
        //     static void Main(string[] args)
        //     {
        //         StackArray[] stacks = { new StackArray(), new StackFIFO(), new StackHanoi() };
        //         StackList stackList = new StackList();
        //         for (int i = 1; i < 15; i++)
        //         {
        //             stacks[0].push(i);
        //             stackList.push(i);
        //             stacks[1].push(i);
        //         }
        //
        //         Random random = new Random();
        //         for (int i = 1; i < 15; i++)
        //         {
        //             stacks[2].push(random.Next(0, 20));
        //         }
        //         while (!stacks[0].isEmpty())
        //         {
        //             Console.Write(stacks[0].pop() + " ");
        //         }
        //         Console.WriteLine();
        //         while (!stackList.isEmpty())
        //         {
        //             Console.Write(stackList.pop() + " ");
        //         }
        //         Console.WriteLine();
        //         while (!stacks[1].isEmpty())
        //         {
        //             Console.Write(stacks[1].pop() + " ");
        //         }
        //         Console.WriteLine();
        //         while (!stacks[2].isEmpty())
        //         {
        //             Console.Write(stacks[2].pop() + " ");
        //         }
        //         Console.WriteLine();
        //         Console.WriteLine("total rejected is " + ((StackHanoi)stacks[2]).reportRejected());
        //     }
        // }
    }
}
