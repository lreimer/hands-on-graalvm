print('Hello Fibonacci from Python on GraalVM!')

n = 42

def fibonacci(n):
    if n == 1:
        return 1
    elif n == 2:
        return 1
    else:
        return fibonacci(n-1) + fibonacci(n-2)

print("{0}'s fibonacci value is {1}".format(n, fibonacci(n)))
