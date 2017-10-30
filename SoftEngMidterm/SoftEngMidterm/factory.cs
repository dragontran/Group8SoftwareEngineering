using System;

namespace SoftEngMidterm
{

    // student 1 dev time for factory 45 minutes
    // student 4 dev time for factory 45 minutes
    // student 3 dev time for factory 15 minutes

    // interface will showcase factory design pattern principles.
    // will allow the creation of StackArray objects without calling on "new".
    interface stackFactory {
        StackArray getStackObject();
    }

    // stack class that will implement stackFactory interface and generate objects.
    class stack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackArray();
        }
    }

    // fifostack class that will implement stackFactory interface and generate objects.
    class fifoStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackFIFO();
        }
    }

    // hanoiStack class that will implement stackFactory interface and generate objects
    class hanoiStack : stackFactory
    {
        public StackArray getStackObject()
        {
            return new StackHanoi();
        }
    }
}
