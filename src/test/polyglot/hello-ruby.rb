puts 'Hello Fibonacci from Ruby on GraalVM!'

n = 42

def fibonacci(n)
    if n == 1
        1
    elsif n == 2
        1
    else
        fibonacci(n-1) + fibonacci(n-2)
    end
end

puts "#{n}'s fibonacci value is #{fibonacci(n)}"