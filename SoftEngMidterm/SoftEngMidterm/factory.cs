using System;

namespace SoftEngMidterm
{

    // student 1 dev time for factory 45 minutes
    // student 3 dev time for factory 15 minutes
    interface stackFactory {
        StackArray getStackObject();
    }

    class stack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackArray();
        }
    }

    class fifoStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackFIFO();
        }
    }

    class hanoiStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackHanoi();
        }
    }
}
