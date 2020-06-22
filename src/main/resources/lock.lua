if redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2]) then
    return true
 else
    return false
 end