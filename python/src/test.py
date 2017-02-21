print 'I\'m ok.'
print  3 > 2

classmates = ['Michael', 'Bob', 'Tracy']
classmates.append("lhz")
print len(classmates)
print classmates[-1]

print max(1, 2)

from abstest import my_abs
print my_abs(-100)

L = ['Michael', 'Sarah', 'Tracy', 'Bob', 'Jack']
for i in L:
        print i

def odd():
    print('step 1')
    yield 1
    print('step 2')
    yield(3)
    print('step 3')
    yield(5)

o=odd()
next(o)
next(o)

def add(x, y, f):
    return f(x) + f(y)

print add(-5, 6, abs)
